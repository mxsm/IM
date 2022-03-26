package com.github.mxsm.register.mananger;

import com.github.mxsm.remoting.ChannelEventListener;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
public class MagpieBridgeOnlineKeepingService implements ChannelEventListener {

    private MagpieBridgeManager magpieBridgeManager;

    public MagpieBridgeOnlineKeepingService(MagpieBridgeManager magpieBridgeManager) {
        this.magpieBridgeManager = magpieBridgeManager;
    }

    public MagpieBridgeManager getMagpieBridgeManager() {
        return magpieBridgeManager;
    }

    /**
     * when the netty channel connect trigger onChannelConnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelConnect(String remoteAddress, Channel channel) {
        // nothing to do here
    }

    /**
     * when the netty channel Disconnect trigger onChannelDisconnect method
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelClose(String remoteAddress, Channel channel) {
        this.magpieBridgeManager.closeChannelOnException(remoteAddress, channel);
    }

    /**
     * when the netty channel on exception trigger onChannelException
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelException(String remoteAddress, Channel channel) {
        this.magpieBridgeManager.closeChannelOnException(remoteAddress, channel);
    }

    /**
     * when the netty channel idle trigger onChannelIdle
     *
     * @param remoteAddress
     * @param channel
     */
    @Override
    public void onChannelIdle(String remoteAddress, Channel channel) {
        this.magpieBridgeManager.closeChannelOnException(remoteAddress, channel);
    }
}
