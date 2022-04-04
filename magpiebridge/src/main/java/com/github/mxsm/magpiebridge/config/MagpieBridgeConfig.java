package com.github.mxsm.magpiebridge.config;

import com.github.mxsm.common.annotation.NotNull;
import com.github.mxsm.magpiebridge.common.MagpieBridgeConst;
import java.util.UUID;

/**
 * @author mxsm
 * @Date 2021/7/2
 * @Since 1.0.0
 */
public class MagpieBridgeConfig {

    /**
     * address split by ,
     */
    @NotNull
    private String registerAddress = System.getProperty(MagpieBridgeConst.REGISTER_ADDRESS);

    private String magpieBridgeName = UUID.randomUUID().toString();

    private long magpieBridgeId = Seed.seed();

    private long magpieBridgeRegisterTimeoutMills = 300000;

    private String registerDomain = System.getProperty(MagpieBridgeConst.REGISTER_ADDRESS, "register.im-mxsm.local");

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

    public long getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public void setMagpieBridgeId(long magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public long getMagpieBridgeRegisterTimeoutMills() {
        return magpieBridgeRegisterTimeoutMills;
    }

    public void setMagpieBridgeRegisterTimeoutMills(long magpieBridgeRegisterTimeoutMills) {
        this.magpieBridgeRegisterTimeoutMills = magpieBridgeRegisterTimeoutMills;
    }

    public String getRegisterDomain() {
        return registerDomain;
    }

    public void setRegisterDomain(String registerDomain) {
        this.registerDomain = registerDomain;
    }
}
