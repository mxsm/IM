package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    private final RemotingHandler remotingHandler;

    public NettyServerHandler(final RemotingHandler remotingHandler){
        super();
        this.remotingHandler = remotingHandler;
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
        this.remotingHandler.processMessageReceived(ctx, msg);
    }
}
