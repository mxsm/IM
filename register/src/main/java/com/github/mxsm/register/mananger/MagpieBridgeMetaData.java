package com.github.mxsm.register.mananger;

import com.github.mxsm.remoting.connection.ConnectionMetaData;

/**
 * @author mxsm
 * @Date 2021/7/29
 * @Since 0.1
 */
public class MagpieBridgeMetaData extends ConnectionMetaData {

    private final String magpieBridgeName;

    private final String magpieBridgeAddress;

    public MagpieBridgeMetaData(String magpieBridgeName, String magpieBridgeAddress) {
        this.magpieBridgeName = magpieBridgeName;
        this.magpieBridgeAddress = magpieBridgeAddress;
    }

    public String getMagpieBridgeName() {
        return magpieBridgeName;
    }

    public String getMagpieBridgeAddress() {
        return magpieBridgeAddress;
    }

}
