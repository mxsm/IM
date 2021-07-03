package com.github.mxsm.magpiebridge.config;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.magpiebridge.common.MagpieBridgeConst;
import java.util.UUID;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 0.1
 */
public class MagpieBridgeConfig {

    @NotNull
    private String registerAddress = System.getProperty(MagpieBridgeConst.REGISTER_ADDRESS);

    private String magpieBridgeName = UUID.randomUUID().toString();

    private int magpieBridgeId;

    private long magpieBridgeRegisterTimeoutMills = 3000;

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getMagpieBridgeName() {
        return magpieBridgeName;
    }

    public void setMagpieBridgeName(String magpieBridgeName) {
        this.magpieBridgeName = magpieBridgeName;
    }

    public int getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public void setMagpieBridgeId(int magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public long getMagpieBridgeRegisterTimeoutMills() {
        return magpieBridgeRegisterTimeoutMills;
    }

    public void setMagpieBridgeRegisterTimeoutMills(long magpieBridgeRegisterTimeoutMills) {
        this.magpieBridgeRegisterTimeoutMills = magpieBridgeRegisterTimeoutMills;
    }
}
