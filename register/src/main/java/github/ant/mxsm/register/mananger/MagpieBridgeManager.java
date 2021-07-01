package github.ant.mxsm.register.mananger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class MagpieBridgeManager {

    private static final  Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 鹊桥名称与信息的Map
     */
    private Map<String /*鹊桥名称*/, MagpieBridgeInfo> magpieBridgeTable = new HashMap<>(128);

    /**
     * 注册鹊桥
     * @param info
     */
    public void queqiaoRegistry(MagpieBridgeInfo info){
        MagpieBridgeInfo magpieBridgeInfo = magpieBridgeTable.putIfAbsent(info.getMagpieBridgeName(), info);
        if(magpieBridgeInfo != null){
            LOGGER.info("名称为:{}的鹊桥在鹊桥管理器中存在",info.getMagpieBridgeName());
        }
    }

    /**
     * 删除鹊桥
     * @param queqiaoName
     */
    public void removeQueqiaoInfo(String queqiaoName){

        try {
            try {
                readWriteLock.writeLock().lock();
                magpieBridgeTable.remove(queqiaoName);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Remove MagpieBridgeInfo Error",e);
        }
    }

}
