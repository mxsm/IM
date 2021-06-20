package com.github.mxsm.remoting.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
public class ServerHandlerInitializer extends ChannelInitializer {

    private NettyConnectManageHandler nettyConnectManageHandler;

    private DefaultEventLoopGroup defaultEventLoopGroup;

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

        ch.pipeline().addLast(defaultEventLoopGroup);
        


    }
}
