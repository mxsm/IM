package com.github.mxsm.remoting.protocol;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public interface RemotingCommand {

    String getCommandName();

    RemotingCommandType getCommandType();
}
