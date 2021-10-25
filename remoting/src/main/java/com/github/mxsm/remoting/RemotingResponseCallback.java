package com.github.mxsm.remoting;


import com.github.mxsm.protocol.core.RemotingCommand;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public interface RemotingResponseCallback {

    void callback(RemotingCommand response);
}
