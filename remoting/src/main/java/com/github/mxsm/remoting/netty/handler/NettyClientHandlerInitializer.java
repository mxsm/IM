package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.netty.NettyClientConfig;
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
public class NettyClientHandlerInitializer extends ChannelInitializer {

    private final ChannelEventListener channelEventListener;

    private final EventExecutorGroup eventExecutorGroup;

    private final MessageLite lite = RemotingCommand.getDefaultInstance();

    private final NettyClientConfig nettyClientConfig;

    private final NettyRemotingHandler nettyRemoting;

    public NettyClientHandlerInitializer(final ChannelEventListener channelEventListener,
        final EventExecutorGroup eventExecutorGroup, final NettyClientConfig nettyClientConfig,
        final NettyRemotingHandler nettyRemoting) {
        this.channelEventListener = channelEventListener;
        this.eventExecutorGroup = eventExecutorGroup;
        this.nettyClientConfig = nettyClientConfig;
        this.nettyRemoting = nettyRemoting;

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
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(eventExecutorGroup,
            new IdleStateHandler(0, 0, nettyClientConfig.getClientChannelMaxIdleTimeSeconds()));
        pipeline.addLast(eventExecutorGroup, new ProtobufVarint32FrameDecoder());
        pipeline.addLast(eventExecutorGroup, new ProtobufDecoder(this.lite));
        pipeline.addLast(eventExecutorGroup, new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(eventExecutorGroup, new ProtobufEncoder());
        pipeline.addLast(eventExecutorGroup, new NettyClientConnectManageHandler(this.channelEventListener));
        pipeline.addLast(eventExecutorGroup, new NettyClientHandler(this.nettyRemoting));
    }
}
