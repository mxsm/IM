package com.github.mxsm.common.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2021/10/31 15:58
 * @Since 1.0.0
 */
public abstract class ServiceThread implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceThread.class);


    private Thread thread;

    protected volatile boolean stopped = false;

    protected boolean isDaemon = false;

    private final AtomicBoolean started = new AtomicBoolean(false);

    public void start(){
        LOGGER.info("Try to start service thread:{} started:{} lastThread:{}", this.getServiceThreadName(), started.get(), thread);
        if(!started.compareAndSet(false, true)){
            return;
        }
        this.stopped = false;
        this.thread = new Thread(this, this.getServiceThreadName());
        this.thread.setDaemon(isDaemon);
        this.thread.start();
    }

    public abstract String getServiceThreadName();
}
