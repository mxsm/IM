package com.github.mxsm.remoting.netty;

import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.RemotingServer;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.protocol.RequestRemotingCommand;
import com.github.mxsm.remoting.protocol.ResponseRemotingCommand;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class NettyRemotingServer implements RemotingServer {

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
    public ResponseRemotingCommand invokeSync(Channel channel, RequestRemotingCommand request,
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
    public void invokeAsync(Channel channel, RequestRemotingCommand request, long timeoutMillis,
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
    public void invokeOneway(Channel channel, RequestRemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {

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
