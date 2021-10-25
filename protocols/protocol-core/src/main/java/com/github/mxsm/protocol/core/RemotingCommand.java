package com.github.mxsm.protocol.core;

import java.io.Serializable;

/**
 * @author mxsm
 * @Date 2021/10/15
 * @Since 0.1
 */
public interface RemotingCommand<T> extends Serializable {

    ProtocolCode getProtocolCode();

    CommandCode getCommandCode();

    RemotingCommandType getCommandType();

    T getRemotingCommand();

    byte[] getRemotingCommandBytes();

    boolean isOnewayRequest();

    long getCommandId();

}
