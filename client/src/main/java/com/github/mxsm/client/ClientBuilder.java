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


    public static ClientBuilder newBuilder(){
        return new ClientBuilder();
    }

    public  ClientBuilder setMagpieBridgeAddress(String address) {
        this.imClientConfig.setMagpieBridgeAddress(address);
        return this;
    }

    public ClientBuilder setMagpiebridgePort(int port) {
        this.imClientConfig.setMagpiebridgePort(port);
        return this;
    }

    public ImClient build() {
        try {
            ImClientImpl imClient = new ImClientImpl(this.nettyClientConfig, this.imClientConfig);
            imClient.start();
            return imClient;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
