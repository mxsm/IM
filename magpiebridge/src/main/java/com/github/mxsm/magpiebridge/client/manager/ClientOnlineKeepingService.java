package com.github.mxsm.magpiebridge.client.manager;

import com.github.mxsm.remoting.ChannelEventListener;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/7/11
 * @Since 0.1
 */
public class ClientOnlineKeepingService implements ChannelEventListener {

    private RemotingClientManager remotingClientManager;


    public ClientOnlineKeepingService(final RemotingClientManager remotingClientManager) {
        this.remotingClientManager = remotingClientManager;
    }

    /**
     * when the netty channel connect trigger onChannelConnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelConnect(String remoteAddress, Channel channel) {
        this.remotingClientManager.onChannelConnect(remoteAddress, channel);
     }

    /**
     * when the netty channel Disconnect trigger onChannelDisconnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelClose(String remoteAddress, Channel channel) {
        this.remotingClientManager.onChannelClose(remoteAddress, channel);
    }

    /**
     * when the netty channel on exception trigger onChannelException
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelException(String remoteAddress, Channel channel) {

    }

    /**
     * when the netty channel idle trigger onChannelIdle
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelIdle(String remoteAddress, Channel channel) {

    }
}
