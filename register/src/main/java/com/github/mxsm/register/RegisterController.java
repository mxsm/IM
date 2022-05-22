package com.github.mxsm.register;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.Symbol;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.etcd.Etcd;
import com.github.mxsm.register.config.RegisterConfig;
import com.github.mxsm.register.mananger.MagpieBridgeOnlineKeepingService;
import com.github.mxsm.register.mananger.ServerManager;
import com.github.mxsm.register.processor.ClientCommandProcessor;
import com.github.mxsm.register.processor.DefaultRegisterRequestProcessor;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private final NettyServerConfig registerServerConfig;

    private final RegisterConfig registerConfig;

    private NettyRemotingServer registerServer;

    private ExecutorService executorService;

    private final ScheduledExecutorService scheduledExecutorService = Executors
        .newSingleThreadScheduledExecutor(new NamedThreadFactory("RegisterScheduledService-Thread"));

    private final ServerManager serverManager;

    private final MagpieBridgeOnlineKeepingService mbOnlineKeepingService;

    private Etcd etcd;

    public RegisterController(final NettyServerConfig registerServerConfig, final RegisterConfig registerConfig) {
        this.registerServerConfig = registerServerConfig;
        this.registerConfig = registerConfig;
        this.serverManager = new ServerManager();
        this.mbOnlineKeepingService = new MagpieBridgeOnlineKeepingService(this.serverManager);
    }

    public void initialize() {

        etcd = new Etcd(this.registerConfig.getEtcdEndpoints());

        registerServer = new NettyRemotingServer(registerServerConfig, mbOnlineKeepingService);
        executorService = Executors.newFixedThreadPool(registerServerConfig.getServerWorkerThreads(),
            new NamedThreadFactory("RegisterWorkThread"));
        registerProcessor();

      //  scheduledExecutorService.scheduleAtFixedRate(() -> serverManager.scanInactiveServer(), 10, 30, TimeUnit.SECONDS);

    }

    public void startup() {

        try {
            registerServer.start();
        } catch (InterruptedException e) {
            LOGGER.error("RegisterServer start error", e);
            System.exit(1);
        }
        //registry register to CoreDNS
        boolean registrySuccess = registry2CoreDNS();
        if (!registrySuccess) {
            registerServer.shutdown();
            System.exit(1);
        }
    }

    public void shutdown() {

        unRegistry2CoreDNS();

        registerServer.shutdown();

    }


    public NettyServerConfig getRegisterServerConfig() {
        return registerServerConfig;
    }

    public RegisterConfig getRegisterConfig() {
        return registerConfig;
    }

    private void registerProcessor() {
        this.registerServer.registerDefaultProcessor(new DefaultRegisterRequestProcessor(this.serverManager),
            this.executorService);
        LOGGER.debug("DefaultRegisterRequestProcessor register completed");
        this.registerServer.registerProcessor(RequestCode.GET_MAGPIE_BRIDGE_ADDRESS,
            new ClientCommandProcessor(this.serverManager), this.executorService);
        LOGGER.debug("ClientCommandProcessor register completed");
    }

    private boolean registry2CoreDNS() {

        StringBuilder key = createRegisterCoreDNSKey();
        String host = StringUtils.isEmpty(registerServerConfig.getBindIp()) ? NetUtils.getLocalAddress()
            : registerServerConfig.getBindIp();
        StringBuilder value = new StringBuilder();
        value.append(JSON.toJSONString(new CoreNDSValue(host, 60)));

        boolean registrySuccess = this.etcd.put(key.toString(), value.toString());
        if (registrySuccess) {
            LOGGER.info("register[{}] registry to CoreNDS, ETCD key[{}], value[{}]",
                registerConfig.getRegisterName(), key, value);
        }
        return registrySuccess;
    }

    private StringBuilder createRegisterCoreDNSKey() {
        StringBuilder key = new StringBuilder(this.registerConfig.getCoreDNSEtcdPath());
        String host = StringUtils.isEmpty(registerServerConfig.getBindIp()) ? NetUtils.getLocalAddress()
            : registerServerConfig.getBindIp();
        int port = this.registerServerConfig.getBindPort();
        key.append("/").append(Lists.reverse(Arrays.asList(this.registerConfig.getDomainName().split(Symbol.DOT)))
            .stream().collect(Collectors.joining("/"))).append("/").append(host).append(":").append(port);
        return key;
    }

    private void unRegistry2CoreDNS() {
        String key = createRegisterCoreDNSKey().toString();
        boolean delete = etcd.delete(key);
        if (delete) {
            LOGGER.info("Register[{}] unRegistry to CoreNDS, ETCD key[{}]", registerConfig.getRegisterName(), key);
        }
    }

    class CoreNDSValue {

        private String host;

        private int ttl;

        public CoreNDSValue(String host, int ttl) {
            this.host = host;
            this.ttl = ttl;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }
    }
}
