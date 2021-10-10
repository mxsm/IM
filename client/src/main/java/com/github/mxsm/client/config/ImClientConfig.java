package com.github.mxsm.client.config;

/**
 * @author mxsm
 * @date 2021/10/10 16:26
 * @Since 1.0.0
 */
public class ImClientConfig {

    private String magpieBridgeAddress;

    private int magpiebridgePort;

    public String getMagpieBridgeAddress() {
        return magpieBridgeAddress;
    }

    public void setMagpieBridgeAddress(String magpieBridgeAddress) {
        this.magpieBridgeAddress = magpieBridgeAddress;
    }

    public int getMagpiebridgePort() {
        return magpiebridgePort;
    }

    public void setMagpiebridgePort(int magpiebridgePort) {
        this.magpiebridgePort = magpiebridgePort;
    }

    public String getMagpieBridgeAddressWithPort(){

        StringBuilder addressWithPort = new StringBuilder();
        addressWithPort.append(this.magpieBridgeAddress).append(":").append(this.magpiebridgePort);
        return addressWithPort.toString();
    }
}
