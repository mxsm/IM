package com.github.mxsm.magpiebridge.client.manager;

import com.github.mxsm.common.client.ClientMetadata;
import com.github.mxsm.remoting.connection.ConnectionPool;
import com.github.mxsm.remoting.connection.DefaultConnectionManager;
import com.github.mxsm.remoting.connection.ServerConnectionManager;
import java.util.Set;

/**
 * @author mxsm
 * @date 2021/11/14 16:48
 * @Since 1.0.0
 */
public class DefaultServerConnectionManager extends DefaultConnectionManager implements ServerConnectionManager {

    public DefaultServerConnectionManager(ConnectionPool connectionPool) {
        super();
    }

    public DefaultServerConnectionManager() {

    }

    @Override
    public long getClientConnectionNums() {
        return getConnectionNumbers().get();
    }

    @Override
    public Set<ClientMetadata> getClientMetadataCollection() {
        return getClientMetadatas();
    }
}
