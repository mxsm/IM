package com.github.mxsm.register.exception;

/**
 * @author mxsm
 * @Date 2021/7/4
 * @Since
 */
public class ServerHandleException extends RuntimeException {


    private final String errorMessage;
    private final String serverAddress;

    public ServerHandleException(String errorMessage) {
        super("DESC: " + errorMessage);
        this.errorMessage = errorMessage;
        this.serverAddress = null;
    }

    public ServerHandleException(String errorMessage, String serverAddress) {
        super(
            String.format("Server address:%s", (serverAddress != null ? " BROKER: " + serverAddress : "")) + "  DESC: "
                + errorMessage);
        this.errorMessage = errorMessage;
        this.serverAddress = serverAddress;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMagpieBridgeAddr() {
        return serverAddress;
    }
}
