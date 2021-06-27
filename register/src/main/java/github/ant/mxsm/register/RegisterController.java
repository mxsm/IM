package github.ant.mxsm.register;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private final NettyServerConfig registerServerConfig;

    private NettyRemotingServer registerServer;

    private ExecutorService executorService;


    public RegisterController(final NettyServerConfig registerServerConfig) {
        this.registerServerConfig = registerServerConfig;
    }

    public void initialize() {

        registerServer = new NettyRemotingServer(registerServerConfig);
        executorService = Executors.newFixedThreadPool(registerServerConfig.getServerWorkerThreads(),
            new NamedThreadFactory("RegisterWorkThread"));
    }

    public void startup() {
        registerServer.start();
    }

    public NettyServerConfig getRegisterServerConfig() {
        return registerServerConfig;
    }

    private void registerProcessor() {
        this.registerServer.registerProcessor(0, null, this.executorService);
    }

}
