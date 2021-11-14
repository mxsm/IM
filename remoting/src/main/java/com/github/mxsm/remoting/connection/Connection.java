package com.github.mxsm.remoting.connection;

import com.github.mxsm.remoting.common.NetUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2021/11/14 14:43
 * @Since 1.0.0
 */
public class Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);

    private Channel channel;

    private ConnectionMetaData connMetaData;

    private AtomicBoolean closed = new AtomicBoolean(false);

    private Properties properties;

    /**
     * the reference count used for this connection. If equals 2, it means this connection has been referenced 2 times
     */
    private final AtomicInteger referenceCount = new AtomicInteger();

    private Set<String> poolKeys = new ConcurrentHashSet<>();

    /**
     * no reference of the current connection
     */
    private static final int NO_REFERENCE = 0;

    public Connection(final Channel channel) {
        this.channel = channel;
    }

    /**
     * increase the reference count
     */
    public void increaseRef() {
        this.referenceCount.getAndIncrement();
    }

    /**
     * decrease the reference count
     */
    public void decreaseRef() {
        this.referenceCount.getAndDecrement();
    }

    /**
     * to check whether the reference count is 0
     *
     * @return true if the reference count is 0
     */
    public boolean noRef() {
        return this.referenceCount.get() == NO_REFERENCE;
    }

    /**
     * Close the connection.
     */
    public void close() {
        if (closed.compareAndSet(false, true)) {
            try {
                if (this.getChannel() != null) {
                    this.getChannel().close().addListener((ChannelFutureListener) future -> LOGGER.info(
                        "Close the connection to remote address={}, result={}, cause={}",
                        NetUtils.parseChannelRemoteAddress(Connection.this.getChannel()), future.isSuccess(),
                        future.cause()));
                }
            } catch (Exception e) {
                LOGGER.warn("Exception caught when closing connection {}",
                    NetUtils.parseChannelRemoteAddress(Connection.this.getChannel()), e);
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }


    /**
     * get all pool keys
     */
    public Set<String> getPoolKeys() {
        return new HashSet<>(poolKeys);
    }
}
