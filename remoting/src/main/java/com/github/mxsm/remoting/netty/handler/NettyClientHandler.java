package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    private final NettyRemotingHandler nettyRemoting;

    public NettyClientHandler(final NettyRemotingHandler nettyRemoting){
        this.nettyRemoting = nettyRemoting;
    }

    /**
     * Is called for each message of type {@link I}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler} belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
        LOGGER.debug("Received RemotingCommand Type: {}", msg.getCommandType());
        nettyRemoting.processMessageReceived(ctx, msg);

    }
}
