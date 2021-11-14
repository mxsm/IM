package com.github.mxsm.remoting.exception;

/**
 * @author mxsm
 * @date 2021/11/14 14:37
 * @Since 1.0.0
 */
public class LifeCycleException extends RemotingException{

    private static final long serialVersionUID = 6439775497986632322L;

    public LifeCycleException(String message) {
        super(message);
    }

    public LifeCycleException(String message, Throwable cause) {
        super(message, cause);
    }
}
