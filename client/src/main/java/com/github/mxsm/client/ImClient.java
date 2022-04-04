package com.github.mxsm.client;

import com.alibaba.fastjson.JSON;

import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.protobuf.ServerMetadata;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.LifeCycle;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.exception.Crc32ValidationException;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.google.protobuf.ByteString;

/**
 * @author mxsm
 * @date 2021/10/10 16:16
 * @Since 1.0.0
 */
public interface ImClient extends LifeCycle {

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
    RemotingCommand invokeSync(final String addr, final RemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingConnectException,
        RemotingSendRequestException, RemotingTimeoutException;

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
    RemotingCommand invokeSync(final RemotingCommand request,
        final long timeoutMillis) throws InterruptedException, RemotingConnectException,
        RemotingSendRequestException, RemotingTimeoutException;

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
    void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

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
    void invokeAsync(final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
        RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

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
    void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;

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
    void invokeOneway(final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;


    default ServerMetadata getConnectMagpieBridge(final String registerAddress)
        throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException, Crc32ValidationException {

        RemotingCommand request = RemotingCommandBuilder.buildRequestCommand()
            .setCode(RequestCode.GET_MAGPIE_BRIDGE_ADDRESS).build();
        RemotingCommand response = invokeSync(registerAddress, request, 2000);

        if (null == response) {
            return null;
        }
        int payloadCrc32 = response.getPayloadCrc32();
        ByteString payload = response.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            throw new Crc32ValidationException("Payload CRC32 not Match");
        }
        return JSON.parseObject(payload.toStringUtf8(), ServerMetadata.class);
    }
}
