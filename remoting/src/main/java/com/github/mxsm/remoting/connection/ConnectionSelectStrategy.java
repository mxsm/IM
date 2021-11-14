package com.github.mxsm.remoting.connection;

import java.util.List;

/**
 * @author mxsm
 * @date 2021/11/14 14:58
 * @Since 1.0.0
 */
public interface ConnectionSelectStrategy {
    /**
     * select strategy
     *
     * @param connections source connections
     * @return selected connection
     */
    Connection select(List<Connection> connections);
}
