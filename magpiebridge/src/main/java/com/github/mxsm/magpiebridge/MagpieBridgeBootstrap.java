package com.github.mxsm.magpiebridge;

import com.github.mxsm.common.commandline.CommandlineUtils;
import com.github.mxsm.common.utils.AnnotationUtils;
import com.github.mxsm.common.utils.MixAll;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.collections4.properties.PropertiesFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 1.0.0
 */
public class MagpieBridgeBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeBootstrap.class);

    public static final String CMD_NAME = "MagpieBridge";

    public static void main(String[] args) {
        main0(args);
    }

    private static void main0(String[] args) {

        MagpieBridgeController magpieBridgeController = createRegisterController(args);

        magpieBridgeController.init();
        try {
            magpieBridgeController.start();
        } catch (InterruptedException e) {
           LOGGER.error("MagpieBridgeController start error",e);
           System.exit(-1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            private volatile boolean hasShutDown = false;

            @Override
            public void run() {
                synchronized (this) {
                    LOGGER.info("MagpieBridge ShutdownHook was invoked");
                    if (!this.hasShutDown) {
                        this.hasShutDown = true;
                        long startTime = System.currentTimeMillis();
                        magpieBridgeController.shutdown();
                        long endTime = System.currentTimeMillis();
                        LOGGER.info("MagpieBridge ShutdownHook Spend time:{}ms", endTime - startTime);
                    }
                }

            }
        }, "MagpieBridgeShutdownHook-Thread"));

        LOGGER.info("----------------MagpieBridge started [IP={},Port={}]-------------------",
            StringUtils.isEmpty(magpieBridgeController.getNettyServerConfig().getBindIp()) ? NetUtils.getLocalAddress()
                : magpieBridgeController.getNettyServerConfig().getBindIp(),
            magpieBridgeController.getNettyServerConfig().getBindPort());

    }

    private static MagpieBridgeController createRegisterController(String[] args) {

        final NettyServerConfig nettyServerConfig = new NettyServerConfig();
        final NettyClientConfig nettyClientConfig = new NettyClientConfig();
        final MagpieBridgeConfig magpieBridgeConfig = new MagpieBridgeConfig();

        CommandLineParser parser = new DefaultParser();
        Options options = CommandlineUtils.buildCommandlineOptions();
        addOptions(options);
        CommandLine cmdLine = CommandlineUtils.parseCmdLine(CMD_NAME, args, options, parser);

        if (cmdLine.hasOption("c")) {
            String filePath = CommandlineUtils.getOptionValue(cmdLine, "c");
            if (StringUtils.isBlank(filePath)) {
                LOGGER.error("Register Config file path is blank");
                CommandlineUtils.printCommandLineHelp(CMD_NAME, options);
            }
            try {
                Properties properties = PropertiesFactory.INSTANCE.load(filePath);
                MixAll.properties2Object(properties, nettyServerConfig);
                MixAll.properties2Object(properties, magpieBridgeConfig);
                MixAll.properties2Object(properties, nettyClientConfig);
            } catch (IOException e) {
                LOGGER.error(String.format("Load Register config file [path=%s] Error", filePath), e);
                CommandlineUtils.printCommandLineHelp(CMD_NAME, options);
            }
        } else {
            CommandlineUtils.printCommandLineHelp(CMD_NAME, options);
        }

        if (cmdLine.hasOption("p")) {
            int port = CommandlineUtils.getOptionValue2Int(cmdLine, "p");
            nettyServerConfig.setBindPort(port);
        }

        if (cmdLine.hasOption("r")) {
            String registerAddrs = CommandlineUtils.getOptionValue(cmdLine, "r");
            magpieBridgeConfig.setRegisterAddress(registerAddrs);
        }

        AnnotationUtils.validatorNotNull(nettyClientConfig, magpieBridgeConfig, nettyClientConfig);

        return new MagpieBridgeController(nettyServerConfig, magpieBridgeConfig, nettyClientConfig);
    }

    private static void addOptions(final Options options) {
        Option portOption = new Option("p", "port", true, "MagpieBridge bind port");
        Option configFileOption = new Option("c", "config", true, "MagpieBridge config file path");
        Option registerOption = new Option("r", "register-address", true,
            "register address,example:127.0.0.1:8080,192.168.10.16:8080");
        options.addOption(portOption).addOption(configFileOption).addOption(registerOption);

    }
}
