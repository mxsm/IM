package com.github.mxsm.remoting.netty;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.channel.ChannelWrapper;
import com.github.mxsm.remoting.netty.handler.ClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingClient implements RemotingClient {

    private final NettyClientConfig nettyClientConfig;

    private final Bootstrap nettyBootstrap;

    private final ChannelEventListener channelEventListener;

    private final EventLoopGroup eventLoopGroupWorker;

    private  EventExecutorGroup eventExecutorGroup;

    /**
     * 记录Channel和IP之间的关系
     */
    private final ConcurrentMap<String /*IP*/, ChannelWrapper> channelTable = new ConcurrentHashMap<>();


    public NettyRemotingClient(final NettyClientConfig nettyClientConfig) {
        this(nettyClientConfig, null);
    }

    public NettyRemotingClient(final NettyClientConfig nettyClientConfig,
        final ChannelEventListener channelEventListener) {
        this.nettyClientConfig = nettyClientConfig;
        this.channelEventListener = channelEventListener;
        this.nettyBootstrap = new Bootstrap();
        this.eventLoopGroupWorker = new NioEventLoopGroup(1, new NamedThreadFactory("NettyClientSelector"));
    }

    /**
     * 更新注册中心地址
     *
     * @param addrs
     */
    @Override
    public void updateRegisterAddressList(List<String> addrs) {

    }

    /**
     * 同步执行
     *
     * @param addr          发送地址
     * @param request       请求命令
     * @param timeoutMillis 过期时间
     * @return
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    @Override
    public RemotingCommand invokeSync(String addr, RemotingCommand request,
        long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException {
        return null;
    }

    /**
     * 异步执行
     *
     * @param addr           发送地址
     * @param request        请求命令
     * @param timeoutMillis  过期时间
     * @param invokeCallback 回调函数
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    @Override
    public void invokeAsync(String addr, RemotingCommand request, long timeoutMillis,
        InvokeCallback invokeCallback)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {

    }

    /**
     * 单项执行
     *
     * @param addr
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    @Override
    public void invokeOneway(String addr, RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {

    }

    /**
     * start service
     */
    @Override
    public void start() {

        this.eventExecutorGroup = new DefaultEventExecutorGroup(nettyClientConfig.getClientWorkerThreads(),
            new NamedThreadFactory("NettyClientWorkerThread"));

        this.nettyBootstrap.group(this.eventLoopGroupWorker).channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_KEEPALIVE, false)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyClientConfig.getConnectTimeoutMillis())
            .option(ChannelOption.SO_SNDBUF, nettyClientConfig.getClientSocketSndBufSize())
            .option(ChannelOption.SO_RCVBUF, nettyClientConfig.getClientSocketRcvBufSize())
            .handler(new ClientHandlerInitializer(channelEventListener,eventExecutorGroup));

    }

    /**
     * shutdown service
     */
    @Override
    public void shutdown() {

    }
}
