package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.common.AnnotationUtils;
import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
public class ClientHandlerInitializer extends ChannelInitializer {

    private final ChannelEventListener channelEventListener;

    private final EventExecutorGroup eventExecutorGroup;

    public ClientHandlerInitializer(ChannelEventListener channelEventListener,
        EventExecutorGroup eventExecutorGroup) {
        this.channelEventListener = channelEventListener;
        this.eventExecutorGroup = eventExecutorGroup;
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

    }
}
