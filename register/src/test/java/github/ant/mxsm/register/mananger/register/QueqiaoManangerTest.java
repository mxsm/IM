package github.ant.mxsm.register.mananger.register;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since
 */
class QueqiaoManangerTest {

    private QueqiaoMananger queqiaoMananger;

    private QueQiaoInfo queQiaoInfo;

    @BeforeEach
    void setUp() {
        queqiaoMananger = new QueqiaoMananger();
        queQiaoInfo = new QueQiaoInfo();
        queQiaoInfo.setQueqiaoName(UUID.randomUUID().toString());
        queQiaoInfo.setConnRegisterTime(System.currentTimeMillis());
        queQiaoInfo.setLastHeartbeatTime(System.currentTimeMillis());
        queQiaoInfo.setOnline(true);
        queQiaoInfo.setQueqiaoAddress("127.0.0.1:7894");
    }

    @Test
    void queqiaoRegistry() {
        queqiaoMananger.queqiaoRegistry(queQiaoInfo);
    }

    @Test
    void removeQueqiaoInfo() {
        queqiaoMananger.removeQueqiaoInfo(queQiaoInfo.getQueqiaoName());
    }
}