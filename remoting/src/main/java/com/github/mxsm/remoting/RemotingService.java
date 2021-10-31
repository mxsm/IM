package com.github.mxsm.remoting;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public interface RemotingService extends Lifecycle{

    /**
     * start service
     */
    void start();

    /**
     * shutdown service
     */
    void shutdown();

}
