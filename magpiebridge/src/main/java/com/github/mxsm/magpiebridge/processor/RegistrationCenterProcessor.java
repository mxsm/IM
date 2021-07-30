package com.github.mxsm.magpiebridge.processor;

import com.alibaba.fastjson.JSONObject;
import com.github.mxsm.common.magpiebridge.MagpieBridgeRole;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.netty.AsyncNettyRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRequestProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
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
            case RequestCode.MAGPIE_BRIDGE_MASTER_CHANGE:
                return doHandleMasterRoleChange(request);
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

    private RemotingCommand doHandleMasterRoleChange(RemotingCommand request) {

        RegisterMagpieBridgeResult result = JSONObject.parseObject(request.getPayload().toStringUtf8(),
            RegisterMagpieBridgeResult.class);
        if (null != result && StringUtils.isNotBlank(result.getMasterAddress())) {
            long magpieBridgeId = result.getMagpieBridgeId();
            long magpieBridgeMasterId = result.getMagpieBridgeMasterId();
            MagpieBridgeRole oldRole = this.magpieBridgeConfig.getMagpieBridgeRole();
            LOGGER.info("receive message from register about magpie bridge role change success, current mb status [id={},role={},master-address={}]",
                this.magpieBridgeConfig.getMagpieBridgeId(), oldRole, result.getMasterAddress());
            if (magpieBridgeId != RegisterMagpieBridgeResult.NO_MASTER
                && magpieBridgeMasterId != RegisterMagpieBridgeResult.NO_MASTER) {
                this.magpieBridgeConfig.setMagpieBridgeId(magpieBridgeId);
                if (StringUtils.equals(result.getMasterAddress(), this.magpieBridgeAddress)
                    && magpieBridgeId == magpieBridgeMasterId) {
                    this.magpieBridgeConfig.setMagpieBridgeRole(MagpieBridgeRole.MASTER.name());
                } else {
                    this.magpieBridgeConfig.setMagpieBridgeRole(MagpieBridgeRole.SLAVE.name());
                }
                LOGGER.info(
                    "receive message from register about magpie bridge role change success and update status,new role [id={},role={}->{},master-address={}]",
                    this.magpieBridgeConfig.getMagpieBridgeId(), oldRole, this.magpieBridgeConfig.getMagpieBridgeRole(),
                    result.getMasterAddress());
            }
        }
        return null;
    }
}
