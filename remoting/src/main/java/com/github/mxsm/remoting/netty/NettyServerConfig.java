package com.github.mxsm.remoting.netty;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since 0.1
 */
public class NettyServerConfig implements Cloneable {

    private int bindPort = 9998;

    private int serverWorkerThreads = Runtime.getRuntime().availableProcessors() * 2;

    private boolean useEpollNativeSelector = false;

    private int serverSelectorThreads = 3;

    private int serverSocketSndBufSize = NettySystemConfig.socketSndbufSize;

    private int serverSocketRcvBufSize = NettySystemConfig.socketRcvbufSize;

    private int serverChannelMaxIdleTimeSeconds = 30;

    private int serverOnewaySemaphoreValue = 256;

    private int serverAsyncSemaphoreValue = 64;

    private int serverCallbackExecutorThreads = 0;

    private int messageProcessorThreads = Runtime.getRuntime().availableProcessors() * 2;

    public boolean isUseEpollNativeSelector() {
        return useEpollNativeSelector;
    }

    public int getServerSelectorThreads() {
        return serverSelectorThreads;
    }

    public void setServerSelectorThreads(int serverSelectorThreads) {
        this.serverSelectorThreads = serverSelectorThreads;
    }

    public int getServerSocketSndBufSize() {
        return serverSocketSndBufSize;
    }

    public void setServerSocketSndBufSize(int serverSocketSndBufSize) {
        this.serverSocketSndBufSize = serverSocketSndBufSize;
    }

    public int getServerSocketRcvBufSize() {
        return serverSocketRcvBufSize;
    }

    public void setServerSocketRcvBufSize(int serverSocketRcvBufSize) {
        this.serverSocketRcvBufSize = serverSocketRcvBufSize;
    }

    public int getBindPort() {
        return bindPort;
    }

    public void setBindPort(int bindPort) {
        this.bindPort = bindPort;
    }

    public int getServerChannelMaxIdleTimeSeconds() {
        return serverChannelMaxIdleTimeSeconds;
    }

    public void setServerChannelMaxIdleTimeSeconds(int serverChannelMaxIdleTimeSeconds) {
        this.serverChannelMaxIdleTimeSeconds = serverChannelMaxIdleTimeSeconds;
    }

    public int getServerWorkerThreads() {
        return serverWorkerThreads;
    }

    public void setServerWorkerThreads(int serverWorkerThreads) {
        this.serverWorkerThreads = serverWorkerThreads;
    }

    public int getServerOnewaySemaphoreValue() {
        return serverOnewaySemaphoreValue;
    }

    public void setServerOnewaySemaphoreValue(int serverOnewaySemaphoreValue) {
        this.serverOnewaySemaphoreValue = serverOnewaySemaphoreValue;
    }

    public int getServerAsyncSemaphoreValue() {
        return serverAsyncSemaphoreValue;
    }

    public void setServerAsyncSemaphoreValue(int serverAsyncSemaphoreValue) {
        this.serverAsyncSemaphoreValue = serverAsyncSemaphoreValue;
    }

    public int getServerCallbackExecutorThreads() {
        return serverCallbackExecutorThreads;
    }

    public void setServerCallbackExecutorThreads(int serverCallbackExecutorThreads) {
        this.serverCallbackExecutorThreads = serverCallbackExecutorThreads;
    }

    public int getMessageProcessorThreads() {
        return messageProcessorThreads;
    }

    public void setMessageProcessorThreads(int messageProcessorThreads) {
        this.messageProcessorThreads = messageProcessorThreads;
    }
}
