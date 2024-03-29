package com.github.mxsm.remoting.connection;

/**
 * @author mxsm
 * @date 2021/11/14 16:36
 * @Since 1.0.0
 */
public class ConnectionMetaData {

    public static final int DEFAULT_CONNECT_TIMEOUT = 0;

    private String ip;

    private int port;

    private int connectTimeout;

    public ConnectionMetaData(String ip, int port, int connectTimeout) {
        this.ip = ip;
        this.port = port;
        this.connectTimeout = connectTimeout;
    }

    public ConnectionMetaData(String ip, int port) {
        this(ip, port, DEFAULT_CONNECT_TIMEOUT);
    }

    public ConnectionMetaData() {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
