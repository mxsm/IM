package com.github.mxsm.remoting;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import io.netty.channel.Channel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 1.0.0
 */
public interface RemotingServer extends RemotingService {

    /**
     * invoke sync
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     * @throws InterruptedException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    RemotingCommand invokeSync(final Channel channel, final RemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
        RemotingTimeoutException;

    /**
     * invoke sync
     *
     * @param channel
     * @param request
     * @param timeout
     * @param timeUnit
     * @return
     * @throws InterruptedException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    default RemotingCommand invokeSync(final Channel channel, final RemotingCommand request,
        final long timeout, final TimeUnit timeUnit) throws InterruptedException, RemotingSendRequestException,
        RemotingTimeoutException {
        return invokeSync(channel, request, timeUnit.toMillis(timeout));
    }

    /**
     * invoke Async
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
    void invokeAsync(final Channel channel, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * invoke Async
     *
     * @param channel
     * @param request
     * @param timeout
     * @param timeUnit
     * @param invokeCallback
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    default void invokeAsync(final Channel channel, final RemotingCommand request, final long timeout,
        final TimeUnit timeUnit, final InvokeCallback invokeCallback) throws InterruptedException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        invokeAsync(channel, request, timeUnit.toMillis(timeout), invokeCallback);
    }

    /**
     * invoke Oneway
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException;


    /**
     * invoke Oneway
     *
     * @param channel
     * @param request
     * @param timeout
     * @param unit
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    default void invokeOneway(final Channel channel, final RemotingCommand request, final long timeout,
        final TimeUnit unit)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException {
        invokeOneway(channel, request, unit.toMillis(timeout));
    }

    /**
     * 注册请求处理器
     *
     * @param requestCode
     * @param processor
     * @param executor
     */
    void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);
}
