package com.github.mxsm.remoting.common;

/**
 * @author mxsm
 * @Date 2021/7/1
 * @Since 0.1
 */
public abstract class RequestCode {

    /*system code 1-20*/

    /**
     * heart beat
     */
    public static final int HEART_BEAT = 0;

    /*magpie bridge  21-200*/

    /**
     * magpie bridge register
     */
    public static final int MAGPIE_BRIDGE_REGISTER = 21;

    /**
     * magpie bridge unregister
     */
    public static final int MAGPIE_BRIDGE_UNREGISTER = 22;

    /*register code 201-400*/

    public static final int MAGPIE_BRIDGE_MASTER_CHANGE = 201;


    /*business code 401 - */

    /**
     * client connection mb
     */
    public static final int CLIENT_CONNECT = 401;

    public static final int GET_MAGPIE_BRIDGE_ADDRESS = 402;
}
