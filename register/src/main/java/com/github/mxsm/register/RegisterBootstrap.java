package com.github.mxsm.register;

import com.github.mxsm.common.MixAll;
import com.github.mxsm.common.commandline.CommandlineUtils;
import com.github.mxsm.register.config.RegisterConfig;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import org.apache.commons.cli.*;
import org.apache.commons.collections4.properties.PropertiesFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class RegisterBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterBootstrap.class);

    public static final String CMD_NAME = "register";

    public static void main(String[] args) {
        main0(args);
    }

    public static void main0(String[] args) {

        RegisterController registerController = createRegisterController(args);

        LOGGER.info("----------------registration center is starting [centerName={},IP={},Port={}]-------------------",
            registerController.getRegisterConfig().getRegisterName(), NetUtils.getLocalAddress(),
            registerController.getRegisterServerConfig().getBindPort());
        registerController.initialize();
        registerController.startup();

        LOGGER.info("----------------registration center started [centerName={},IP={},Port={}]-------------------",
            registerController.getRegisterConfig().getRegisterName(), NetUtils.getLocalAddress(),
            registerController.getRegisterServerConfig().getBindPort());
    }

    private static RegisterController createRegisterController(String[] args) {

        NettyServerConfig serverConfig = new NettyServerConfig();
        RegisterConfig registerConfig = new RegisterConfig();

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
                MixAll.properties2Object(properties, serverConfig);
                MixAll.properties2Object(properties, registerConfig);
            } catch (IOException e) {
                LOGGER.error(String.format("Load Register config file [path=%s] Error", filePath), e);
                CommandlineUtils.printCommandLineHelp(CMD_NAME, options);
            }
        } else {
            CommandlineUtils.printCommandLineHelp(CMD_NAME, options);
        }

        if (cmdLine.hasOption("p")) {
            int port = CommandlineUtils.getOptionValue2Int(cmdLine, "p");
            serverConfig.setBindPort(port);
        }
        RegisterController controller = new RegisterController(serverConfig, registerConfig);

        return controller;
    }

    private static void addOptions(final Options options) {
        Option portOption = new Option("p", "port", true, "register bind port");
        Option configFileOption = new Option("c", "config", true, "register config file path");
        options.addOption(portOption).addOption(configFileOption);

    }

}
