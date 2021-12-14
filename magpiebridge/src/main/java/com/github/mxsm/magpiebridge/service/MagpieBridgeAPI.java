package com.github.mxsm.magpiebridge.service;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.common.thread.NamedThreadFactory;
import com.github.mxsm.magpiebridge.config.MagpieBridgeConfig;
import com.github.mxsm.magpiebridge.exception.RegisterRequestException;
import com.github.mxsm.magpiebridge.processor.RegistrationCenterProcessor;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.remoting.RemotingClient;
import com.github.mxsm.remoting.common.RequestCode;
import com.github.mxsm.remoting.common.ResponseCode;
import com.github.mxsm.remoting.exception.RemotingConnectException;
import com.github.mxsm.remoting.exception.RemotingSendRequestException;
import com.github.mxsm.remoting.exception.RemotingTimeoutException;
import com.github.mxsm.remoting.exception.RemotingTooMuchRequestException;
import com.github.mxsm.remoting.netty.NettyClientConfig;
import com.github.mxsm.remoting.netty.NettyRemotingClient;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final String magpieBridgeAddress;

    private final MagpieBridgeConfig magpieBridgeConfig;

    private ExecutorService bmExecutorService = new ThreadPoolExecutor(GeneralUtils.getAvailableProcessors(),
        GeneralUtils.getAvailableProcessors() * 2, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<>(128),
        new NamedThreadFactory("MagpieBridgeAPI_Thread"));

    private ExecutorService registerExecutor = Executors.newFixedThreadPool(4,
        new NamedThreadFactory("RegisterExecutorThread"));
    ;

    public MagpieBridgeAPI(NettyClientConfig nettyClientConfig, String magpieBridgeAddress,
        MagpieBridgeConfig magpieBridgeConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.magpieBridgeAddress = magpieBridgeAddress;
        this.magpieBridgeConfig = magpieBridgeConfig;
        this.remotingClient = new NettyRemotingClient(this.nettyClientConfig);
    }

    public void start() {
        registerProcessor();
        this.remotingClient.start();
    }

    public void shutdown() {
        this.remotingClient.shutdown();
    }

    public void updateRegisterAddressList(final List<String> addrs) {
        this.remotingClient.updateRegisterAddressList(addrs);
    }

    /**
     * 注册所有的
     *
     * @param mbInfo
     */
    public List<RemotingCommand> registerMagpieBridgeAll(final MagpieBridgeMetadata mbInfo, final long timeout) {

        final List<String> registerAddressList = this.remotingClient.getRegisterAddressList();
        if (CollectionUtils.isEmpty(registerAddressList)) {
            LOGGER.warn("Registration center address is empty, not center to register MagpieBridge");
            return null;
        }

        CountDownLatch registerLatch = new CountDownLatch(registerAddressList.size());
        List<RemotingCommand> registerMbResultList = new ArrayList<>();
        byte[] mbInfoBytes = JSON.toJSONBytes(mbInfo);
        final int crc32 = GeneralUtils.crc32(mbInfoBytes);

        for (String registerAddress : registerAddressList) {
            this.bmExecutorService.submit(() -> {
                try {
                    RemotingCommand request = RemotingCommandBuilder.buildRequestCommand()
                        .setCode(RequestCode.MAGPIE_BRIDGE_REGISTER).setPayloadCrc32(crc32)
                        .setPayload(ByteString.copyFrom(mbInfoBytes)).build();
                    RemotingCommand registerMbResult = MagpieBridgeAPI.this
                        .registerMagpieBridge(registerAddress, request, timeout, false);
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
            LOGGER.warn("get MagpieBridge register Result time out[timeout={}ms]", timeout);
        }
        return registerMbResultList;
    }

    /**
     * unregister all magpie bridge
     *
     * @param mbInfo
     * @param timeout
     * @return
     */
    public List<RemotingCommand> unRegisterMagpieBridgeAll(final MagpieBridgeMetadata mbInfo, final long timeout) {

        final List<String> registerAddressList = this.remotingClient.getRegisterAddressList();
        if (CollectionUtils.isEmpty(registerAddressList)) {
            LOGGER.warn("Registration center address is empty, not center to register MagpieBridge");
            return null;
        }
        byte[] mbInfoBytes = JSON.toJSONBytes(mbInfo);
        final int crc32 = GeneralUtils.crc32(mbInfoBytes);

        for (String registerAddress : registerAddressList) {
            this.bmExecutorService.submit(() -> {
                try {

                    RemotingCommand request = RemotingCommandBuilder.buildRequestCommand(true)
                        .setCode(RequestCode.MAGPIE_BRIDGE_UNREGISTER).setPayloadCrc32(crc32)
                        .setPayload(ByteString.copyFrom(mbInfoBytes)).build();
                    MagpieBridgeAPI.this.unRegisterMagpieBridge(registerAddress, request, timeout, true);

                    LOGGER.info("Register MagpieBridge[{}] SUCCESS", mbInfo.getMagpieBridgeName());
                } catch (Exception e) {
                    LOGGER.error(String.format("Register MagpieBridge Error to Registration[%s]", registerAddress), e);
                }
            });
        }
        return null;
    }

    /**
     * 注册鹊桥到注册中心
     *
     * @param registerAddress
     * @param request
     * @param timeoutMillis
     * @param oneway
     * @return
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     * @throws InterruptedException
     * @throws RegisterRequestException
     */
    private RemotingCommand registerMagpieBridge(final String registerAddress, final RemotingCommand request,
        final long timeoutMillis, final boolean oneway)
        throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException, RegisterRequestException {

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
            LOGGER.warn("Register MagpieBridge to registration center[{}], Return null", registerAddress);
            return null;
        }
        switch (response.getCode()) {
            case ResponseCode.SUCCESS:
                LOGGER.info("Register MagpieBridge to registration center[{}] SUCCESS", registerAddress);
                break;
            default:
                LOGGER.error("Register MagpieBridge Return code[{}],register error:{}", response.getCode(),
                    response.getResultMessage());
                throw new RegisterRequestException(response.getCode(), response.getResultMessage());
        }

        return response;
    }

    /**
     * 注销注册中心的鹊桥
     *
     * @param registerAddress
     * @param request
     * @param timeoutMillis
     * @param oneway
     * @return
     * @throws RemotingConnectException
     * @throws RemotingSendRequestException
     * @throws RemotingTimeoutException
     * @throws InterruptedException
     * @throws RegisterRequestException
     */
    private RemotingCommand unRegisterMagpieBridge(final String registerAddress, final RemotingCommand request,
        final long timeoutMillis, final boolean oneway)
        throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException, RegisterRequestException {

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
            LOGGER.warn("unregister MagpieBridge from registration center[{}], Return null", registerAddress);
            return null;
        }
        switch (response.getCode()) {
            case ResponseCode.SUCCESS:
                LOGGER.info("unregister MagpieBridge from registration center[{}] SUCCESS", registerAddress);
                break;
            default:
                LOGGER.error("unregister MagpieBridge Return code[{}],register error:{}", response.getCode(),
                    response.getResultMessage());
                throw new RegisterRequestException(response.getCode(), response.getResultMessage());
        }
        return response;
    }

    private void registerProcessor() {
        RegistrationCenterProcessor registrationCenterProcessor = new RegistrationCenterProcessor(
            this.magpieBridgeConfig, this.magpieBridgeAddress);
        this.remotingClient.registerProcessor(RequestCode.MAGPIE_BRIDGE_MASTER_CHANGE, registrationCenterProcessor,
            this.registerExecutor);
    }
}
