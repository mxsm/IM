package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.remoting.ChannelEventListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端连接管理
 *
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
@Sharable
public class NettyServerConnectManageHandler extends ChannelDuplexHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerConnectManageHandler.class.getSimpleName());

    private ChannelEventListener channelEventListener;

    public NettyServerConnectManageHandler() {
        super();
    }

    public NettyServerConnectManageHandler(ChannelEventListener channelEventListener) {
        this.channelEventListener = channelEventListener;
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward to the next {@link ChannelInboundHandler}
     * in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Netty Server channelRegistered");
        super.channelRegistered(ctx);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onChannelRegistered(channel.remoteAddress().toString(), channel);
        }

    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward to the next {@link
     * ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Netty Server channelUnregistered");
        super.channelUnregistered(ctx);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onChannelUnregistered(channel);
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the next {@link ChannelInboundHandler} in
     * the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Netty Server channelActive");
        super.channelActive(ctx);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onChannelActive(channel);
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward to the next {@link ChannelInboundHandler} in
     * the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Netty Server channelInactive");
        super.channelInactive(ctx);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onChannelInactive(channel);
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward to the next {@link ChannelHandler}
     * in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("Netty Server exceptionCaught");
        super.exceptionCaught(ctx, cause);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onExceptionCaught(channel, cause);
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward to the next {@link
     * ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOGGER.info("Netty Server channelRegistered");
        super.userEventTriggered(ctx, evt);
        if (channelEventListener != null) {
            Channel channel = ctx.channel();
            channelEventListener.onUserEventTriggered(channel, evt);
        }
    }

    public ChannelEventListener getChannelEventListener() {
        return channelEventListener;
    }

    public void setChannelEventListener(ChannelEventListener channelEventListener) {
        this.channelEventListener = channelEventListener;
    }
}
