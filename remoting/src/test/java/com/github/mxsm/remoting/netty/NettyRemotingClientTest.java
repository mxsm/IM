package com.github.mxsm.remoting.netty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since
 */
class NettyRemotingClientTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void updateRegisterAddressList() {
    }

    @Test
    void invokeSync() {
    }

    @Test
    void invokeAsync() {
    }

    @Test
    void invokeOneway() {


    }

    @Test
    void start() {

        NettyRemotingClient client = new NettyRemotingClient(new NettyClientConfig());
        client.start();
        client.shutdown();

    }

    @Test
    void shutdown() {

        NettyRemotingClient client = new NettyRemotingClient(new NettyClientConfig());
        client.start();
        client.shutdown();

    }


}