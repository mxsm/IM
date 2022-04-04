package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.NettyUtils;
import com.github.mxsm.remoting.event.NettyEvent;
import com.github.mxsm.remoting.event.NettyEventPublisher;
import com.github.mxsm.remoting.event.NettyEventType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * server connection management
 *
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
@Sharable
public class NettyServerConnectManageHandler extends ChannelDuplexHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerConnectManageHandler.class.getSimpleName());

    private final NettyEventPublisher nettyEventPublisher;

    public NettyServerConnectManageHandler(final NettyEventPublisher nettyEventPublisher) {
        this.nettyEventPublisher = nettyEventPublisher;
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
        final String address = NetUtils.parseChannelRemoteAddress(ctx.channel());
        LOGGER.info("Netty Server channelRegistered[{}]", address);
        super.channelRegistered(ctx);
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
        final String address = NetUtils.parseChannelRemoteAddress(ctx.channel());
        LOGGER.info("Netty Server channelUnregistered[{}]",address);
        super.channelUnregistered(ctx);

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
        final String address = NetUtils.parseChannelRemoteAddress(ctx.channel());
        LOGGER.info("Netty Server channelActive[{}]",address);
        super.channelActive(ctx);
        if (this.nettyEventPublisher != null) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.CONNECT, address, ctx.channel()));
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

        final String address = NetUtils.parseChannelRemoteAddress(ctx.channel());
        LOGGER.info("Netty Server channelInactive[{}]", address);
        super.channelInactive(ctx);
        if (this.nettyEventPublisher != null) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.CLOSE, address, ctx.channel()));
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
        LOGGER.error("nettyServerConnect exception",cause);
        if (nettyEventPublisher != null) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.EXCEPTION,
                    NetUtils.parseChannelRemoteAddress(ctx.channel()), ctx.channel(), cause));
        } else {
            LOGGER.error("Netty Server exceptionCaught", cause);
        }
        NettyUtils.closeChannel(ctx.channel());
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
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (this.nettyEventPublisher != null && event.state() == IdleState.ALL_IDLE) {
                String remoteAddress = NetUtils.parseChannelRemoteAddress(ctx.channel());
                LOGGER.info("netty server piplie: idle exception [{}]",remoteAddress);
                if (nettyEventPublisher != null) {
                    Channel channel = ctx.channel();
                    this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.IDLE,
                            NetUtils.parseChannelRemoteAddress(channel), channel, event));
                }
            }
        }
        ctx.fireUserEventTriggered(evt);
    }
}
