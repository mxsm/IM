package com.github.mxsm.remoting;

/**
 * @author mxsm
 * @date 2021/10/31 20:51
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
