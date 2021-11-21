package com.github.mxsm.remoting.connection;

import com.github.mxsm.remoting.LifeCycle;
import com.github.mxsm.remoting.exception.RemotingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2021/11/14 16:02
 * @Since 1.0.0
 */
public class DefaultConnectionManager implements ConnectionManager, LifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConnectionManager.class);

    private Map<String /*client ip:port*/,Connection> connectionPoolTable = new ConcurrentHashMap<>();

    //connection  num
    private final AtomicLong connectionNumbers = new AtomicLong(0);

    public DefaultConnectionManager() {

    }

    /**
     * before start execute
     */
    @Override
    public void init() {

    }

    /**
     * start
     */
    @Override
    public void start() {

    }

    /**
     * shutdown
     */
    @Override
    public void shutdown() {

    }

    /**
     * Check whether it is started
     *
     * @return
     */
    @Override
    public boolean isStarted() {
        return false;
    }

    /**
     * add connection to manager
     *
     * @param connection
     */
    @Override
    public void addConnection(Connection connection) {
        /*Set<String> poolKeys = connection.getPoolKeys();
        for (String poolKey : poolKeys) {
            this.addConnection(connection, poolKey);
        }*/
        Connection originConn = this.connectionPoolTable.get(connection.getUniqueKey());
        if(originConn == null){
            this.connectionPoolTable.put(connection.getUniqueKey(),connection);
        }else {
            originConn.increaseRef();
        }

    }

    /**
     * add connection to manager
     *
     * @param connection
     * @param poolKey
     */
    @Override
    public void addConnection(Connection connection, String poolKey) {

        /*ConnectionPool pool = null;
        try {
            // get or create an empty connection pool
            //pool = this.getConnectionPoolAndCreateIfAbsent(poolKey, new ConnectionPoolCall());
        } catch (Exception e) {
            // should not reach here.
            LOGGER.error("[NOTIFYME] Exception occurred when getOrCreateIfAbsent an empty ConnectionPool!", e);
        }
        if (pool != null) {
            pool.add(connection);
        } else {
            // should not reach here.
            LOGGER.error("[NOTIFYME] Connection pool NULL!");
        }*/

    }

    /**
     * Get a connection from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey
     * @return
     */
    @Override
    public Connection get(String poolKey) {
        ConnectionPool pool = null;//this.getConnectionPool(this.connTasks.get(poolKey));
        return null == pool ? null : pool.get();
    }

    /**
     * Get all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return a list of {@link Connection}<br> or return an empty list if there is no {@link ConnectionPool} mapping
     * with poolKey.
     */
    @Override
    public List<Connection> getAll(String poolKey) {
        return null;
    }

    /**
     * Get all connections of all poolKey.
     *
     * @return a map with poolKey as key and a list of connections in ConnectionPool as value
     */
    @Override
    public Map<String, List<Connection>> getAll() {
        return null;
    }

    /**
     * Remove a {@link Connection} from all {@link ConnectionPool} with the poolKeys in {@link Connection}, and close
     * it.
     *
     * @param connection
     */
    @Override
    public void remove(Connection connection) {

    }

    /**
     * Remove and close a {@link Connection} from {@link ConnectionPool} with the specified poolKey.
     *
     * @param connection target connection
     * @param poolKey    unique key of a {@link ConnectionPool}
     */
    @Override
    public void remove(Connection connection, String poolKey) {

    }

    /**
     * Remove and close all connections from {@link ConnectionPool} with the specified poolKey.
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     */
    @Override
    public void remove(String poolKey) {

    }

    /**
     * check a connection whether available, if not, throw RemotingException
     *
     * @param connection target connection
     */
    @Override
    public void check(Connection connection) throws RemotingException {

    }

    /**
     * Get the number of {@link Connection} in {@link ConnectionPool} with the specified pool key
     *
     * @param poolKey unique key of a {@link ConnectionPool}
     * @return connection count
     */
    @Override
    public int count(String poolKey) {
        return 0;
    }

    /**
     * Create a connection using specified {@link String} address.
     *
     * @param address        a {@link String} address, e.g. 127.0.0.1:1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     * @throws RemotingException if create failed
     */
    @Override
    public Connection create(String address, int connectTimeout) throws RemotingException {
        return null;
    }

    /**
     * Create a connection using specified ip and port.
     *
     * @param ip             connect ip, e.g. 127.0.0.1
     * @param port           connect port, e.g. 1111
     * @param connectTimeout an int connect timeout value
     * @return the created {@link Connection}
     */
    @Override
    public Connection create(String ip, int port, int connectTimeout) throws RemotingException {
        return null;
    }
}
