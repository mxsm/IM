package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.NettyUtils;
import com.github.mxsm.remoting.event.NettyEvent;
import com.github.mxsm.remoting.event.NettyEventPublisher;
import com.github.mxsm.remoting.event.NettyEventType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.net.SocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端连接管理
 *
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class NettyClientConnectManageHandler extends ChannelDuplexHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientConnectManageHandler.class.getSimpleName());

    private final NettyEventPublisher nettyEventPublisher;

    public NettyClientConnectManageHandler(final NettyEventPublisher nettyEventPublisher) {
        this.nettyEventPublisher = nettyEventPublisher;
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {

        final String remote = localAddress == null ? "unknown" : NetUtils.parseSocketAddress2Address(remoteAddress);
        final String local = localAddress == null ? "unknown" : NetUtils.parseSocketAddress2Address(localAddress);

        LOGGER.info("netty client pipeline: connct {}==>{}", local, remote);
        super.connect(ctx, remoteAddress, localAddress, promise);

        if (null != this.nettyEventPublisher) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.CONNECT, remote, ctx.channel()));
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        String remoteAddress = NetUtils.parseChannelRemoteAddress(ctx.channel());
        super.close(ctx, promise);
        LOGGER.info("netty client pipeline: close {}", remoteAddress);
        if (null != this.nettyEventPublisher) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress, ctx.channel()));
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        String remoteAddress = NetUtils.parseChannelRemoteAddress(ctx.channel());
        LOGGER.info("netty client pipeline: disconnect {}", remoteAddress);
        super.disconnect(ctx, promise);
        if (null != this.nettyEventPublisher) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress, ctx.channel()));
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (this.nettyEventPublisher != null && event.state() == IdleState.ALL_IDLE) {
                String remoteAddress = NetUtils.parseChannelRemoteAddress(ctx.channel());
                LOGGER.info("netty client piplie: idle exception [{}]",remoteAddress);
                if (nettyEventPublisher != null) {
                    Channel channel = ctx.channel();
                    this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.IDLE,
                            NetUtils.parseChannelRemoteAddress(channel), channel, event));
                }
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("netty client exception",cause);
        if (nettyEventPublisher != null) {
            this.nettyEventPublisher.publishEvent(new NettyEvent(NettyEventType.EXCEPTION,
                    NetUtils.parseChannelRemoteAddress(ctx.channel()), ctx.channel(), cause));
        } else {
            LOGGER.error("Netty client exceptionCaught", cause);
        }
        NettyUtils.closeChannel(ctx.channel());
    }
}
