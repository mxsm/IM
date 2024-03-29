package com.github.mxsm.protocol.utils;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.protobuf.constant.RemotingCommandType;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 1.0.0
 */
public abstract class RemotingCommandBuilder {

    public static final AtomicLong incrementCmdId = new AtomicLong(0);

    public static RemotingCommand.Builder buildRequestCommand() {

        return RemotingCommand.newBuilder().setCommandType(RemotingCommandType.REQUEST).setCommandId(incrementCmdId.incrementAndGet())
            .setCreateTimestamp(System.currentTimeMillis());
    }

    public static RemotingCommand.Builder buildRequestCommand(boolean oneway) {

        return RemotingCommand.newBuilder().setCommandType(RemotingCommandType.REQUEST).setCommandId(incrementCmdId.incrementAndGet())
            .setCreateTimestamp(System.currentTimeMillis()).setOneway(oneway);
    }

    public static RemotingCommand.Builder buildResponseCommand() {

        return RemotingCommand.newBuilder().setCommandType(RemotingCommandType.RESPONSE)
            .setCreateTimestamp(System.currentTimeMillis());
    }

    public static RemotingCommand.Builder buildResponseCommand(long commandId) {

        return RemotingCommand.newBuilder().setCommandType(RemotingCommandType.RESPONSE).setCommandId(commandId)
            .setCreateTimestamp(System.currentTimeMillis());
    }

    public static void markOneway(RemotingCommand command) {

    }

}
