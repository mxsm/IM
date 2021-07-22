package com.github.mxsm.register.mananger;


import io.netty.channel.Channel;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class MagpieBridgeLiveInfo {

    //最近一次更新时间
    private long lastUpdateTimestamp;

    private final long connectionTimestamp;

    private final Channel channel;

    private volatile boolean online = false;

    public MagpieBridgeLiveInfo(long lastUpdateTime, long connectionTime, final Channel channel) {
        this(lastUpdateTime,connectionTime,channel,true);
    }

    public MagpieBridgeLiveInfo(long lastUpdateTimestamp, long connectionTimestamp, Channel channel, boolean online) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.connectionTimestamp = connectionTimestamp;
        this.channel = channel;
        this.online = online;
        this.online = online;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public long getConnectionTimestamp() {
        return connectionTimestamp;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
