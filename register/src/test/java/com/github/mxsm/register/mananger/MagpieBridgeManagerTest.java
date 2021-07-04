package com.github.mxsm.register.mananger;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since
 */
class MagpieBridgeManagerTest {

    private MagpieBridgeManager magpieBridgeManager;

    private MagpieBridgeLiveInfo magpieBridgeLiveInfo;

    @BeforeEach
    void setUp() {
        magpieBridgeManager = new MagpieBridgeManager();
        magpieBridgeLiveInfo = new MagpieBridgeLiveInfo();
        magpieBridgeLiveInfo.setMagpieBridgeName(UUID.randomUUID().toString());
        magpieBridgeLiveInfo.setConnRegisterTime(System.currentTimeMillis());
        magpieBridgeLiveInfo.setLastHeartbeatTime(System.currentTimeMillis());
        magpieBridgeLiveInfo.setOnline(true);
        magpieBridgeLiveInfo.setMagpieBridgeAddress("127.0.0.1:7894");
    }

    @Test
    void queqiaoRegistry() {
        magpieBridgeManager.magpieBridgeRegistry(magpieBridgeLiveInfo);
    }

    @Test
    void removeQueqiaoInfo() {
        magpieBridgeManager.removeMagpieBridgeInfo(magpieBridgeLiveInfo.getMagpieBridgeName());
    }
}