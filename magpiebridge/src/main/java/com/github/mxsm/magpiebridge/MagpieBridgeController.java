package com.github.mxsm.magpiebridge;

import com.github.mxsm.common.Symbol;
import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.common.magpiebridge.MagpieBridgeRole;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.client.manager.ClientOnlineKeepingService;
import com.github.mxsm.magpiebridge.cluster.ClusterMetaData;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.service.MagpieBridgeAPI;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 0.1
 */
public class MagpieBridgeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeController.class);

    private final NettyServerConfig nettyServerConfig;

    private final MagpieBridgeConfig magpieBridgeConfig;

    private final NettyClientConfig nettyClientConfig;

    private NettyRemotingServer magpieBridgeServer;

    private MagpieBridgeAPI magpieBridgeAPI;

    private final ClientOnlineKeepingService clientOnlineKeepingService;

    private final ClusterMetaData clusterMetaData;

    private ScheduledExecutorService magpieBridgeRegisterService = Executors
        .newSingleThreadScheduledExecutor(new NamedThreadFactory("MagpieBridgeRegisterService"));


    public MagpieBridgeController(final NettyServerConfig nettyServerConfig,
        final MagpieBridgeConfig magpieBridgeConfig, final NettyClientConfig nettyClientConfig) {
        this.nettyServerConfig = nettyServerConfig;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.nettyClientConfig = nettyClientConfig;
        this.magpieBridgeAPI = new MagpieBridgeAPI(this.nettyClientConfig);
        this.clientOnlineKeepingService = new ClientOnlineKeepingService();
        this.clusterMetaData = new ClusterMetaData();
    }

    public void initialize() {

        //before initialize check
        checkConfig();

        this.magpieBridgeServer = new NettyRemotingServer(this.nettyServerConfig, this.clientOnlineKeepingService);
        this.magpieBridgeAPI.updateRegisterAddressList(
            Arrays.asList(this.getMagpieBridgeConfig().getRegisterAddress().split(Symbol.COMMA)));

        //启动定时发送MagpieBridge信息到注册中心
        magpieBridgeRegisterService.scheduleAtFixedRate(() -> registerMagpieBridgeAll(), 10, 10, TimeUnit.SECONDS);
    }

    private void checkConfig() {
        if (this.magpieBridgeConfig.getMagpieBridgeRole() == MagpieBridgeRole.MASTER
            && this.magpieBridgeConfig.getMagpieBridgeId() != 0) {
            LOGGER.error("if magpie bridge the role is master, magpie bridge id must be zero(0), actually the id is"
                + this.magpieBridgeConfig.getMagpieBridgeId());
            System.exit(0);
        }
    }

    private void registerMagpieBridgeAll() {

        MagpieBridgeInfo mbInfo = buildMagpieBridgeInfo();

        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        List<RemotingCommand> responseCommands = this.magpieBridgeAPI
            .registerMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
        if(CollectionUtils.isEmpty(responseCommands)){
            LOGGER.warn("register magpie bridge to registration center [{}] not response",this.magpieBridgeConfig.getRegisterAddress());
            return;
        }
        for (RemotingCommand responseCommand : responseCommands){
            
        }
    }

    private void unRegisterMagpieBridgeAll() {

        MagpieBridgeInfo mbInfo = buildMagpieBridgeInfo();

        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        this.magpieBridgeAPI.unRegisterMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
    }

    private MagpieBridgeInfo buildMagpieBridgeInfo() {
        MagpieBridgeInfo mbInfo = new MagpieBridgeInfo();
        mbInfo.setMagpieBridgeId(this.magpieBridgeConfig.getMagpieBridgeId());
        mbInfo.setMagpieBridgeAddress(NetUtils.getLocalAddress() + ":" + this.nettyServerConfig.getBindPort());
        mbInfo.setMagpieBridgeName(this.magpieBridgeConfig.getMagpieBridgeName());
        mbInfo.setMagpieBridgeCreateTimestamp(System.currentTimeMillis());
        mbInfo.setMagpieBridgeClusterName(this.magpieBridgeConfig.getMagpieBridgeClusterName());
        mbInfo.setMagpieBridgeRole(this.getMagpieBridgeConfig().getMagpieBridgeRole());
        return mbInfo;
    }

    public void startup() {

        this.magpieBridgeServer.start();
        this.magpieBridgeAPI.start();

        this.registerMagpieBridgeAll();
    }

    public void shutdown() {

        this.unRegisterMagpieBridgeAll();
        this.magpieBridgeAPI.shutdown();
        this.magpieBridgeServer.shutdown();


    }

    public NettyServerConfig getNettyServerConfig() {
        return nettyServerConfig;
    }

    public MagpieBridgeConfig getMagpieBridgeConfig() {
        return magpieBridgeConfig;
    }
}
