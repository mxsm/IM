package com.github.mxsm.remoting.event;

import io.netty.channel.Channel;
import java.util.EventObject;

/**
 * @author mxsm
 * @Date 2021/7/7
 * @Since
 */
public class NettyEvent extends EventObject {

    private final NettyEventType eventType;

    private final String address;

    private final Channel channel;

    public NettyEvent(NettyEventType eventType, String address, Channel channel, Object eventObject) {
        super(eventObject);
        this.eventType = eventType;
        this.address = address;
        this.channel = channel;
    }

    public NettyEvent(NettyEventType eventType, String address, Channel channel) {
        super(new Object());
        this.eventType = eventType;
        this.address = address;
        this.channel = channel;
    }

    public NettyEventType getEventType() {
        return eventType;
    }

    public String getAddress() {
        return address;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "NettyEvent{" +
            "eventType=" + eventType +
            ", address='" + address + '\'' +
            ", channel=" + channel +
            '}';
    }
}
