package com.github.mxsm.magpiebridge;

import com.alibaba.fastjson.JSONObject;
import com.github.mxsm.common.Symbol;
import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.common.magpiebridge.MagpieBridgeRole;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.client.manager.ClientOnlineKeepingService;
import com.github.mxsm.magpiebridge.client.manager.DefaultServerConnectionManager;
import com.github.mxsm.magpiebridge.cluster.ClusterMetaData;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.processor.CtrlCommandProcessor;
import com.github.mxsm.magpiebridge.service.MagpieBridgeAPI;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.LifeCycle;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 0.1
 */
public class MagpieBridgeController implements LifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeController.class);

    private final NettyServerConfig nettyServerConfig;

    private final MagpieBridgeConfig magpieBridgeConfig;

    private final NettyClientConfig nettyClientConfig;

    private final ClientOnlineKeepingService clientOnlineKeepingService;

    private final ClusterMetaData clusterMetaData;

    private final String magpieBridgeAddress;

    private NettyRemotingServer magpieBridgeServer;

    private MagpieBridgeAPI magpieBridgeAPI;

    private ScheduledExecutorService magpieBridgeRegisterService;

    private ExecutorService messageProcessExecutor;

    private volatile boolean started = false;

    public MagpieBridgeController(final NettyServerConfig nettyServerConfig,
        final MagpieBridgeConfig magpieBridgeConfig, final NettyClientConfig nettyClientConfig) {

        this.nettyServerConfig = nettyServerConfig;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.nettyClientConfig = nettyClientConfig;
        this.clientOnlineKeepingService = new ClientOnlineKeepingService(new DefaultServerConnectionManager());
        this.clusterMetaData = new ClusterMetaData();
        this.magpieBridgeAddress = getMagpieBridgeAddress();
    }

    @Override
    public void init() {
        checkConfig();
        this.messageProcessExecutor = Executors.newFixedThreadPool(nettyServerConfig.getMessageProcessorThreads(),
            new NamedThreadFactory("MessageProcessorWorkThread"));
        this.magpieBridgeServer = new NettyRemotingServer(this.nettyServerConfig, this.clientOnlineKeepingService);
        this.magpieBridgeAPI = new MagpieBridgeAPI(this.nettyClientConfig, this.magpieBridgeAddress,
            this.magpieBridgeConfig);
        this.magpieBridgeRegisterService = Executors
            .newSingleThreadScheduledExecutor(new NamedThreadFactory("MagpieBridgeRegisterServiceThread"));
        //upate register service address
        List<String> rgtAddress = Arrays.asList(this.getMagpieBridgeConfig().getRegisterAddress().split(Symbol.COMMA));
        this.magpieBridgeAPI.updateRegisterAddressList(rgtAddress);

        // register request processor
        registerProcessor();

        //schedule config
        this.magpieBridgeRegisterService.scheduleAtFixedRate(() -> registerMagpieBridgeAll(), 10, 10, TimeUnit.SECONDS);
    }


    @Override
    public void start() {
        this.magpieBridgeServer.start();
        this.magpieBridgeAPI.start();
        boolean registerSuccess = this.registerMagpieBridgeAll();
        if (!registerSuccess) {
            System.exit(-1);
        }
        this.started = true;
    }

    public void shutdown() {

        this.unRegisterMagpieBridgeAll();
        this.magpieBridgeAPI.shutdown();
        this.magpieBridgeServer.shutdown();


    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    private void checkConfig() {
        if (this.magpieBridgeConfig.getMagpieBridgeRole() == MagpieBridgeRole.MASTER
            && this.magpieBridgeConfig.getMagpieBridgeId() != 0) {
            LOGGER.error("if magpie bridge the role is master, magpie bridge id must be zero(0), actually the id is"
                + this.magpieBridgeConfig.getMagpieBridgeId());
            System.exit(0);
        }
    }

    private boolean registerMagpieBridgeAll() {

        MagpieBridgeMetadata mbInfo = buildMagpieBridgeInfo();
        boolean isRegisterSuccess = false;
        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        List<RemotingCommand> responseCommands = this.magpieBridgeAPI
            .registerMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
        if (CollectionUtils.isEmpty(responseCommands)) {
            LOGGER.warn("register magpie bridge to registration center [{}] not response",
                this.magpieBridgeConfig.getRegisterAddress());
            return isRegisterSuccess;
        }

        for (RemotingCommand responseCommand : responseCommands) {
            if (null == responseCommand || responseCommand.getCode() != ResponseCode.SUCCESS) {
                continue;
            }
            RegisterMagpieBridgeResult result = JSONObject
                .parseObject(responseCommand.getPayload().toStringUtf8(), RegisterMagpieBridgeResult.class);
            if (null != result && StringUtils.isNotBlank(result.getMasterAddress())) {
                long magpieBridgeId = result.getMagpieBridgeId();
                long magpieBridgeMasterId = result.getMagpieBridgeMasterId();
                MagpieBridgeRole oldRole = this.magpieBridgeConfig.getMagpieBridgeRole();
                LOGGER.info(
                    "register magpie bridge to center success, current mb status [id={},role={},master-address={}]",
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
                        "register magpie bridge to center success and update status,new role [id={},role={}->{},master-address={}]",
                        this.magpieBridgeConfig.getMagpieBridgeId(), oldRole,
                        this.magpieBridgeConfig.getMagpieBridgeRole(), result.getMasterAddress());
                    isRegisterSuccess = true;
                    break;
                }
            }
        }

        return isRegisterSuccess;
    }

    private void unRegisterMagpieBridgeAll() {

        MagpieBridgeMetadata mbInfo = buildMagpieBridgeInfo();

        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        this.magpieBridgeAPI.unRegisterMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
    }

    private MagpieBridgeMetadata buildMagpieBridgeInfo() {
        MagpieBridgeMetadata mbInfo = new MagpieBridgeMetadata();
        mbInfo.setMagpieBridgeId(this.magpieBridgeConfig.getMagpieBridgeId());
        mbInfo.setMagpieBridgeAddress(this.magpieBridgeAddress);
        mbInfo.setMagpieBridgeName(this.magpieBridgeConfig.getMagpieBridgeName());
        mbInfo.setMagpieBridgeCreateTimestamp(System.currentTimeMillis());
        mbInfo.setMagpieBridgeClusterName(this.magpieBridgeConfig.getMagpieBridgeClusterName());
        mbInfo.setMagpieBridgeRole(this.getMagpieBridgeConfig().getMagpieBridgeRole());
        return mbInfo;
    }

    public NettyServerConfig getNettyServerConfig() {
        return nettyServerConfig;
    }

    public MagpieBridgeConfig getMagpieBridgeConfig() {
        return magpieBridgeConfig;
    }

    private String getMagpieBridgeAddress() {
        return NetUtils.getLocalAddress() + Symbol.COLON + this.nettyServerConfig.getBindPort();
    }

    private void registerProcessor() {
        CtrlCommandProcessor ctrlCp = new CtrlCommandProcessor();
        magpieBridgeServer.registerProcessor(RequestCode.CLIENT_CONNECT, ctrlCp, this.messageProcessExecutor);

    }
}
