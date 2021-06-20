package com.github.mxsm.remoting.netty;

/**
 * @author mxsm
 * @Date 2021/6/19
 * @Since 0.1
 */
public class NettyServerConfig implements Cloneable {

    private int bindPort = 9998;

    private boolean useEpollNativeSelector = false;

    private int serverSelectorThreads = 3;

    private int serverSocketSndBufSize = NettySystemConfig.socketSndbufSize;

    private int serverSocketRcvBufSize = NettySystemConfig.socketRcvbufSize;

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
}
