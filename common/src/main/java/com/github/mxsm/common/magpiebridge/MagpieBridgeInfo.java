package com.github.mxsm.common.magpiebridge;

/**
 * @author mxsm
 * @Date 2021/7/3
 * @Since 0.1
 */
public class MagpieBridgeInfo {

    //名称
    private String magpieBridgeName;

    private String magpieBridgeAddress ;

    private int magpieBridgeId ;

    private long magpieBridgeCreateTimestamp ;

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

    public int getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public void setMagpieBridgeId(int magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public long getMagpieBridgeCreateTimestamp() {
        return magpieBridgeCreateTimestamp;
    }

    public void setMagpieBridgeCreateTimestamp(long magpieBridgeCreateTimestamp) {
        this.magpieBridgeCreateTimestamp = magpieBridgeCreateTimestamp;
    }
}
