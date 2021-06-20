package com.github.mxsm.remoting.netty;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import java.util.List;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingClient implements RemotingClient {

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

    }

    /**
     * shutdown service
     */
    @Override
    public void shutdown() {

    }
}
