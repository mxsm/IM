package github.ant.mxsm.register.mananger;

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

    private MagpieBridgeInfo magpieBridgeInfo;

    @BeforeEach
    void setUp() {
        magpieBridgeManager = new MagpieBridgeManager();
        magpieBridgeInfo = new MagpieBridgeInfo();
        magpieBridgeInfo.setQueqiaoName(UUID.randomUUID().toString());
        magpieBridgeInfo.setConnRegisterTime(System.currentTimeMillis());
        magpieBridgeInfo.setLastHeartbeatTime(System.currentTimeMillis());
        magpieBridgeInfo.setOnline(true);
        magpieBridgeInfo.setQueqiaoAddress("127.0.0.1:7894");
    }

    @Test
    void queqiaoRegistry() {
        magpieBridgeManager.queqiaoRegistry(magpieBridgeInfo);
    }

    @Test
    void removeQueqiaoInfo() {
        magpieBridgeManager.removeQueqiaoInfo(magpieBridgeInfo.getQueqiaoName());
    }
}