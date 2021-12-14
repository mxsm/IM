package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.common.utils.AnnotationUtils;
import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
public class NettyServerHandlerInitializer extends ChannelInitializer {

    @NotNull
    private final NettyServerConnectManageHandler nettyServerConnectManageHandler;

    @NotNull
    private final EventExecutorGroup eventExecutorGroup;

    @NotNull
    private final MessageLite lite = RemotingCommand.getDefaultInstance();

    private final NettyServerConfig nettyServerConfig;

    private final NettyServerHandler nettyServerHandler;

    private final ProtobufEncoder protobufEncoder = new ProtobufEncoder();

    private final ProtobufVarint32LengthFieldPrepender fieldPrepender = new ProtobufVarint32LengthFieldPrepender();

    public NettyServerHandlerInitializer(final NettyRemotingHandler nettyRemoting,final EventExecutorGroup eventExecutorGroup,
        final NettyServerConfig nettyServerConfig, final NettyServerHandler nettyServerHandler) {
        this.nettyServerConnectManageHandler = new NettyServerConnectManageHandler(nettyRemoting);
        this.eventExecutorGroup = eventExecutorGroup;
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
        pipeline.addLast(eventExecutorGroup,
            new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()));
        pipeline.addLast(eventExecutorGroup, new ProtobufVarint32FrameDecoder());
        pipeline.addLast(eventExecutorGroup, new ProtobufDecoder(lite));
        pipeline.addLast(eventExecutorGroup, fieldPrepender);
        pipeline.addLast(eventExecutorGroup, protobufEncoder);
        pipeline.addLast(eventExecutorGroup, nettyServerConnectManageHandler);
        pipeline.addLast(eventExecutorGroup, nettyServerHandler);
        
    }
}
