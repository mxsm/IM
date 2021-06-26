package github.ant.mxsm.register;

import com.github.mxsm.remoting.netty.NettyServerConfig;
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

    public static void main(String[] args) {
        main0(args);
    }

    public static void main0(String[] args) {

        RegisterController registerController = createRegisterController(args);

        registerController.initialize();
        registerController.startup();

        LOGGER.info("----------------Register started-------------------");
    }

    private static RegisterController createRegisterController(String[] args){

        Options options = createOptions();
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(100);

        helpFormatter.printHelp("register", options,true);


        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        nettyServerConfig.setBindPort(9876);
        RegisterController controller = new RegisterController(nettyServerConfig);

        return controller;
    }

    private static Options createOptions(){
        Options options = new Options();

        Option helpOption = new Option("h", "help", false, "disable pagination of output");
        Option portOption = new Option("p", "port", false, "register bind port");
        Option configFileOption = new Option("c", "config", true, "register config file");
        options.addOption(helpOption).addOption(portOption).addOption(configFileOption);
        return options;
    }

}
