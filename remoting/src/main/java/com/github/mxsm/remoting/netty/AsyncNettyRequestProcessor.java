package com.github.mxsm.remoting.netty;


import com.github.mxsm.protocol.core.RemotingCommand;
import com.github.mxsm.remoting.RemotingResponseCallback;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since
 */
public interface AsyncNettyRequestProcessor extends NettyRequestProcessor {

    default void asyncProcessRequest(ChannelHandlerContext ctx, RemotingCommand request,
        RemotingResponseCallback responseCallback) throws Exception {
        RemotingCommand response = processRequest(ctx, request);
        responseCallback.callback(response);
    }
}
