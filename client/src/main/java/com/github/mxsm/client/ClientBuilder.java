package com.github.mxsm.client;

import com.github.mxsm.client.config.ImClientConfig;
import com.github.mxsm.remoting.netty.NettyClientConfig;

/**
 * @author mxsm
 * @date 2021/10/10 15:38
 * @Since 1.0.0
 */
public class ClientBuilder {

    private ImClientConfig imClientConfig = new ImClientConfig();

    private NettyClientConfig nettyClientConfig = new NettyClientConfig();

    private static ClientBuilder DEFAULT_INSTANCE = new ClientBuilder();

    private ClientBuilder() {

    }

    public static ClientBuilder newBuilder(){
        return DEFAULT_INSTANCE;
    }

    public  ClientBuilder setMagpieBridgeAddress(String address) {
        DEFAULT_INSTANCE.getImClientConfig().setMagpieBridgeAddress(address);
        return DEFAULT_INSTANCE;
    }

    public ClientBuilder setMagpiebridgePort(int port) {
        DEFAULT_INSTANCE.getImClientConfig().setMagpiebridgePort(port);
        return DEFAULT_INSTANCE;
    }

    public static ImClient build() {
        return new ImClientImpl(DEFAULT_INSTANCE.getNettyClientConfig(), DEFAULT_INSTANCE.getImClientConfig());
    }


    private ImClientConfig getImClientConfig() {
        return imClientConfig;
    }

    private NettyClientConfig getNettyClientConfig() {
        return nettyClientConfig;
    }
}
