package com.github.mxsm.remoting.connection;


import com.github.mxsm.protocol.protobuf.ClientMetadata;
import com.github.mxsm.remoting.LifeCycle;
import java.util.Set;

/**
 * @author mxsm
 * @date 2021/11/14 16:29
 * @Since 1.0.0
 */
public interface ServerConnectionManager extends ConnectionManager, LifeCycle {

    /**
     * get client number
     * @return
     */
    long getClientConnectionNums();


    Set<ClientMetadata> getClientMetadataCollection();
}
