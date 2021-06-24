package com.github.mxsm.remoting.netty;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingServer;
import com.github.mxsm.remoting.common.RemotingUtils;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.handler.NettyServerConnectManageHandler;
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
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingServer implements RemotingServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRemotingServer.class);

    private final NettyServerConfig nettyServerConfig;

    private final ServerBootstrap serverBootstrap;


    private final EventLoopGroup bossEventLoopGroup;

    private final EventLoopGroup selectorEventLoopGroup;

    private final ChannelEventListener channelEventListener;

    private final EventExecutorGroup eventExecutorGroup;

    private int bindPort;

    private NettyServerConnectManageHandler nettyServerConnectManageHandler;

    private NettyServerHandler nettyServerHandler;

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig) {
        this(nettyServerConfig, null);
    }

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig,
        final ChannelEventListener channelEventListener) {

        this.serverBootstrap = new ServerBootstrap();
        this.channelEventListener = channelEventListener;

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

        nettyServerConnectManageHandler = new NettyServerConnectManageHandler(this.channelEventListener);
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
        return null;
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

    }

    /**
     * 注册请求处理器
     *
     * @param requestProcessor
     */
    @Override
    public void registerProcessor(NettyRequestProcessor requestProcessor) {

    }

    /**
     * start service
     */
    @Override
    public void start() {

        nettyServerHandler = new NettyServerHandler();

        this.serverBootstrap.group(this.bossEventLoopGroup, this.selectorEventLoopGroup)
            .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .option(ChannelOption.SO_REUSEADDR, true)
            .childOption(ChannelOption.SO_KEEPALIVE, false)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
            .childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
            .localAddress(new InetSocketAddress(nettyServerConfig.getBindPort()))
            .childHandler(new NettyServerHandlerInitializer(nettyServerConnectManageHandler, eventExecutorGroup, nettyServerConfig,
                nettyServerHandler));

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            InetSocketAddress address = (InetSocketAddress) sync.channel().localAddress();
            bindPort = address.getPort();
            LOGGER.info(">>>>>>>>>>NettyRemotingServer-[{}:{}]启动完成<<<<<<<<<<<<", address.getAddress().getHostAddress(),
                bindPort);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * shutdown service
     */
    @Override
    public void shutdown() {
        this.bossEventLoopGroup.shutdownGracefully();
        this.selectorEventLoopGroup.shutdownGracefully();
    }

    private boolean useEpoll() {
        return RemotingUtils.isLinuxPlatform() && nettyServerConfig.isUseEpollNativeSelector() && Epoll.isAvailable();
    }
}
