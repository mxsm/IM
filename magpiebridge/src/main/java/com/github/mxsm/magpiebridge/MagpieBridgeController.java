package com.github.mxsm.magpiebridge;

import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.service.MagpieBridgeAPI;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingClient;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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

   // private NettyRemotingClient magpieBridgeClient;

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
        //this.magpieBridgeClient = new NettyRemotingClient(this.nettyClientConfig);

        //启动定时发送MagpieBridge信息到注册中心
        //registerAllMagpieBridge();

    }

    private void registerMagpieBridgeAll() {


        MagpieBridgeInfo mbInfo = new MagpieBridgeInfo();
        mbInfo.setMagpieBridgeId(this.magpieBridgeConfig.getMagpieBridgeId());
        mbInfo.setMagpieBridgeAddress(NetUtils.getLocalAddress() + ":" + this.nettyServerConfig.getBindPort());
        mbInfo.setMagpieBridgeName(this.magpieBridgeConfig.getMagpieBridgeName());
        mbInfo.setMagpieBridgeCreateTimestamp(System.currentTimeMillis());

        this.magpieBridgeAPI.registerBrokerAll(mbInfo,this.magpieBridgeConfig.getMagpieBridgeRegisterTimeoutMills());

    }


    public void startup() {
        this.magpieBridgeServer.start();

    }

    public NettyServerConfig getNettyServerConfig() {
        return nettyServerConfig;
    }

    public MagpieBridgeConfig getMagpieBridgeConfig() {
        return magpieBridgeConfig;
    }
}
