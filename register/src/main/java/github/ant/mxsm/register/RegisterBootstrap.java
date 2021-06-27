package github.ant.mxsm.register;

import com.github.mxsm.common.commandline.CommandlineUtils;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        registerController.initialize();
        registerController.startup();

        LOGGER.info("----------------Register started-------------------");
    }

    private static RegisterController createRegisterController(String[] args) {

        CommandLineParser parser = new DefaultParser();
        Options options = CommandlineUtils.buildCommandlineOptions();
        options = createOptions(options);
        CommandLine cmdLine = CommandlineUtils.parseCmdLine(CMD_NAME, args, options, parser);

        if(cmdLine.hasOption("p")){

        }

        if(cmdLine.hasOption("c")){

        }else {
            CommandlineUtils.printCommandLineHelp(CMD_NAME,options);
        }


        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        nettyServerConfig.setBindPort(9876);
        RegisterController controller = new RegisterController(nettyServerConfig);

        return controller;
    }

    private static Options createOptions(final Options options) {
        Option portOption = new Option("p", "port", false, "register bind port");
        Option configFileOption = new Option("c", "config", true, "register config file");
        options.addOption(portOption).addOption(configFileOption);
        return options;
    }

}
