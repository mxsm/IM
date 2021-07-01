package com.github.mxsm.protocol.utils;

import com.github.mxsm.protocol.protobuf.RemotingCommand;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 0.1
 */
public abstract class RemotingCommandBuilder {

    public static final AtomicLong incrementCmdId = new AtomicLong(0);

    public static RemotingCommand buildRequestCommand(){

        return null;
    }

    public static RemotingCommand buildResponseCommand(){



        return null;
    }

    public static void markOneway(RemotingCommand command){

    }

}
