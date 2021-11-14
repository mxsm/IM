package com.github.mxsm.remoting.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author mxsm
 * @date 2021/11/14 14:56
 * @Since 1.0.0
 */
public class ConnectionPool {

    private CopyOnWriteArrayList<Connection> connections;

    private ConnectionSelectStrategy strategy;

    private volatile long lastAccessTimestamp;

    public ConnectionPool(ConnectionSelectStrategy strategy) {
        this.strategy = strategy;
        this.connections = new CopyOnWriteArrayList<>();
        this.lastAccessTimestamp = System.currentTimeMillis();
    }

    /**
     * add a connection
     *
     * @param connection Connection
     */
    public void add(Connection connection) {
        markAccess();
        if (null == connection) {
            return;
        }
        boolean res = connections.addIfAbsent(connection);
        if (res) {
            connection.increaseRef();
        }
    }

    /**
     * do mark the time stamp when access this pool
     */
    private void markAccess() {
        lastAccessTimestamp = System.currentTimeMillis();
    }

    /**
     * check weather a connection already added
     *
     * @param connection Connection
     * @return whether this pool contains the target connection
     */
    public boolean contains(Connection connection) {
        return connections.contains(connection);
    }

    /**
     * removeAndTryClose a connection
     *
     * @param connection Connection
     */
    public void removeAndTryClose(Connection connection) {
        if (null == connection) {
            return;
        }
        boolean res = connections.remove(connection);
        if (res) {
            connection.decreaseRef();
        }
        if (connection.noRef()) {
            connection.close();
        }
    }

    /**
     * get a connection
     *
     * @return Connection
     */
    public Connection get() {
        if (null != connections) {
            List<Connection> snapshot = new ArrayList<Connection>(connections);
            if (snapshot.size() > 0) {
                return strategy.select(snapshot);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * get all connections
     *
     * @return Connection List
     */
    public List<Connection> getAll() {
        return new ArrayList<>(connections);
    }

    /**
     * connection pool size
     *
     * @return pool size
     */
    public int size() {
        return connections.size();
    }

    /**
     * is connection pool empty
     *
     * @return true if this connection pool has no connection
     */
    public boolean isEmpty() {
        return connections.isEmpty();
    }
}
