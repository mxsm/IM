package com.github.mxsm.register.mananger;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 鹊桥名称与信息的Map
     */
    private Map<String /*MagpieBridge Name*/, MagpieBridgeLiveInfo> magpieBridgeTable = new HashMap<>(128);

    /**
     * 注册鹊桥
     *
     * @param info
     */
    public void magpieBridgeRegistry(final MagpieBridgeLiveInfo info) {
        try {
            try {
                readWriteLock.writeLock().lockInterruptibly();
                MagpieBridgeLiveInfo liveInfo = magpieBridgeTable.get(info.getMagpieBridgeName());
                if (null != liveInfo) {
                    liveInfo.setOnline(true);
                    liveInfo.setLastHeartbeatTime(System.currentTimeMillis());
                    LOGGER.info("update magpie bridge[name={},address={}]", info.getMagpieBridgeName(),
                        info.getMagpieBridgeAddress());
                    return;
                }
                magpieBridgeTable.put(info.getMagpieBridgeName(), info);
                LOGGER.info("register magpie bridge[name={},address={}] SUCCESS", info.getMagpieBridgeName(),
                    info.getMagpieBridgeAddress());
            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Register Magpie Bridge Error", e);
        }
    }

    /**
     * 删除鹊桥
     *
     * @param queqiaoName
     */
    public void removeMagpieBridgeInfo(String magpieBridgeName) {

        try {
            try {
                readWriteLock.writeLock().lock();
                magpieBridgeTable.remove(magpieBridgeName);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Remove MagpieBridgeInfo Error", e);
        }
    }

}
