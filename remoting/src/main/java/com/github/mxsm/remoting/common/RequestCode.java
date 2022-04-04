package com.github.mxsm.remoting.common;

/**
 * @author mxsm
 * @Date 2021/7/1
 * @Since 1.0.0
 */
public abstract class RequestCode {

    /*system code 1-100*/

    /**
     * heart beat
     */
    public static final int HEART_BEAT = 0;



    /*system server 101-400*/


    public static final int SERVER_REGISTRY = 101;

    public static final int SERVER_UNREGISTRY = 102;



    /*business code 401 - */

    /**
     * client connection mb
     */
    public static final int CLIENT_CONNECT = 401;

    public static final int GET_MAGPIE_BRIDGE_ADDRESS = 402;
}
