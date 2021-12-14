package com.github.mxsm.client;

import com.github.mxsm.client.config.ImClientConfig;
import com.github.mxsm.common.utils.AnnotationUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingClient;

/**
 * @author mxsm
 * @date 2021/10/10 16:25
 * @Since 1.0.0
 */
public class ImClientImpl implements ImClient {

    private final RemotingClient remotingClient;

    private final NettyClientConfig nettyClientConfig;

    private final ImClientConfig imClientConfig;

    public ImClientImpl(final NettyClientConfig nettyClientConfig, final ImClientConfig imClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.imClientConfig = imClientConfig;
        this.remotingClient = new NettyRemotingClient(this.nettyClientConfig);
    }

    @Override
    public void init() {
        AnnotationUtils.validatorNotNull(this.nettyClientConfig,this.imClientConfig);
    }

    @Override
    public void start() {
        this.remotingClient.start();
    }



    @Override
    public void shutdown() {

    }

    /**
     * Check whether it is started
     *
     * @return
     */
    @Override
    public boolean isStarted() {
        return false;
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
    public RemotingCommand invokeSync(String addr, RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException {
        return this.remotingClient.invokeSync(addr, request, timeoutMillis);
    }

    /**
     * 同步执行
     *
     * @param request       请求命令
     * @param timeoutMillis 过期时间
     * @return
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    @Override
    public RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException {
        return this.invokeSync(imClientConfig.getMagpieBridgeAddressWithPort(), request, timeoutMillis);
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
    public void invokeAsync(String addr, RemotingCommand request, long timeoutMillis, InvokeCallback invokeCallback)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        this.remotingClient.invokeAsync(addr, request, timeoutMillis, invokeCallback);
    }

    /**
     * 异步执行
     *
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
    public void invokeAsync(RemotingCommand request, long timeoutMillis, InvokeCallback invokeCallback)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        this.remotingClient.invokeAsync(this.imClientConfig.getMagpieBridgeAddressWithPort(), request, timeoutMillis,
            invokeCallback);
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
        this.remotingClient.invokeOneway(addr, request, timeoutMillis);
    }

    /**
     * 单项执行
     *
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    @Override
    public void invokeOneway(RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        this.remotingClient.invokeOneway(this.imClientConfig.getMagpieBridgeAddressWithPort(), request, timeoutMillis);
    }
}
