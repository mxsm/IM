package com.github.mxsm.remoting;

import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import com.github.mxsm.remoting.protocol.RequestRemotingCommand;
import com.github.mxsm.remoting.protocol.ResponseRemotingCommand;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public interface RemotingServer extends RemotingService {

    /**
     * 同步执行
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     * @throws InterruptedException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    ResponseRemotingCommand invokeSync(final Channel channel, final RequestRemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
        RemotingTimeoutException;

    /**
     * 异步执行
     * @param channel
     * @param request
     * @param timeoutMillis
     * @param invokeCallback
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeAsync(final Channel channel, final RequestRemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 单项执行
     * @param channel
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeOneway(final Channel channel, final RequestRemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException;


    /**
     * 注册请求处理器
     * @param requestProcessor
     */
    void registerProcessor(final NettyRequestProcessor requestProcessor);
}
