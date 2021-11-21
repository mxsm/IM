package com.github.mxsm.magpiebridge.processor;

import com.github.mxsm.common.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
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
 * @date 2021/11/21 15:53
 * @Since 1.0.0
 */
public class CtrlCommandProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCenterProcessor.class);

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getCode(),
                NetUtils.parseChannelRemoteAddress(ctx.channel()));
        }
        RemotingCommand response;
        switch (request.getCode()) {
            case RequestCode.CLIENT_CONNECT:
                response = handleConnect(request);
                break;
            default:
                response = null;
        }
        return response;
    }

    private RemotingCommand handleConnect(RemotingCommand request) {
        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());
        int payloadCrc32 = request.getPayloadCrc32();
        ByteString payload = request.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            LOGGER.warn("Payload CRC32 not Match");
            responseBuilder.setCode(ResponseCode.CRC32_MATCH_ERROR);
            responseBuilder.setResultMessage("payload CRC32 not Match");
            return responseBuilder.build();
        }
        responseBuilder.setCode(ResponseCode.ACK);
        return responseBuilder.build();
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
