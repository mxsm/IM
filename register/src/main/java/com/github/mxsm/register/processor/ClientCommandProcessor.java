package com.github.mxsm.register.processor;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.register.mananger.MagpieBridgeManager;
import com.github.mxsm.register.strategy.RandomSelectMagpieBridgeStrategy;
import com.github.mxsm.register.strategy.SelectMagpieBridgeStrategy;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import com.google.protobuf.ByteString;
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

    private SelectMagpieBridgeStrategy strategy = new RandomSelectMagpieBridgeStrategy();

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

        MagpieBridgeMetadata magpieBridge = this.magpieBridgeManager.getMagpieBridge(this.strategy);
        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());
        byte[] bytes = JSON.toJSONBytes(magpieBridge);
        int crc32 = GeneralUtils.crc32(bytes);
        responseBuilder.setPayloadCrc32(crc32);
        responseBuilder.setPayload(ByteString.copyFrom(bytes));
        return responseBuilder.setCode(ResponseCode.SUCCESS).build();
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
