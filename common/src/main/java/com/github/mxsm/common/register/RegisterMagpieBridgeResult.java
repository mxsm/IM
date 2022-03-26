package com.github.mxsm.common.register;

/**
 * @author mxsm
 * @Date 2021/7/15
 * @Since 1.0.0
 */
public class RegisterMagpieBridgeResult {

    public static final int NO_MASTER = -1;

    private  String masterAddress;

    private  long magpieBridgeId;

    private  long magpieBridgeMasterId;

    public RegisterMagpieBridgeResult(final long magpieBridgeId, final long magpieBridgeMasterId, final String masterAddress) {
        this.masterAddress = masterAddress;
        this.magpieBridgeId = magpieBridgeId;
        this.magpieBridgeMasterId = magpieBridgeMasterId;
    }

    public RegisterMagpieBridgeResult(final long magpieBridgeId, final String masterAddress) {
        this(magpieBridgeId,magpieBridgeId,masterAddress);
    }

    public RegisterMagpieBridgeResult() {
        this(-1,-1,null);
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public long getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public long getMagpieBridgeMasterId() {
        return magpieBridgeMasterId;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public void setMagpieBridgeId(long magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public void setMagpieBridgeMasterId(long magpieBridgeMasterId) {
        this.magpieBridgeMasterId = magpieBridgeMasterId;
    }

    @Override
    public String toString() {
        return "RegisterMagpieBridgeResult{" +
            "masterAddress='" + masterAddress + '\'' +
            ", magpieBridgeId=" + magpieBridgeId +
            ", magpieBridgeMasterId=" + magpieBridgeMasterId +
            '}';
    }
}
