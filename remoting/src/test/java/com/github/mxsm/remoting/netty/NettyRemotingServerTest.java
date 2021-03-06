package com.github.mxsm.remoting.netty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since
 */
class NettyRemotingServerTest {

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
        boolean started = true;
        try {
            NettyRemotingServer server = new NettyRemotingServer(new NettyServerConfig());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            started = false;
        }
        Assertions.assertTrue(started);
    }

    @Test
    void shutdown() {
        boolean shutdown = true;
        try {
            NettyRemotingServer server = new NettyRemotingServer(new NettyServerConfig());
            server.start();
            server.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            shutdown = false;
        }
        Assertions.assertTrue(shutdown);
    }
}