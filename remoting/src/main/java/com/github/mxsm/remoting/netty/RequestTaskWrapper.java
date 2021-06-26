package com.github.mxsm.remoting.netty;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public class RequestTaskWrapper implements Runnable{

    private final RequestTask task;

    private final Channel channel;

    private final RemotingCommand cmd;

    private final long createTimestamp = System.currentTimeMillis();

    private volatile boolean stopRun = false;


    public RequestTaskWrapper(RequestTask task, Channel channel, RemotingCommand cmd) {
        this.task = task;
        this.channel = channel;
        this.cmd = cmd;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
     * causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
