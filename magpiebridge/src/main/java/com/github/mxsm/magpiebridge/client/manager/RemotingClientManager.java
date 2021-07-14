package com.github.mxsm.magpiebridge.client.manager;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mxsm
 * @Date 2021/7/11
 * @Since 0.1
 */
public class RemotingClientManager {

    //connection number of client
    private AtomicLong connections = new AtomicLong(0);


    public long incrementConnection(){
        return connections.incrementAndGet();
    }

    public long decrementConnection(){
        return connections.decrementAndGet();
    }

}
