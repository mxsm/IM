package com.github.mxsm.remoting.netty;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public interface NettyRequestProcessor {

    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
        throws Exception;

    /**
     * request is reject
     * @return
     */
    boolean rejectRequest();
}
