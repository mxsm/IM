package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author mxsm
 * @Date 2021/7/1
 * @Since 1.0.0
 */
public interface RemotingHandler {

    default void processMessageReceived(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
        final RemotingCommand cmd = msg;
        switch (cmd.getCommandType()) {
            case REQUEST:
                processRequestCommand(ctx, msg);
                break;
            case RESPONSE:
                processResponseCommand(ctx, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 处理请求命令
     * @param ctx
     * @param cmd
     */
    void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd);

    /**
     * 处理返回命令
     * @param ctx
     * @param cmd
     */
    void processResponseCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd);
}
