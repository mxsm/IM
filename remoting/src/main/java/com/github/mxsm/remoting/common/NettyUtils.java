package com.github.mxsm.remoting.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 0.1
 */
public abstract class NettyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyUtils.class);

    public static void closeChannel(Channel channel) {
        final String remoteAddr = NetUtils.parseChannelRemoteAddress(channel);
        channel.close().addListener((ChannelFutureListener) future -> LOGGER
            .info("closeChannel: close the connection to remote address[{}] result: {}", remoteAddr,
                future.isSuccess()));
    }

}
