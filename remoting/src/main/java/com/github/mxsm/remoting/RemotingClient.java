package com.github.mxsm.remoting;

import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.protocol.RequestRemotingCommand;
import com.github.mxsm.remoting.protocol.ResponseRemotingCommand;
import java.util.List;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public interface RemotingClient extends RemotingService {

    /**
     * 更新注册中心地址
     * @param addrs
     */
    void updateRegisterAddressList(final List<String> addrs);

    /**
     * 同步执行
     * @param addr 发送地址
     * @param request 请求命令
     * @param timeoutMillis 过期时间
     * @return
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     */
    ResponseRemotingCommand invokeSync(final String addr, final RequestRemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingConnectException,
        RemotingSendRequestException, RemotingTimeoutException;

    /**
     * 异步执行
     * @param addr 发送地址
     * @param request 请求命令
     * @param timeoutMillis 过期时间
     * @param invokeCallback 回调函数
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeAsync(final String addr, final RequestRemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 单项执行
     * @param addr
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingConnectException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    void invokeOneway(final String addr, final RequestRemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;

/*    void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);*/
}
