package com.github.mxsm.remoting.netty;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingServer;
import com.github.mxsm.remoting.common.RemotingUtils;
import com.github.mxsm.remoting.event.NettyEvent;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.handler.NettyRemotingHandler;
import com.github.mxsm.remoting.netty.handler.NettyServerHandler;
import com.github.mxsm.remoting.netty.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingServer extends NettyRemotingHandler implements RemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRemotingServer.class);

    private final NettyServerConfig nettyServerConfig;

    private final ServerBootstrap serverBootstrap;


    private final EventLoopGroup bossEventLoopGroup;

    private final EventLoopGroup selectorEventLoopGroup;

    private final ChannelEventListener channelEventListener;

    private final EventExecutorGroup eventExecutorGroup;

    private int bindPort;

    private NettyServerHandler nettyServerHandler;

    private final ExecutorService publicExecutor;

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig) {
        this(nettyServerConfig, null);
    }

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig,
        final ChannelEventListener channelEventListener) {
        super(nettyServerConfig.getServerOnewaySemaphoreValue(), nettyServerConfig.getServerAsyncSemaphoreValue());
        this.serverBootstrap = new ServerBootstrap();
        this.channelEventListener = channelEventListener;

        int publicThreadNums = nettyServerConfig.getServerCallbackExecutorThreads();
        if (publicThreadNums <= 0) {
            publicThreadNums = 4;
        }

        this.publicExecutor = Executors
            .newFixedThreadPool(publicThreadNums, new NamedThreadFactory("NettyServerPublicExecutor"));

        this.nettyServerConfig = nettyServerConfig;
        if (useEpoll()) {
            this.bossEventLoopGroup = new EpollEventLoopGroup(1, new NamedThreadFactory("NettyEPOLLBoss"));
            this.selectorEventLoopGroup = new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads(),
                new NamedThreadFactory("NettyEPOLLSelector"));
        } else {
            this.bossEventLoopGroup = new NioEventLoopGroup(1, new NamedThreadFactory("NettyNIOBoss"));
            this.selectorEventLoopGroup = new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads(),
                new NamedThreadFactory("NettyNIOSelector"));
        }
        eventExecutorGroup = new DefaultEventExecutorGroup(nettyServerConfig.getServerWorkerThreads(),
            new NamedThreadFactory("eventExecutorThread"));
    }


    /**
     * 同步执行
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     * @throws InterruptedException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    @Override
    public RemotingCommand invokeSync(Channel channel, RemotingCommand request,
        long timeoutMillis) throws InterruptedException, RemotingSendRequestException, RemotingTimeoutException {
        return this.invokeSyncImpl(channel, request, timeoutMillis);

    }

    /**
     * 异步执行
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @param invokeCallback
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    @Override
    public void invokeAsync(Channel channel, RemotingCommand request, long timeoutMillis,
        InvokeCallback invokeCallback)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        this.invokeAsyncImpl(channel, request, timeoutMillis, invokeCallback);
    }

    /**
     * 单项执行
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    @Override
    public void invokeOneway(Channel channel, RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        this.invokeOnewayImpl(channel, request, timeoutMillis);
    }

    /**
     * 注册请求处理器
     *
     * @param requestProcessor
     */
    @Override
    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor) {
        super.registerProcessor(requestCode, processor, executor);
    }

    @Override
    public ChannelEventListener getChannelEventListener() {
        return this.channelEventListener;
    }

    /**
     * start service
     */
    @Override
    public void start() {

        nettyServerHandler = new NettyServerHandler(this);

        this.serverBootstrap.group(this.bossEventLoopGroup, this.selectorEventLoopGroup)
            .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .option(ChannelOption.SO_REUSEADDR, true)
            .childOption(ChannelOption.SO_KEEPALIVE, false)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
            .childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
            .localAddress(new InetSocketAddress(nettyServerConfig.getBindPort()))
            .childHandler(new NettyServerHandlerInitializer(this, eventExecutorGroup,
                nettyServerConfig,
                nettyServerHandler));

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            InetSocketAddress address = (InetSocketAddress) sync.channel().localAddress();
            bindPort = address.getPort();
            LOGGER.info("---------------NettyRemotingServer-[{}:{}] started finish----------------",
                address.getAddress().getHostAddress(),
                bindPort);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.channelEventListener != null) {
            this.nettyEventWorker.start();
            LOGGER.info("-----------------NettyEventWork started-------------------");
        }
    }

    /**
     * shutdown service
     */
    @Override
    public void shutdown() {

        if (this.nettyEventWorker != null) {
            this.nettyEventWorker.shutdown(false);
        }

        this.bossEventLoopGroup.shutdownGracefully();
        this.selectorEventLoopGroup.shutdownGracefully();
        if (this.eventExecutorGroup != null) {
            this.eventExecutorGroup.shutdownGracefully();
        }

        if(this.publicExecutor != null){
            this.publicExecutor.shutdown();
        }
    }

    private boolean useEpoll() {
        return RemotingUtils.isLinuxPlatform() && nettyServerConfig.isUseEpollNativeSelector() && Epoll.isAvailable();
    }

    @Override
    public ExecutorService getCallbackExecutor() {
        return this.publicExecutor;
    }

    @Override
    public void publishEvent(NettyEvent nettyEvent) {
        putNettyEvent(nettyEvent);
    }
}
