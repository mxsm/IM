package com.github.mxsm.remoting.common;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since
 */
public class SemaphoreReleaseWrapper {

    private AtomicBoolean released = new AtomicBoolean(false);

    private Semaphore semaphore;

    public SemaphoreReleaseWrapper(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void release(){
        if (this.semaphore != null) {
            if (this.released.compareAndSet(false, true)) {
                this.semaphore.release();
            }
        }
    }
}
