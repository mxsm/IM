package com.github.mxsm.protocol;

import java.io.Serializable;

/**
 * @author mxsm
 * @Date 2021/10/15
 * @Since
 */
public interface RemotingCommand<T> extends Serializable {

    ProtocolCode getProtocolCode();

    CommandCode getCommandCode();

    T getRemotingCommand();

    byte[] getRemotingCommandBytes();

}
