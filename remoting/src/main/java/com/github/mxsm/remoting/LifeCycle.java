package com.github.mxsm.remoting;

/**
 * @author mxsm
 * @date 2021/10/31 20:51
 * @Since 1.0.0
 */
public interface LifeCycle {

    /**
     * before start execute
     */
    void init();

    /**
     * start
     */
    void start() throws InterruptedException;

    /**
     * shutdown
     */
    void shutdown();

}
