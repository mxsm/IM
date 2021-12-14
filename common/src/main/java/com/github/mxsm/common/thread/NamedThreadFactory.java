package com.github.mxsm.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since 0.1
 */
public class NamedThreadFactory implements ThreadFactory {

    private AtomicInteger threadIndex;

    private final String threadNamePrefix;

    private boolean isDemoThread;


    public NamedThreadFactory(final String threadNamePrefix, boolean isDemoThread) {
        this(new AtomicInteger(0), threadNamePrefix, isDemoThread);
    }

    public NamedThreadFactory(AtomicInteger threadIndex, final String threadNamePrefix, boolean isDemoThread) {
        this.threadIndex = threadIndex == null ? new AtomicInteger(0) : threadIndex;
        this.threadNamePrefix = threadNamePrefix;
        this.isDemoThread = isDemoThread;
    }

    public NamedThreadFactory(final String threadNamePrefix) {
        this(threadNamePrefix, true);
    }

    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize priority, name, daemon status, {@code
     * ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r, threadNamePrefix + "_" + threadIndex.incrementAndGet());
        if (isDemoThread) {
            thread.setDaemon(true);
        }
        return thread;
    }
}
