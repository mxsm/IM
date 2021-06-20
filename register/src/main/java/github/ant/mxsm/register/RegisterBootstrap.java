package github.ant.mxsm.register;

import com.github.mxsm.remoting.netty.NettyServerConfig;
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

        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        nettyServerConfig.setBindPort(9876);
        RegisterController controller = new RegisterController(nettyServerConfig);

        controller.initialize();
        controller.startup();

        LOGGER.info("----------------Register started-------------------");
    }

}
