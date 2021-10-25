package com.github.mxsm.remoting;

/**
 * @author mxsm
 * @date 2021/10/24 22:27
 * @Since 1.0.0
 */
public interface Lifecycle {

    void beforeInit();

    void init();

    void afterInit();

    void beforeStart();

    void start();

    void afterStart();

    void beforeShutdown();

    void shutdown();

    void afterShutdown();

}
