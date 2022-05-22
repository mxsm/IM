package com.github.mxsm.client;

import static org.junit.jupiter.api.Assertions.*;

import com.github.mxsm.protocol.protobuf.ServerMetadata;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.exception.Crc32ValidationException;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @date 2022/5/21 21:22
 * @Since 1.0.0
 */
public class ImClientTest {

    private ImClient client;

    @BeforeEach
    void setUp() {
        client = ClientBuilder.newBuilder().build();
    }

    @Test
    void invokeSync() {
    }

    @Test
    void testInvokeSync() {
    }

    @Test
    void invokeAsync() {
    }

    @Test
    void testInvokeAsync() {
    }

    @Test
    void invokeOneway() {
    }

    @Test
    void testInvokeOneway() {
    }

    @Test
    void getConnectMagpieBridge() {
        try {
            ServerMetadata connectMagpieBridge = client.getConnectMagpieBridge("192.168.3.21:10001");
            System.out.println(NetUtils.intIpAddress2String(connectMagpieBridge.getServerIp())+":"+connectMagpieBridge.getServerPort());
        } catch (RemotingConnectException e) {
            e.printStackTrace();
        } catch (RemotingSendRequestException e) {
            e.printStackTrace();
        } catch (RemotingTimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Crc32ValidationException e) {
            e.printStackTrace();
        }
    }
}