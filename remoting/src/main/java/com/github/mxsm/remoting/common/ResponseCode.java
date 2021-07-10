package com.github.mxsm.remoting.common;

/**
 * @author mxsm
 * @Date 2021/7/1
 * @Since
 */
public abstract class ResponseCode {

    public static final int CRC32_MATCH_ERROR = 99;

    public static final int SUCCESS = 200;

    public static final int SYSTEM_ERROR = 500;

    public static final int REQUEST_CODE_NOT_SUPPORTED = 404;

    public static final int SYSTEM_BUSY = 503;


}
