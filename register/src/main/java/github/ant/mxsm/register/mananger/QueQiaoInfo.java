package github.ant.mxsm.register.mananger;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class QueQiaoInfo {

    /**
     * 鹊桥名称
     */
    private String queqiaoName;

    /**
     * 鹊桥地址 IP:Port
     */
    private String queqiaoAddress;

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

    public String getQueqiaoName() {
        return queqiaoName;
    }

    public void setQueqiaoName(String queqiaoName) {
        this.queqiaoName = queqiaoName;
    }

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

    public String getQueqiaoAddress() {
        return queqiaoAddress;
    }

    public void setQueqiaoAddress(String queqiaoAddress) {
        this.queqiaoAddress = queqiaoAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QueQiaoInfo)) {
            return false;
        }

        QueQiaoInfo that = (QueQiaoInfo) o;

        if (getConnRegisterTime() != that.getConnRegisterTime()) {
            return false;
        }
        if (getLastHeartbeatTime() != that.getLastHeartbeatTime()) {
            return false;
        }
        if (isOnline() != that.isOnline()) {
            return false;
        }
        if (!getQueqiaoName().equals(that.getQueqiaoName())) {
            return false;
        }
        return getQueqiaoAddress().equals(that.getQueqiaoAddress());
    }

    @Override
    public int hashCode() {
        int result = getQueqiaoName().hashCode();
        result = 31 * result + getQueqiaoAddress().hashCode();
        result = 31 * result + (int) (getConnRegisterTime() ^ (getConnRegisterTime() >>> 32));
        result = 31 * result + (int) (getLastHeartbeatTime() ^ (getLastHeartbeatTime() >>> 32));
        result = 31 * result + (isOnline() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueQiaoInfo{" +
            "queqiaoName='" + queqiaoName + '\'' +
            ", queqiaoAddress='" + queqiaoAddress + '\'' +
            ", connRegisterTime=" + connRegisterTime +
            ", lastHeartbeatTime=" + lastHeartbeatTime +
            ", online=" + online +
            '}';
    }
}
