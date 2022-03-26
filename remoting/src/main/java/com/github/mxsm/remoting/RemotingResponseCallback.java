package com.github.mxsm.remoting;

import com.github.mxsm.protocol.protobuf.RemotingCommand;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 1.0.0
 */
public interface RemotingResponseCallback {

    void callback(RemotingCommand response);
}
