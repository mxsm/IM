package com.github.mxsm.client;

import static org.junit.jupiter.api.Assertions.*;

import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @date 2021/10/10 22:19
 * @Since 1.0.0
 */
class ImClientTest {



    @Test
    void invokeSync() {

       /* ImClient client = ClientBuilder.newBuilder().setMagpiebridgePort(9998).setMagpieBridgeAddress("192.168.242.1")
            .build();
        try {
            client.invokeSync(RemotingCommandBuilder.buildRequestCommand().setCode(1).build(), 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingConnectException e) {
            e.printStackTrace();
        } catch (RemotingSendRequestException e) {
            e.printStackTrace();
        } catch (RemotingTimeoutException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}