package com.github.mxsm.magpiebridge;

import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingClient;
import com.github.mxsm.remoting.netty.NettyRemotingServer;
import com.github.mxsm.remoting.netty.NettyServerConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 0.1
 */
public class MagpieBridgeController {

    private final NettyServerConfig nettyServerConfig;

    private final MagpieBridgeConfig magpieBridgeConfig;

    private final NettyClientConfig nettyClientConfig;

    private NettyRemotingClient magpieBridgeClient;

    private NettyRemotingServer magpieBridgeServer;

    private ScheduledExecutorService magpieBridgeRegisterService = Executors
        .newSingleThreadScheduledExecutor(new NamedThreadFactory("MagpieBridgeRegisterService"));;

    public MagpieBridgeController(final NettyServerConfig nettyServerConfig,
        final MagpieBridgeConfig magpieBridgeConfig, final NettyClientConfig nettyClientConfig) {
        this.nettyServerConfig = nettyServerConfig;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.nettyClientConfig = nettyClientConfig;
    }

    public void initialize() {
        this.magpieBridgeServer = new NettyRemotingServer(this.nettyServerConfig);
        this.magpieBridgeClient = new NettyRemotingClient(this.nettyClientConfig);

        this.magpieBridgeRegisterService.scheduleAtFixedRate(() -> {

   /*         try {
                magpieBridgeClient.invokeSync("127.0.0.1:8888", RemotingCommandBuilder.buildRequestCommand(), 10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemotingConnectException e) {
                e.printStackTrace();
            } catch (RemotingSendRequestException e) {
                e.printStackTrace();
            } catch (RemotingTimeoutException e) {
                e.printStackTrace();
            }

        }, 5, 10, TimeUnit.SECONDS);*/

    }


    public void startup() {
        this.magpieBridgeServer.start();
        this.magpieBridgeClient.start();

    }

    public NettyServerConfig getNettyServerConfig() {
        return nettyServerConfig;
    }

    public MagpieBridgeConfig getMagpieBridgeConfig() {
        return magpieBridgeConfig;
    }
}
