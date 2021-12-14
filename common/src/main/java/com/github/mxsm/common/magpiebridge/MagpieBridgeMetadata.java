package com.github.mxsm.common.magpiebridge;

/**
 * @author mxsm
 * @Date 2021/7/3
 * @Since 0.1
 */
public class MagpieBridgeMetadata {

    //name
    private String magpieBridgeName;

    //adress: ip:port
    private String magpieBridgeAddress;

    //id
    private long magpieBridgeId;

    //createTime
    private long magpieBridgeCreateTimestamp;

    private String magpieBridgeClusterName;

    private MagpieBridgeRole magpieBridgeRole;

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

    public long getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public void setMagpieBridgeId(long magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public long getMagpieBridgeCreateTimestamp() {
        return magpieBridgeCreateTimestamp;
    }

    public void setMagpieBridgeCreateTimestamp(long magpieBridgeCreateTimestamp) {
        this.magpieBridgeCreateTimestamp = magpieBridgeCreateTimestamp;
    }

    public String getMagpieBridgeClusterName() {
        return magpieBridgeClusterName;
    }

    public void setMagpieBridgeClusterName(String magpieBridgeClusterName) {
        this.magpieBridgeClusterName = magpieBridgeClusterName;
    }

    public MagpieBridgeRole getMagpieBridgeRole() {
        return magpieBridgeRole;
    }

    public void setMagpieBridgeRole(MagpieBridgeRole magpieBridgeRole) {
        this.magpieBridgeRole = magpieBridgeRole;
    }
}
