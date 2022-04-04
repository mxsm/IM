package com.github.mxsm.magpiebridge.exception;

/**
 * @author mxsm
 * @Date 2021/7/4
 * @Since
 */
public class RegisterRequestException extends Exception {

    private final int responseCode;
    private final String errorMessage;
    private final String serverAddress;

    public RegisterRequestException(int responseCode, String errorMessage) {
        super(String.format("ResponseCode:%d", responseCode) + "  DESC: " + errorMessage);
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.serverAddress = null;
    }

    public RegisterRequestException(int responseCode, String errorMessage, String serverAddress) {
        super(String.format("ResponseCode:%d,Server address:%s", responseCode,
            (serverAddress != null ? " BROKER: " + serverAddress : "")) + "  DESC: " + errorMessage);
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.serverAddress = serverAddress;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMagpieBridgeAddr() {
        return serverAddress;
    }
}
