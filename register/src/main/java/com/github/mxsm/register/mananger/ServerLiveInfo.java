package com.github.mxsm.register.mananger;


import com.github.mxsm.common.enums.ServerType;
import com.github.mxsm.protocol.protobuf.ClientMetadata;
import io.netty.channel.Channel;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
public class ServerLiveInfo {

    //最近一次更新时间
    private long lastUpdateTimestamp;

    private final long connectionTimestamp;

    private final Channel channel;

    private volatile boolean online = false;

    private volatile long clientNums;

    private final  ServerType serverType;

    private Set<ClientMetadata> serverMetadataSet = new HashSet<>(512);

    public ServerLiveInfo(long lastUpdateTime, long connectionTime, final Channel channel, final ServerType serverType) {
        this(lastUpdateTime, connectionTime, channel, true, serverType);
    }

    public ServerLiveInfo(long lastUpdateTimestamp, long connectionTimestamp, Channel channel, boolean online, final ServerType serverType) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.connectionTimestamp = connectionTimestamp;
        this.channel = channel;
        this.online = online;
        this.serverType = serverType;
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

    public long getClientNums() {
        return clientNums;
    }

    public void setClientNums(long clientNums) {
        this.clientNums = clientNums;
    }

    public void addClientMetadatas(Collection<ClientMetadata> datas) {
        this.serverMetadataSet.addAll(datas);
    }

    public ServerType getServerType() {
        return serverType;
    }
}
