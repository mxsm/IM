package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.common.AnnotationUtils;
import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
public class ServerHandlerInitializer extends ChannelInitializer {

    @NotNull
    private final NettyConnectManageHandler nettyConnectManageHandler;

    @NotNull
    private final DefaultEventLoopGroup defaultEventLoopGroup;

    @NotNull
    private final MessageLite lite;

    private final NettyServerConfig nettyServerConfig;

    private final NettyServerHandler nettyServerHandler;

    public ServerHandlerInitializer(
        final NettyConnectManageHandler nettyConnectManageHandler, final DefaultEventLoopGroup defaultEventLoopGroup,
        final MessageLite lite, final NettyServerConfig nettyServerConfig, final NettyServerHandler nettyServerHandler) {
        this.nettyConnectManageHandler = nettyConnectManageHandler;
        this.defaultEventLoopGroup = defaultEventLoopGroup;
        this.lite = lite;
        this.nettyServerConfig = nettyServerConfig;
        this.nettyServerHandler = nettyServerHandler;
    }

    /**
     * This method will be called once the {@link Channel} was registered. After the method returns this instance will
     * be removed from the {@link ChannelPipeline} of the {@link Channel}.
     *
     * @param ch the {@link Channel} which was registered.
     * @throws Exception is thrown if an error occurs. In that case it will be handled by {@link
     *                   #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close the {@link
     *                   Channel}.
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {

        AnnotationUtils.validatorNotNull(this);

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(lite));
        pipeline.addLast(new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()));
        pipeline.addLast(nettyConnectManageHandler);
        pipeline.addLast(new NettyServerHandler());

    }
}
