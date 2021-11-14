package com.github.mxsm.remoting.netty;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.Future;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.common.SemaphoreReleaseWrapper;
import io.netty.channel.Channel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since
 */
public class ResponseFuture implements Future {

    private final long commandId;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private volatile boolean sendRequestOk = false;

    private final InvokeCallback<ResponseFuture> invokeCallback;

    private final long beginTimestamp = System.currentTimeMillis();

    private final Channel processChannel;

    private final long timeoutMillis;

    private volatile RemotingCommand responseCommand;

    private volatile Throwable cause;

    private final SemaphoreReleaseWrapper semaphoreReleaseWrapper;

    public ResponseFuture(final Channel processChannel, final InvokeCallback invokeCallback, final long commandId,
        final long timeoutMillis, final SemaphoreReleaseWrapper semaphoreReleaseWrapper) {
        this.commandId = commandId;
        this.invokeCallback = invokeCallback;
        this.processChannel = processChannel;
        this.timeoutMillis = timeoutMillis;
        this.semaphoreReleaseWrapper = semaphoreReleaseWrapper;

    }


    public void putRemotingCommandResponse(final RemotingCommand responseCommand) {
        this.responseCommand = responseCommand;
        this.countDownLatch.countDown();
    }

    public RemotingCommand wait4Response(final long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }

    public void release(){
        if(this.semaphoreReleaseWrapper != null){
            this.semaphoreReleaseWrapper.release();
        }
    }

    public void executeInvokeCallback() {
        if (invokeCallback != null) {
            invokeCallback.operationComplete(this);
        }
    }

    public long getCommandId() {
        return commandId;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public boolean isSendRequestOk() {
        return sendRequestOk;
    }

    public void setSendRequestOk(boolean sendRequestOk) {
        this.sendRequestOk = sendRequestOk;
    }

    public InvokeCallback<ResponseFuture> getInvokeCallback() {
        return invokeCallback;
    }

    public long getBeginTimestamp() {
        return beginTimestamp;
    }

    public Channel getProcessChannel() {
        return processChannel;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public RemotingCommand getResponseCommand() {
        return responseCommand;
    }

    public void setResponseCommand(RemotingCommand responseCommand) {
        this.responseCommand = responseCommand;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
