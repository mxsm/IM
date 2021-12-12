package com.github.mxsm.register.processor;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.register.mananger.MagpieBridgeManager;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2021/12/12 22:00
 * @Since 1.0.0
 */
public class ClientCommandProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCommandProcessor.class);

    private final MagpieBridgeManager magpieBridgeManager;

    public ClientCommandProcessor(final MagpieBridgeManager magpieBridgeManager) {
        this.magpieBridgeManager = magpieBridgeManager;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getCode(),
                NetUtils.parseChannelRemoteAddress(ctx.channel()));
        }

        RemotingCommand response;
        switch (request.getCode()) {
            case RequestCode.GET_MAGPIE_BRIDGE_ADDRESS:
                response = handleGetMagpieBridgeAddress(request);
                break;
            default:
                response = null;
        }
        return response;
    }


    private RemotingCommand  handleGetMagpieBridgeAddress(RemotingCommand request){


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
