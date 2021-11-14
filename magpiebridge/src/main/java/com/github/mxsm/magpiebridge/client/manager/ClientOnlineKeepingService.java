package com.github.mxsm.magpiebridge.client.manager;

import com.github.mxsm.remoting.ChannelEventListener;
import com.github.mxsm.remoting.connection.Connection;
import com.github.mxsm.remoting.connection.ServerConnectionManager;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/7/11
 * @Since 0.1
 */
public class ClientOnlineKeepingService implements ChannelEventListener {

    private ServerConnectionManager connectionManager;

    public ClientOnlineKeepingService(final ServerConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * when the netty channel connect trigger onChannelConnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelConnect(String remoteAddress, Channel channel) {
        this.connectionManager.addConnection(new Connection(channel), remoteAddress);
     }

    /**
     * when the netty channel Disconnect trigger onChannelDisconnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelClose(String remoteAddress, Channel channel) {

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
