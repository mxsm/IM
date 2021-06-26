package com.github.mxsm.protocol.utils;

import com.github.mxsm.protocol.protobuf.RemotingCommand;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 0.1
 */
public abstract class ProtobufUtils {

    public static boolean isOnewayRequest(RemotingCommand request){
        return request == null? true : request.getOneway();
    }
}
