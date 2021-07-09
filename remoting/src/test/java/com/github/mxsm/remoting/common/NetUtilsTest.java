package com.github.mxsm.remoting.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/7/3
 * @Since
 */
class NetUtilsTest {

    @Test
    void ip2SocketAddress() {

    }

    @Test
    void parseChannelRemoteAddr() {

    }

    @Test
    void getLocalAddress() {
        String localAddress = NetUtils.getLocalAddress();
        Assertions.assertNotNull(localAddress);
    }

    @Test
    void normalizeHostAddress() {
        try {
            String s = NetUtils.normalizeHostAddress(InetAddress.getLocalHost());
            Assertions.assertNotNull(s);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}