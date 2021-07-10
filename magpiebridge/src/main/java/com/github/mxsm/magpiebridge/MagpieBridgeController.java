package com.github.mxsm.magpiebridge;

import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.service.MagpieBridgeAPI;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
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


    private ScheduledExecutorService magpieBridgeRegisterService = Executors
        .newSingleThreadScheduledExecutor(new NamedThreadFactory("MagpieBridgeRegisterService"));
    ;

    public MagpieBridgeController(final NettyServerConfig nettyServerConfig,
        final MagpieBridgeConfig magpieBridgeConfig, final NettyClientConfig nettyClientConfig) {
        this.nettyServerConfig = nettyServerConfig;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.nettyClientConfig = nettyClientConfig;
        this.magpieBridgeAPI = new MagpieBridgeAPI(this.nettyClientConfig);
    }

    public void initialize() {
        this.magpieBridgeServer = new NettyRemotingServer(this.nettyServerConfig);

        this.magpieBridgeAPI.updateRegisterAddressList(
            Arrays.asList(this.getMagpieBridgeConfig().getRegisterAddress().split(",")));

        //启动定时发送MagpieBridge信息到注册中心
        magpieBridgeRegisterService.scheduleAtFixedRate(() -> registerMagpieBridgeAll(), 10, 10, TimeUnit.SECONDS);
    }

    private void registerMagpieBridgeAll() {

        MagpieBridgeInfo mbInfo = buildMagpieBridgeInfo();

        long magpieBridgeRegisterTimeoutMills = this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills();
        this.magpieBridgeAPI.registerMagpieBridgeAll(mbInfo, magpieBridgeRegisterTimeoutMills);
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
        return mbInfo;
    }

    public void startup() {

        this.magpieBridgeServer.start();
        this.magpieBridgeAPI.start();

        this.registerMagpieBridgeAll();
    }

    public void shutdown(){

        this.magpieBridgeAPI.shutdown();
        this.magpieBridgeServer.shutdown();
        this.unRegisterMagpieBridgeAll();

    }

    public NettyServerConfig getNettyServerConfig() {
        return nettyServerConfig;
    }

    public MagpieBridgeConfig getMagpieBridgeConfig() {
        return magpieBridgeConfig;
    }
}
