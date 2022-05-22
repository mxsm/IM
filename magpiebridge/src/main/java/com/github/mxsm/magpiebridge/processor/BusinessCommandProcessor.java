package com.github.mxsm.magpiebridge.processor;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mxsm
 * @date 2022/5/22 10:28
 * @Since 1.0.0
 */
public class BusinessCommandProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {
        return null;
    }

    /**
     * request is reject
     *
     * @return
     */
    @Override
    public boolean rejectRequest() {
        return false;
    }
}
