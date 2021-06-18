package com.github.mxsm.remoting.protocol;

/**
 * @author mxsm
 * @Date 2021/6/18
 * @Since 0.1
 */
public class RequestRemotingCommand implements RemotingCommand{

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public RemotingCommandType getCommandType() {
        return RemotingCommandType.REQUEST_COMMAND;
    }
}
