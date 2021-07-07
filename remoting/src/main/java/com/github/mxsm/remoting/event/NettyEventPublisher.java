package com.github.mxsm.remoting.event;

/**
 * @author mxsm
 * @Date 2021/7/7
 * @Since
 */
@FunctionalInterface
public interface NettyEventPublisher {

    void publishEvent(NettyEvent nettyEvent);
}
