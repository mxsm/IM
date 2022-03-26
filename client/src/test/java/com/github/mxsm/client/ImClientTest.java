package com.github.mxsm.client;

import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.common.RequestCode;
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
    void invokeSync() throws Exception {

        ImClient client = ClientBuilder.newBuilder().setMagpiebridgePort(9999).setMagpieBridgeAddress("127.0.0.1")
            .build();

        RemotingCommand remotingCommand = client.invokeSync(
            RemotingCommandBuilder.buildRequestCommand().setCode(RequestCode.CLIENT_CONNECT).build(),
            2000);

/*        MagpieBridgeMetadata connectMagpieBridge = client.getConnectMagpieBridge("127.0.0.1:8888");

        System.out.println(connectMagpieBridge);
        //System.out.println(remotingCommand.getCode());

        TimeUnit.SECONDS.sleep(100);*/
    }
}