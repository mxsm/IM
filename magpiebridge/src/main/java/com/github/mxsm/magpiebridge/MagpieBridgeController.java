package com.github.mxsm.magpiebridge;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.Symbol;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.client.manager.ClientOnlineKeepingService;
import com.github.mxsm.magpiebridge.client.manager.DefaultServerConnectionManager;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.processor.BusinessCommandProcessor;
import com.github.mxsm.magpiebridge.processor.CtrlCommandProcessor;
import com.github.mxsm.magpiebridge.service.MagpieBridgeAPI;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.protobuf.ServerMetadata;
import com.github.mxsm.protocol.protobuf.ServerMetadata.Builder;
import com.github.mxsm.protocol.protobuf.constant.ServerType;
import com.github.mxsm.remoting.LifeCycle;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.connection.ServerConnectionManager;
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
 * @Since 1.0.0
 */
public class MagpieBridgeController implements LifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeController.class);

    private final NettyServerConfig nettyServerConfig;

    private final MagpieBridgeConfig magpieBridgeConfig;

    private final NettyClientConfig nettyClientConfig;

    private final ClientOnlineKeepingService clientOnlineKeepingService;

    private final String magpieBridgeAddress;

    private NettyRemotingServer magpieBridgeServer;

    private MagpieBridgeAPI magpieBridgeAPI;

    private ScheduledExecutorService magpieBridgeRegisterService;

    private ExecutorService messageProcessExecutor;

    private volatile boolean started = false;

    private ServerConnectionManager serverConnectionManager;

    public MagpieBridgeController(final NettyServerConfig nettyServerConfig,
        final MagpieBridgeConfig magpieBridgeConfig, final NettyClientConfig nettyClientConfig) {

        this.nettyServerConfig = nettyServerConfig;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.nettyClientConfig = nettyClientConfig;
        this.serverConnectionManager = new DefaultServerConnectionManager();
        this.clientOnlineKeepingService = new ClientOnlineKeepingService(this.serverConnectionManager);
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
    }


    @Override
    public void start() throws InterruptedException {
        this.magpieBridgeServer.start();
        this.magpieBridgeAPI.start();
        boolean registerSuccess = this.registerMagpieBridgeAll();
        if (!registerSuccess) {
            System.exit(-1);
        }
        this.started = true;

        //schedule config
        this.magpieBridgeRegisterService.scheduleAtFixedRate(() -> registerMagpieBridgeAll(), 10, 20, TimeUnit.SECONDS);
    }

    public void shutdown() {

        this.unRegisterMagpieBridgeAll();
        this.magpieBridgeAPI.shutdown();
        this.magpieBridgeServer.shutdown();


    }

    private void checkConfig() {
        if (this.magpieBridgeConfig.getMagpieBridgeId() != 0) {
            LOGGER.error("if magpie bridge the role is master, magpie bridge id must be zero(0), actually the id is"
                + this.magpieBridgeConfig.getMagpieBridgeId());
            System.exit(0);
        }
    }

    private boolean registerMagpieBridgeAll() {

        ServerMetadata mbInfo = buildMagpieBridgeInfo();
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
        }
        isRegisterSuccess = true;
        return isRegisterSuccess;
    }

    private void unRegisterMagpieBridgeAll() {

        ServerMetadata mbInfo = buildMagpieBridgeInfo();

        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        this.magpieBridgeAPI.unRegisterMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
    }

    private ServerMetadata buildMagpieBridgeInfo() {
        Builder mbInfoBuilder = ServerMetadata.newBuilder();
        mbInfoBuilder.setServerIp(StringUtils.isEmpty(nettyServerConfig.getBindIp()) ? NetUtils.getLocalAddress4Int()
            : NetUtils.address4Int(nettyServerConfig.getBindIp()));
        mbInfoBuilder.setServerPort(this.nettyServerConfig.getBindPort());
        mbInfoBuilder.setName(this.magpieBridgeConfig.getMagpieBridgeName());
        mbInfoBuilder.setServerType(ServerType.MAGPIE_BRIDGE);
        return mbInfoBuilder.build();
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
        magpieBridgeServer.registerProcessor(RequestCode.GET_MAGPIE_BRIDGE_ADDRESS, ctrlCp, this.messageProcessExecutor);

        BusinessCommandProcessor businessCp = new BusinessCommandProcessor();
        magpieBridgeServer.registerProcessor(RequestCode.SINGLE_CHAT,businessCp,this.messageProcessExecutor);
    }
}
