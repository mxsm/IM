package com.github.mxsm.remoting;

import java.net.InetSocketAddress;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 1.0.0
 */
public interface RemotingService extends LifeCycle {

    /**
     * start service
     */
    void start();

    /**
     * shutdown service
     */
    void shutdown();
}
