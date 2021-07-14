package com.github.mxsm.common.magpiebridge;

/**
 * @author mxsm
 * @Date 2021/7/15
 * @Since 0.1
 */
public class RegisterMagpieBridgeResult {

    private final String masterAddress;

    private final long magpieBridgeId;

    public RegisterMagpieBridgeResult(final long magpieBridgeId, final String masterAddress) {
        this.masterAddress = masterAddress;
        this.magpieBridgeId = magpieBridgeId;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public long getMagpieBridgeId() {
        return magpieBridgeId;
    }
}
