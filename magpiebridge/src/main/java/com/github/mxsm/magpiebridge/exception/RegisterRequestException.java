package com.github.mxsm.magpiebridge.exception;

/**
 * @author mxsm
 * @Date 2021/7/4
 * @Since
 */
public class RegisterRequestException extends Exception {

    private final int responseCode;
    private final String errorMessage;
    private final String magpieBridgeAddr;

    public RegisterRequestException(int responseCode, String errorMessage) {
        super(String.format("ResponseCode:%d", responseCode) + "  DESC: " + errorMessage);
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.magpieBridgeAddr = null;
    }

    public RegisterRequestException(int responseCode, String errorMessage, String magpieBridgeAddr) {
        super(String.format("ResponseCode:%d,MagpieBridge address:%s", responseCode,
            (magpieBridgeAddr != null ? " BROKER: " + magpieBridgeAddr : "")) + "  DESC: " + errorMessage);
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.magpieBridgeAddr = magpieBridgeAddr;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMagpieBridgeAddr() {
        return magpieBridgeAddr;
    }
}
