package com.github.mxsm.magpiebridge.client.manager;

import io.netty.channel.Channel;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/11
 * @Since 1.0.0
 */
public class RemotingClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemotingClientManager.class);

    //connection number of client
    private AtomicLong connections = new AtomicLong(0);

    private RemotingClientMetadata remotingClientMetadata;

    public long incrementConnection() {
        return connections.incrementAndGet();
    }

    public long decrementConnection() {
        return connections.decrementAndGet();
    }

    /**
     * when the netty channel connect trigger onChannelConnect method
     *
     * @param remoteAddress
     * @param channel
     */
    public void onChannelConnect(String remoteAddress, Channel channel) {
        long currentConnections = connections.incrementAndGet();
        LOGGER.info("Remote client[{}] connect to this magpiebridge, magpiebridge hold connections [{}]", remoteAddress,
            currentConnections);
    }

    /**
     * when the netty channel Disconnect trigger onChannelDisconnect method
     *
     * @param remoteAddress
     * @param channel
     */
    public void onChannelClose(String remoteAddress, Channel channel) {
        long currentConnections = connections.decrementAndGet();
        LOGGER.info("Remote client[{}] disconnect to this magpiebridge, magpiebridge hold connections [{}]", remoteAddress,
            currentConnections);
    }

    /**
     * when the netty channel on exception trigger onChannelException
     *
     * @param remoteAddress
     * @param channel
     */

    public void onChannelException(String remoteAddress, Channel channel) {
        //
    }
}
