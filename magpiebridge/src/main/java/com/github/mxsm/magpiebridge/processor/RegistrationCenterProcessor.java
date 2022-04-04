package com.github.mxsm.magpiebridge.processor;

import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/30
 * @Since
 */
public class RegistrationCenterProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCenterProcessor.class);

    private final MagpieBridgeConfig magpieBridgeConfig;

    private final String magpieBridgeAddress;

    public RegistrationCenterProcessor(MagpieBridgeConfig magpieBridgeConfig, String magpieBridgeAddress) {
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.magpieBridgeAddress = magpieBridgeAddress;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getCode(),
                NetUtils.parseChannelRemoteAddress(ctx.channel()));
        }

        switch (request.getCode()) {

            default:
                break;
        }

        return null;
    }

    /**
     * request is reject
     *
     * @return
     */
    @Override
    public boolean rejectRequest() {
        return false;
    }
}
