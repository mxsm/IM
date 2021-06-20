package com.github.mxsm.remoting.netty;

import com.github.mxsm.remoting.protocol.RequestRemotingCommand;
import com.github.mxsm.remoting.protocol.ResponseRemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public interface NettyRequestProcessor {

    ResponseRemotingCommand processRequest(ChannelHandlerContext ctx, RequestRemotingCommand request)
        throws Exception;

}
