package com.github.mxsm.remoting;

import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
public interface ChannelEventListener {

    /**
     * when the netty channel connect trigger onChannelConnect method
     * @param remoteAddress
     * @param channel
     */
    void onChannelConnect(final String remoteAddress, final Channel channel);

    /**
     * when the netty channel Disconnect trigger onChannelDisconnect method
     * @param remoteAddress
     * @param channel
     */
    void onChannelClose(final String remoteAddress, final Channel channel);

    /**
     * when the netty channel on exception trigger onChannelException
     * @param remoteAddress
     * @param channel
     */
    void  onChannelException(final String remoteAddress, final Channel channel);

    /**
     * when the netty channel idle trigger onChannelIdle
     * @param remoteAddress
     * @param channel
     */
    void onChannelIdle(final String remoteAddress, final Channel channel);
}
