package com.github.mxsm.register.processor;

import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.protobuf.ServerMetadata;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.register.exception.ServerHandleException;
import com.github.mxsm.register.mananger.ServerManager;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
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

    private final ServerManager serverManager;

    public DefaultRegisterRequestProcessor(final ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception {

        if (ctx != null) {
            LOGGER.debug("receive request from RequestCode[{}] IP[{}]", request.getCode(),
                    NetUtils.parseChannelRemoteAddress(ctx.channel()));
        }

        switch (request.getCode()) {
            case RequestCode.HEART_BEAT:
                return this.heartBeat(ctx, request);
            case RequestCode.SERVER_REGISTRY:
                return this.registryServer(ctx, request);
            case RequestCode.SERVER_UNREGISTRY:
                return this.unRegistryServer(ctx, request);
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

    private RemotingCommand registryServer(final ChannelHandlerContext ctx, final RemotingCommand request) {

        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());

        int payloadCrc32 = request.getPayloadCrc32();
        ByteString payload = request.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            LOGGER.warn("Payload CRC32 not Match");
            responseBuilder.setCode(ResponseCode.CRC32_MATCH_ERROR);
            responseBuilder.setResultMessage("payload CRC32 not Match");
            return responseBuilder.build();
        }

        ServerMetadata serverInfo = null;
        try {
            serverInfo = ServerMetadata.parseFrom(request.getPayload());
        } catch (InvalidProtocolBufferException e) {
            LOGGER.error("Parse bytes to ServerMetadata instance error",e);
            responseBuilder.setCode(ResponseCode.SYSTEM_ERROR);
            responseBuilder.setResultMessage("parse bytes to ServerMetadata instance error");
            return responseBuilder.build();
        }

        try {
            this.serverManager.registryServer(ctx.channel(), serverInfo);
        } catch (Exception e) {
            LOGGER.error("Register Server Fail", e);
            responseBuilder.setCode(ResponseCode.SYSTEM_ERROR);
            if(e instanceof ServerHandleException){
                responseBuilder.setResultMessage(((ServerHandleException)e).getErrorMessage());
            }else{
                responseBuilder.setResultMessage("Register Server Fail");
            }
        }
        responseBuilder.setCode(ResponseCode.SUCCESS);
        return responseBuilder.build();
    }

    private RemotingCommand unRegistryServer(final ChannelHandlerContext ctx, final RemotingCommand request) {

        RemotingCommand.Builder responseBuilder = RemotingCommandBuilder.buildResponseCommand(request.getCommandId());

        int payloadCrc32 = request.getPayloadCrc32();
        ByteString payload = request.getPayload();
        if (payloadCrc32 != 0 && payloadCrc32 != GeneralUtils.crc32(payload.toByteArray())) {
            LOGGER.warn("Payload CRC32 not Match");
            responseBuilder.setCode(ResponseCode.CRC32_MATCH_ERROR);
            responseBuilder.setResultMessage("payload CRC32 not Match");
            return responseBuilder.build();
        }

        ServerMetadata serverInfo;
        try {
            serverInfo = ServerMetadata.parseFrom(request.getPayload());
        } catch (InvalidProtocolBufferException e) {
            LOGGER.error("Parse bytes to ServerMetadata instance error",e);
            responseBuilder.setCode(ResponseCode.SYSTEM_ERROR);
            responseBuilder.setResultMessage("parse bytes to ServerMetadata instance error");
            return responseBuilder.build();
        }

        this.serverManager.unRegistryServer(ctx.channel(), serverInfo);

        return responseBuilder.setCode(ResponseCode.SUCCESS).build();
    }

    private RemotingCommand heartBeat(final ChannelHandlerContext ctx, final RemotingCommand request) {
        LOGGER.info("receive the remoting[{}] heart beat",NetUtils.parseChannelRemoteAddress(ctx.channel()));
        return RemotingCommandBuilder.buildResponseCommand().setCode(ResponseCode.SUCCESS).build();
    }
}
