package com.github.mxsm.remoting.netty.handler;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.InvokeCallback;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.SemaphoreReleaseWrapper;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.ResponseFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 0.1
 */
public abstract class AbstractNettyRemoting implements RemotingHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNettyRemoting.class);

    /**
     * 使用信号量来现在最大的单向发送的数据并发数(非公平锁提高并发)
     */
    protected final Semaphore semaphoreOneway;

    /**
     * 使用信号量来现在最大的异步发送的数据并发数(非公平锁提高并发)
     */
    protected final Semaphore semaphoreAsync;

    /**
     * 保存ResponseFuture
     */
    protected Map<Long, ResponseFuture> responseTable = new ConcurrentHashMap<>(1024);


    public AbstractNettyRemoting(final int permitsOneway, final int permitsAsync) {
        this.semaphoreOneway = new Semaphore(permitsOneway);
        this.semaphoreAsync = new Semaphore(permitsAsync);
    }

    public abstract ExecutorService getCallbackExecutor();

    /**
     * 单项发送消息的实现
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    protected void invokeOnewayImpl(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        //设置单向发送
        RemotingCommandBuilder.markOneway(request);
        boolean tryAcquire = semaphoreOneway.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        //竞争锁成功的情况
        if (tryAcquire) {
            final SemaphoreReleaseWrapper releaseWrapper = new SemaphoreReleaseWrapper(semaphoreOneway);
            try {
                channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
                    releaseWrapper.release();
                    if (!future.isSuccess()) {
                        LOGGER.warn("send a request command to channel <{}> failed.",
                            NetUtils.parseChannelRemoteAddr(channel));
                    }
                });
            } catch (Exception e) {
                LOGGER.warn("write send a request command to channel <{}> failed.",
                    NetUtils.parseChannelRemoteAddr(channel));
                throw new RemotingSendRequestException(NetUtils.parseChannelRemoteAddr(channel), e);
            } finally {
                releaseWrapper.release();
            }
        } else {
            if (timeoutMillis <= 0) {
                throw new RemotingTooMuchRequestException("invokeOnewayImpl invoke too fast");
            } else {
                //打印单个信号量的信息
                String info = String.format(
                    "invokeOnewayImpl tryAcquire semaphore timeout, %dms, waiting thread nums: %d semaphoreOnewayValue: %d",
                    timeoutMillis,
                    this.semaphoreOneway.getQueueLength(),
                    this.semaphoreOneway.availablePermits()
                );
                LOGGER.warn(info);
                throw new RemotingTimeoutException(info);
            }
        }

    }

    /**
     * 异步发送
     *
     * @param channel
     * @param request
     * @param timeoutMillis
     * @param invokeCallback
     * @throws InterruptedException
     * @throws RemotingTooMuchRequestException
     * @throws RemotingTimeoutException
     * @throws RemotingSendRequestException
     */
    protected void invokeAsyncImpl(final Channel channel, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        long startTime = System.currentTimeMillis();
        boolean tryAcquire = semaphoreAsync.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (tryAcquire) {

            final SemaphoreReleaseWrapper releaseWrapper = new SemaphoreReleaseWrapper(semaphoreAsync);
            long costTime = System.currentTimeMillis() - startTime;
            if (costTime >= timeoutMillis) {
                releaseWrapper.release();
                throw new RemotingSendRequestException("invokeAsyncImpl send time out");
            }
            final long commandId = request.getCommandId();
            ResponseFuture responseFuture = new ResponseFuture(channel, invokeCallback, commandId,
                timeoutMillis, releaseWrapper);
            this.responseTable.put(commandId, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    responseFuture.setSendRequestOk(true);
                    return;
                }
                requestFail(commandId);
                LOGGER.warn("invokeAsyncImpl: send a request command to channel <{}> failed.",
                    NetUtils.parseChannelRemoteAddr(channel));
            });

        } else {
            if (timeoutMillis <= 0) {
                throw new RemotingTooMuchRequestException("invokeAsyncImpl invoke too fast");
            } else {
                //打印单个信号量的信息
                String info = String.format(
                    "invokeAsyncImpl tryAcquire semaphore timeout, %dms, waiting thread nums: %d semaphoreAsyncValue: %d",
                    timeoutMillis,
                    this.semaphoreOneway.getQueueLength(),
                    this.semaphoreOneway.availablePermits()
                );
                LOGGER.warn(info);
                throw new RemotingTimeoutException(info);
            }
        }


    }

    protected RemotingCommand invokeSyncImpl(final Channel channel, final RemotingCommand request,
        final long timeoutMillis)
        throws InterruptedException, RemotingSendRequestException, RemotingTimeoutException {

        final long commandId = request.getCommandId();

        try {
            ResponseFuture responseFuture = new ResponseFuture(channel, null, commandId, timeoutMillis, null);
            this.responseTable.put(commandId, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    responseFuture.setSendRequestOk(true);
                    return;
                } else {
                    responseFuture.setSendRequestOk(false);
                }
                responseTable.remove(commandId);
                responseFuture.setCause(f.cause());
                responseFuture.putRemotingCommandResponse(null);
                LOGGER.warn("send a request command to channel <{}> failed.", NetUtils.parseChannelRemoteAddr(channel));
            });

            RemotingCommand responseCommand = responseFuture.wait4Response(timeoutMillis);
            if (null == responseCommand) {
                if (responseFuture.isSendRequestOk()) {
                    throw new RemotingTimeoutException(NetUtils.parseChannelRemoteAddr(channel), timeoutMillis,
                        responseFuture.getCause());
                } else {
                    throw new RemotingSendRequestException(NetUtils.parseChannelRemoteAddr(channel),
                        responseFuture.getCause());
                }
            }
            return responseCommand;
        } finally {
            this.responseTable.remove(commandId);
        }
    }


    private void requestFail(final long commandId) {
        ResponseFuture responseFuture = responseTable.remove(commandId);
        if (responseFuture != null) {
            responseFuture.setSendRequestOk(false);
            responseFuture.putRemotingCommandResponse(null);
            try {
                executeInvokeCallback(responseFuture);
            } catch (Throwable e) {
                LOGGER.warn("execute callback in requestFail, and callback throw", e);
            } finally {
                responseFuture.release();
            }
        }
    }

    protected void executeInvokeCallback(final ResponseFuture responseFuture) {
        boolean runInThisThread = false;
        ExecutorService executor = this.getCallbackExecutor();
        if (executor != null) {
            try {
                executor.submit(() -> {
                    try {
                        responseFuture.executeInvokeCallback();
                    } catch (Throwable e) {
                        LOGGER.warn("execute callback in executor exception, and callback throw", e);
                    } finally {
                        responseFuture.release();
                    }
                });
            } catch (Exception e) {
                runInThisThread = true;
                LOGGER.warn("execute callback in executor exception, maybe executor busy", e);
            }
        } else {
            runInThisThread = true;
        }

        if (runInThisThread) {
            try {
                responseFuture.executeInvokeCallback();
            } catch (Throwable e) {
                LOGGER.warn("executeInvokeCallback Exception", e);
            } finally {
                responseFuture.release();
            }
        }
    }
}
