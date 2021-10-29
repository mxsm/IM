package com.github.mxsm.store;

import com.github.mxsm.protocol.protobuf.RemotingCommand;

/**
 * @author mxsm
 * @Date 2021/10/29
 * @Since 1.0.0
 */
public interface AppendMessageCallback {

    AppendMessageResult appendMessage(long offSet, RemotingCommand command);

}
