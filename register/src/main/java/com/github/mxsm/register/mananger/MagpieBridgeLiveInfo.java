package com.github.mxsm.register.mananger;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class MagpieBridgeLiveInfo {

    /**
     * 鹊桥名称
     */
    private String magpieBridgeName;

    /**
     * 鹊桥地址 IP:Port
     */
    private String magpieBridgeAddress;

    /**
     * 连接注册中心时间
     */
    private long connRegisterTime;

    /**
     * 最后一次心跳时间
     */
    private long lastHeartbeatTime;

    /**
     * 鹊桥是否在线,默认不在线
     */
    private volatile boolean online = false;

    public long getConnRegisterTime() {
        return connRegisterTime;
    }

    public void setConnRegisterTime(long connRegisterTime) {
        this.connRegisterTime = connRegisterTime;
    }

    public long getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(long lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }


    public String getMagpieBridgeName() {
        return magpieBridgeName;
    }

    public void setMagpieBridgeName(String magpieBridgeName) {
        this.magpieBridgeName = magpieBridgeName;
    }

    public String getMagpieBridgeAddress() {
        return magpieBridgeAddress;
    }

    public void setMagpieBridgeAddress(String magpieBridgeAddress) {
        this.magpieBridgeAddress = magpieBridgeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MagpieBridgeLiveInfo)) {
            return false;
        }

        MagpieBridgeLiveInfo that = (MagpieBridgeLiveInfo) o;

        if (getConnRegisterTime() != that.getConnRegisterTime()) {
            return false;
        }
        if (getLastHeartbeatTime() != that.getLastHeartbeatTime()) {
            return false;
        }
        if (isOnline() != that.isOnline()) {
            return false;
        }
        if (!getMagpieBridgeName().equals(that.getMagpieBridgeName())) {
            return false;
        }
        return getMagpieBridgeAddress().equals(that.getMagpieBridgeAddress());
    }

    @Override
    public int hashCode() {
        int result = getMagpieBridgeName().hashCode();
        result = 31 * result + getMagpieBridgeAddress().hashCode();
        result = 31 * result + (int) (getConnRegisterTime() ^ (getConnRegisterTime() >>> 32));
        result = 31 * result + (int) (getLastHeartbeatTime() ^ (getLastHeartbeatTime() >>> 32));
        result = 31 * result + (isOnline() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MagpieBridgeInfo{" +
            "magpieBridgeName='" + magpieBridgeName + '\'' +
            ", magpieBridgeAddress='" + magpieBridgeAddress + '\'' +
            ", connRegisterTime=" + connRegisterTime +
            ", lastHeartbeatTime=" + lastHeartbeatTime +
            ", online=" + online +
            '}';
    }
}
