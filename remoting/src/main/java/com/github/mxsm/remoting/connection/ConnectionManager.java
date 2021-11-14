package com.github.mxsm.remoting.connection;

import com.github.mxsm.remoting.LifeCycle;
import com.github.mxsm.remoting.exception.RemotingException;
import java.util.List;
import java.util.Map;

/**
 * @author mxsm
 * @date 2021/11/14 14:36
 * @Since 1.0.0
 */
public interface ConnectionManager extends LifeCycle {

    /**
     * add connection to manager
     * @param connection
     */
    void addConnection(final Connection connection);

    /**
     * add connection to manager
     * @param connection
     * @param poolKey
     */
    void addConnection(final Connection connection, final String poolKey);

    /**
     * Get a connection from {@link ConnectionPool} with the specified poolKey.
     * @param poolKey
     * @return
     */
    Connection get(String poolKey);

    /**
     * Get all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return a list of {@link Connection}<br>
     *   or return an empty list if there is no {@link ConnectionPool} mapping with poolKey.
     */
    List<Connection> getAll(String poolKey);

    /**
     * Get all connections of all poolKey.
     *
     * @return a map with poolKey as key and a list of connections in ConnectionPool as value
     */
    Map<String, List<Connection>> getAll();

    /**
     * Remove a {@link Connection} from all {@link ConnectionPool} with the poolKeys in {@link Connection}, and close it.
     */
    void remove(Connection connection);

    /**
     * Remove and close a {@link Connection} from {@link ConnectionPool} with the specified poolKey.
     *
     * @param connection target connection
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    void remove(Connection connection, String poolKey);

    /**
     * Remove and close all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    void remove(String poolKey);

    /**
     * check a connection whether available, if not, throw RemotingException
     *
     * @param connection target connection
     */
    void check(Connection connection) throws RemotingException;

    /**
     * Get the number of {@link Connection} in {@link ConnectionPool} with the specified pool key
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return connection count
     */
    int count(String poolKey);

    /**
     * Create a connection using specified {@link String} address.
     *
     * @param address a {@link String} address, e.g. 127.0.0.1:1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     * @throws RemotingException if create failed
     */
    Connection create(String address, int connectTimeout) throws RemotingException;

    /**
     * Create a connection using specified ip and port.
     *
     * @param ip connect ip, e.g. 127.0.0.1
     * @param port connect port, e.g. 1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     */
    Connection create(String ip, int port, int connectTimeout) throws RemotingException;
}
