package github.ant.mxsm.register;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private NettyServerConfig  registerServerConfig;

    private NettyRemotingServer registerServer;

    public RegisterController(NettyServerConfig registerServerConfig) {
        this.registerServerConfig = registerServerConfig;
    }

    public void initialize(){

        registerServer = new NettyRemotingServer(registerServerConfig);
        

    }

    public void startup(){
        registerServer.start();
    }

}
