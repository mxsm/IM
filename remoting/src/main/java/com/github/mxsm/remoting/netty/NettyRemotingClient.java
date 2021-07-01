package com.github.mxsm.remoting.netty;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.NettyUtils;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.channel.ChannelWrapper;
import com.github.mxsm.remoting.netty.handler.NettyClientHandlerInitializer;
import com.github.mxsm.remoting.netty.handler.NettyRemotingHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingClient extends NettyRemotingHandler implements RemotingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRemotingClient.class);

    private static final long LOCK_TIMEOUT_MILLIS = 3000;

    private final NettyClientConfig nettyClientConfig;

    private final Bootstrap nettyBootstrap;

    private final ChannelEventListener channelEventListener;

    private final EventLoopGroup eventLoopGroupWorker;

    private EventExecutorGroup eventExecutorGroup;

    private Lock channelTableLock = new ReentrantLock();

    private final ExecutorService publicExecutor;

    /**
     * Invoke the callback methods in this executor when process response.
     */
    private ExecutorService callbackExecutor;


    /**
     * 记录Channel和IP之间的关系
     */
    private final ConcurrentMap<String /*IP(IP:port)*/, ChannelWrapper> channelTable = new ConcurrentHashMap<>();


    public NettyRemotingClient(final NettyClientConfig nettyClientConfig) {
        this(nettyClientConfig, null);
    }

    public NettyRemotingClient(final NettyClientConfig nettyClientConfig,
        final ChannelEventListener channelEventListener) {
        super(nettyClientConfig.getClientOnewaySemaphoreValue(), nettyClientConfig.getClientAsyncSemaphoreValue());
        this.nettyClientConfig = nettyClientConfig;
        this.channelEventListener = channelEventListener;
        this.nettyBootstrap = new Bootstrap();
        this.eventLoopGroupWorker = new NioEventLoopGroup(1, new NamedThreadFactory("NettyClientSelector"));
        int publicThreadNums = nettyClientConfig.getClientCallbackExecutorThreads();
        if (publicThreadNums <= 0) {
            publicThreadNums = 4;
        }
        this.publicExecutor = Executors
            .newFixedThreadPool(publicThreadNums, new NamedThreadFactory("NettyClientPublicExecutor"));
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
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = this.getOrElseCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                long costTime = System.currentTimeMillis() - beginStartTime;
                if (timeoutMillis < costTime) {
                    throw new RemotingTimeoutException("invokeSync call timeout");
                }
                RemotingCommand response = this.invokeSyncImpl(channel, request, timeoutMillis - costTime);
                return response;
            } catch (RemotingSendRequestException e) {
                LOGGER.warn("invokeSync: send request exception, so close the channel[{}]", addr);
                this.closeChannel(addr, channel);
                throw e;
            } catch (RemotingTimeoutException e) {
                if (nettyClientConfig.isClientCloseSocketIfTimeout()) {
                    this.closeChannel(addr, channel);
                    LOGGER.warn("invokeSync: close socket because of timeout, {}ms, {}", timeoutMillis, addr);
                }
                LOGGER.warn("invokeSync: wait response timeout exception, the channel[{}]", addr);
                throw e;
            }
        } else {
            this.closeChannel(addr, channel);
            throw new RemotingConnectException(addr);
        }

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
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = this.getOrElseCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                long costTime = System.currentTimeMillis() - beginStartTime;
                if (timeoutMillis < costTime) {
                    throw new RemotingTooMuchRequestException("invokeAsync call timeout");
                }
                this.invokeAsyncImpl(channel, request, timeoutMillis - costTime, invokeCallback);
            } catch (RemotingSendRequestException e) {
                LOGGER.warn("invokeAsync: send request exception, so close the channel[{}]", addr);
                this.closeChannel(addr, channel);
                throw e;
            }
        } else {
            this.closeChannel(addr, channel);
            throw new RemotingConnectException(addr);
        }
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

        final Channel channel = getOrElseCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                this.invokeOnewayImpl(channel, request, timeoutMillis);
            } catch (RemotingSendRequestException e) {
                LOGGER.warn("invokeOneway: send request exception, so close the channel[{}]", addr);
                this.closeChannel(addr, channel);
                throw e;
            }
        } else {
            closeChannel(addr, channel);
            throw new RemotingConnectException(addr);
        }

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
            .handler(
                new NettyClientHandlerInitializer(channelEventListener, eventExecutorGroup, nettyClientConfig, this));

    }

    /**
     * shutdown service
     */
    @Override
    public void shutdown() {

    }

    /**
     * 根据IP从缓存中获取，如果不存在则创建Channel
     *
     * @param ip 带上端口127.0.0.1:8080
     * @return
     */
    private Channel getOrElseCreateChannel(String ip) throws InterruptedException {

        ChannelWrapper channelWrapper = this.channelTable.get(ip);
        if (null != channelWrapper && channelWrapper.isOK()) {
            return channelWrapper.getChannel();
        }

        if (this.channelTableLock.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
            try {
                boolean newCreateChannel;
                channelWrapper = this.channelTable.get(ip);
                if (channelWrapper == null) {
                    newCreateChannel = true;
                } else {
                    if (channelWrapper.isOK()) {
                        return channelWrapper.getChannel();
                    } else if (!channelWrapper.getChannelFuture().isDone()) {
                        newCreateChannel = false;
                    } else {
                        this.channelTable.remove(ip);
                        newCreateChannel = true;
                    }
                }
                if (newCreateChannel) {
                    ChannelFuture channelFuture = this.nettyBootstrap.bind(NetUtils.ip2SocketAddress(ip));
                    LOGGER.info("createChannel: begin to connect remote host[{}] asynchronously", ip);
                    channelWrapper = ChannelWrapper.builder(channelFuture);
                    this.channelTable.put(ip, channelWrapper);
                }
            } catch (Exception e) {
                LOGGER.error("createChannel: create channel exception", e);
            } finally {
                this.channelTableLock.unlock();
            }
            if (channelWrapper != null) {
                ChannelFuture channelFuture = channelWrapper.getChannelFuture();
                if (channelFuture.await(this.nettyClientConfig.getConnectTimeoutMillis(), TimeUnit.MILLISECONDS)) {
                    if (channelWrapper.isOK()) {
                        LOGGER.info("createChannel: connect remote host[{}] success, {}", ip, channelFuture);
                        return channelWrapper.getChannel();
                    }
                } else {
                    LOGGER.warn("createChannel: connect remote host[{}] timeout {}ms, {}", ip,
                        this.nettyClientConfig.getConnectTimeoutMillis(), channelFuture);
                }
            }
        }
        return null;

    }

    private void closeChannel(final String ip, final Channel channel) {
        if (null == channel) {
            return;
        }
        final String remoteIp = ip == null ? NetUtils.parseChannelRemoteAddr(channel) : ip;
        try {
            if (this.channelTableLock.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
                boolean removeCw = true;
                ChannelWrapper channelWrapper = this.channelTable.get(remoteIp);
                if (channelWrapper == null) {
                    removeCw = false;
                } else if (channelWrapper.getChannel() != channel) {
                    removeCw = false;
                } else {
                    //
                }
                if (removeCw) {
                    this.channelTable.remove(remoteIp);
                    LOGGER.info("closeChannel: Remove Channel[{}] From channelTable", remoteIp);
                }
                NettyUtils.closeChannel(channel);
            }
        } catch (InterruptedException e) {
            LOGGER.error("closeChannel: close the channel exception", e);
        } finally {
            this.channelTableLock.unlock();
        }

    }

    @Override
    public ExecutorService getCallbackExecutor() {
        return callbackExecutor != null ? callbackExecutor : publicExecutor;
    }

    @Override
    public ChannelEventListener getChannelEventListener() {
        return this.channelEventListener;
    }
}
