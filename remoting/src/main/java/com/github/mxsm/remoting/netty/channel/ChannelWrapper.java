package com.github.mxsm.remoting.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author mxsm
 * @Date 2021/6/23
 * @Since 1.0.0
 */
public class ChannelWrapper {

    private final ChannelFuture channelFuture;

    private ChannelWrapper(final ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public static ChannelWrapper builder(final ChannelFuture channelFuture){
        return new ChannelWrapper(channelFuture);
    }

    public Channel getChannel(){
        return this.channelFuture.channel();
    }

    public ChannelFuture getChannelFuture() {
        return this.channelFuture;
    }

    public boolean isWriteable(){
        Channel channel = this.channelFuture.channel();
        return channel != null && channel.isWritable();
    }

    public boolean isOK(){
        Channel channel = this.channelFuture.channel();
        return channel != null && channel.isActive();
    }
}
