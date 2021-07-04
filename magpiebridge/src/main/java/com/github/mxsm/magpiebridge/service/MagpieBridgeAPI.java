package com.github.mxsm.magpiebridge.service;

import com.github.mxsm.common.GeneralUtils;
import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.common.NetUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/7/3
 * @Since
 */
public class MagpieBridgeAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeAPI.class);

    private final RemotingClient remotingClient;

    private final NettyClientConfig nettyClientConfig;

    private ExecutorService bmExecutorService = new ThreadPoolExecutor(GeneralUtils.getAvailableProcessors(),
        GeneralUtils.getAvailableProcessors() * 2, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(128));

    public MagpieBridgeAPI(final NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.remotingClient = new NettyRemotingClient(this.nettyClientConfig);
    }

    /**
     * 注册所有的
     *
     * @param mbInfo
     */
    public List<RemotingCommand> registerBrokerAll(final MagpieBridgeInfo mbInfo, final long timeout) {

        final List<String> registerAddressList = this.remotingClient.getRegisterAddressList();
        if (CollectionUtils.isEmpty(registerAddressList)) {
            LOGGER.warn("Registration center address is empty, not center to register MagpieBridge");
            return null;
        }

        CountDownLatch registerLatch = new CountDownLatch(registerAddressList.size());
        List<RemotingCommand> registerMbResultList = new ArrayList<>();
        for (String registerAddress : registerAddressList) {
            this.bmExecutorService.submit(() -> {
                try {
                    RemotingCommand registerMbResult = MagpieBridgeAPI.this
                        .registerBroker(registerAddress, null, timeout, false);
                    registerMbResultList.add(registerMbResult);
                    LOGGER.info("Register MagpieBridge[{}] SUCCESS", mbInfo.getMagpieBridgeName());
                } catch (Exception e) {
                    LOGGER.error(String.format("Register MagpieBridge Error to Registration[%s]", registerAddress), e);
                } finally {
                    registerLatch.countDown();
                }
            });
        }

        try {
            registerLatch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("Get MagpieBridge register Result time out[timeout={}ms]", timeout);
        }

        return registerMbResultList;
    }


    private RemotingCommand registerBroker(final String registerAddress, final RemotingCommand request,
        final long timeoutMillis, final boolean oneway)
        throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException {

        if (oneway) {

            try {
                this.remotingClient.invokeOneway(registerAddress, request, timeoutMillis);

            } catch (RemotingTooMuchRequestException e) {
                // nothing to do
            }
            return null;
        }

        RemotingCommand response = this.remotingClient.invokeSync(registerAddress, request, timeoutMillis);

        if (response == null) {
            return null;
        }

        return response;
    }
}
