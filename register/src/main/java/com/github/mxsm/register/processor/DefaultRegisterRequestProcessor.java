package com.github.mxsm.register.processor;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.register.mananger.MagpieBridgeManager;
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
 * @Date 2021/7/1
 * @Since 1.0.0
 */
public class DefaultRegisterRequestProcessor implements NettyRequestProcessor, AsyncNettyRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisterRequestProcessor.class);

    private final MagpieBridgeManager magpieBridgeManager;

    public DefaultRegisterRequestProcessor(final MagpieBridgeManager magpieBridgeManager) {
        this.magpieBridgeManager = magpieBridgeManager;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getCode(),
                    NetUtils.parseChannelRemoteAddress(ctx.channel()));
        }

        switch (request.getCode()) {
            case RequestCode.HEART_BEAT:
                return this.heartbeat(ctx, request);
            case RequestCode.MAGPIE_BRIDGE_REGISTER:
                return this.registerMagpieBridge(ctx, request);
            case RequestCode.MAGPIE_BRIDGE_UNREGISTER:
                return this.unRegisterMagpieBridge(ctx, request);
            default:
                break;
        }

        return null;
    }

    /**
     * 请求是否抛弃
     *
     * @return
     */
    @Override
    public boolean rejectRequest() {
        return false;
    }

    private RemotingCommand registerMagpieBridge(final ChannelHandlerContext ctx, final RemotingCommand request) {

        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());

        int payloadCrc32 = request.getPayloadCrc32();
        ByteString payload = request.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            LOGGER.warn("Payload CRC32 not Match");
            responseBuilder.setCode(ResponseCode.CRC32_MATCH_ERROR);
            responseBuilder.setResultMessage("payload CRC32 not Match");
            return responseBuilder.build();
        }

        MagpieBridgeMetadata mbInfo = JSON.parseObject(payload.toByteArray(), MagpieBridgeMetadata.class);

        RegisterMagpieBridgeResult result = this.magpieBridgeManager.registerMagpieBridge(ctx.channel(), mbInfo);

        return responseBuilder.setCode(ResponseCode.SUCCESS).setPayload(ByteString.copyFrom(JSON.toJSONBytes(result))).build();
    }

    private RemotingCommand unRegisterMagpieBridge(final ChannelHandlerContext ctx, final RemotingCommand request) {

        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());

        int payloadCrc32 = request.getPayloadCrc32();
        ByteString payload = request.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            LOGGER.warn("Payload CRC32 not Match");
            responseBuilder.setCode(ResponseCode.CRC32_MATCH_ERROR);
            responseBuilder.setResultMessage("payload CRC32 not Match");
            return responseBuilder.build();
        }

        MagpieBridgeMetadata mbInfo = JSON.parseObject(payload.toByteArray(), MagpieBridgeMetadata.class);

        this.magpieBridgeManager.unRegisterMagpieBridge(ctx.channel(), mbInfo);

        return responseBuilder.setCode(ResponseCode.SUCCESS).build();
    }

    private RemotingCommand heartbeat(final ChannelHandlerContext ctx, final RemotingCommand request) {
        LOGGER.info("receive the remoting[{}] heart beat",NetUtils.parseChannelRemoteAddress(ctx.channel()));
        return RemotingCommandBuilder.buildResponseCommand().setCode(ResponseCode.SUCCESS).build();
    }
}
