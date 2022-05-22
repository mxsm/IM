package com.github.mxsm.remoting;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 1.0.0
 */
public interface RemotingClient extends RemotingService {

    /**
     * 更新注册中心地址
     * @param addrs
     */
    void updateRegisterAddressList(final List<String> addrs);


    /**
     * 获取注册中心地址
     * @return
     */
    List<String> getRegisterAddressList();

    /**
     * 同步执行
     * @param requestHost 发送地址
     * @param request 请求命令
     * @param timeoutMillis 过期时间
     * @return
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    RemotingCommand invokeSync(final String requestHost, final RemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingConnectException,
        RemotingSendRequestException, RemotingTimeoutException;

    /**
     *
     * @param requestHost
     * @param request
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    default RemotingCommand invokeSync(final String requestHost, final RemotingCommand request,
        final long timeout,final TimeUnit unit) throws InterruptedException, RemotingConnectException,
        RemotingSendRequestException, RemotingTimeoutException{
        return invokeSync(requestHost, request, unit.toMillis(timeout));
    }

    /**
     * 异步执行
     * @param requestHost 发送地址
     * @param request 请求命令
     * @param timeoutMillis 过期时间
     * @param invokeCallback 回调函数
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeAsync(final String requestHost, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     *
     * @param requestHost
     * @param request
     * @param timeout
     * @param unit
     * @param invokeCallback
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    default void invokeAsync(final String requestHost, final RemotingCommand request, final long timeout,final TimeUnit unit,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException{
        invokeAsync(requestHost, request, unit.toMillis(timeout), invokeCallback);
    }

    /**
     * 单向执行
     * @param requestHost
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeOneway(final String requestHost, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;

    /**
     *
     * @param requestHost
     * @param request
     * @param timeout
     * @param unit
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    default  void invokeOneway(final String requestHost, final RemotingCommand request, final long timeout, final TimeUnit unit)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException{
        invokeOneway(requestHost, request, unit.toMillis(timeout));
    }
}
