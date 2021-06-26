package com.github.mxsm.protocol.utils;

import com.github.mxsm.protocol.protobuf.RemotingCommand;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 0.1
 */
public abstract class RemotingCommandBuilder {

    public static RemotingCommand buildRequestCommand(){

        return null;
    }

    public static RemotingCommand buildResponseCommand(){

        return null;
    }

    public static void markOneway(RemotingCommand command){

    }

}
