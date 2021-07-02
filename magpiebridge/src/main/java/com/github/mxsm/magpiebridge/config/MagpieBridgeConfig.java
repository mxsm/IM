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
}
