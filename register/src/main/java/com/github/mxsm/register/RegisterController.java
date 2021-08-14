package com.github.mxsm.register;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.register.config.RegisterConfig;
import com.github.mxsm.register.mananger.MagpieBridgeManager;
import com.github.mxsm.register.mananger.MagpieBridgeOnlineKeepingService;
import com.github.mxsm.register.processor.DefaultRegisterRequestProcessor;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private final NettyServerConfig registerServerConfig;

    private final RegisterConfig registerConfig;

    private NettyRemotingServer registerServer;

    private ExecutorService executorService;

    private final ScheduledExecutorService scheduledExecutorService = Executors
        .newSingleThreadScheduledExecutor(new NamedThreadFactory("RegisterScheduledServiceThread"));

    private final MagpieBridgeManager magpieBridgeManager;

    private final MagpieBridgeOnlineKeepingService mbOnlineKeepingService;

    public RegisterController(final NettyServerConfig registerServerConfig, final RegisterConfig registerConfig) {
        this.registerServerConfig = registerServerConfig;
        this.registerConfig = registerConfig;
        this.magpieBridgeManager = new MagpieBridgeManager();
        this.mbOnlineKeepingService = new MagpieBridgeOnlineKeepingService(this.magpieBridgeManager);
    }

    public void initialize() {

        registerServer = new NettyRemotingServer(registerServerConfig, mbOnlineKeepingService);
        executorService = Executors.newFixedThreadPool(registerServerConfig.getServerWorkerThreads(),
            new NamedThreadFactory("RegisterWorkThread"));
        registerProcessor();

        scheduledExecutorService.scheduleAtFixedRate(() -> magpieBridgeManager.scanInactiveMagpieBridge(), 10, 10,
            TimeUnit.SECONDS);

    }

    public void startup() {
        registerServer.start();
    }

    public NettyServerConfig getRegisterServerConfig() {
        return registerServerConfig;
    }

    public RegisterConfig getRegisterConfig() {
        return registerConfig;
    }

    private void registerProcessor() {
        this.registerServer.registerDefaultProcessor(new DefaultRegisterRequestProcessor(this.magpieBridgeManager),
            this.executorService);
        LOGGER.debug("DefaultRegisterRequestProcessor register complete");
    }

}
