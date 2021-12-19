package com.github.mxsm.remoting.exception;

/**
 * @author mxsm
 * @date 2021/12/19 16:56
 * @Since 1.0.0
 */
public class Crc32ValidationException extends RemotingException{

    private static final long serialVersionUID = -6061365915274953196L;

    public Crc32ValidationException(String message) {
        super(message);
    }

    public Crc32ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
