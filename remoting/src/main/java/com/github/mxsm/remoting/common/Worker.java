package com.github.mxsm.remoting.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/7
 * @Since 0.1
 */
public abstract class Worker implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private static final long MAX_WAIT_TIME = 60 * 1000;

    protected final Thread thread;

    private volatile boolean running = false;

    public Worker() {
        this.thread = new Thread(this, this.getWorkerName());
    }

    public abstract String getWorkerName();

    public void start(){
        this.running = true;
        this.thread.start();
        LOGGER.info("-----{} started-----",this.getWorkerName());
    }

    public void shutdown(final boolean interrupt){
        this.running = false;
        if(interrupt){
            this.thread.interrupt();
        }

        try {
            long startTime = System.currentTimeMillis();
            this.thread.join(getWaitTimeBeforeShutdown());
            long endTime = System.currentTimeMillis();

            LOGGER.info("work[{}] wait run to finish time:{}",endTime-startTime);

        } catch (InterruptedException e) {
            LOGGER.error("work shutdown error", e);
        }

    }

    public long getWaitTimeBeforeShutdown(){
        return MAX_WAIT_TIME;
    }

    public boolean isRunning(){
        return this.running;
    }
}
