package com.github.mxsm.register.mananger;

import com.github.mxsm.remoting.common.NettyUtils;
import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class MagpieBridgeManager {

    public static final long MAGPIE_BRIDGE_INACTIVE = 10 * 1000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Map<String /*MagpieBridge Name*/, MagpieBridgeMetaData> magpieBridgeMetaDataTable = new HashMap<>(
        128);

    private final Map<String /*MagpieBridge address*/, MagpieBridgeLiveInfo> magpieBridgeLiveTable = new HashMap<>(128);

    public void registerMagpieBridge(final Channel channel, final String remoteAddress, final String magpieBridgeName) {
        try {
            try {

                readWriteLock.writeLock().lockInterruptibly();
                MagpieBridgeLiveInfo liveInfo = magpieBridgeLiveTable.get(remoteAddress);
                if (null != liveInfo) {
                    liveInfo.setOnline(true);
                    long lastUpdateTimestamp = System.currentTimeMillis();
                    liveInfo.setLastUpdateTimestamp(lastUpdateTimestamp);
                    liveInfo.setConnectionTimestamp(lastUpdateTimestamp);
                    LOGGER.info("update magpie bridge[name={},address={}]", magpieBridgeName, remoteAddress);
                } else {
                    liveInfo = new MagpieBridgeLiveInfo(System.currentTimeMillis(), System.currentTimeMillis(), channel,
                        true);
                    magpieBridgeLiveTable.put(remoteAddress, liveInfo);
                    LOGGER.info("register magpie bridge[name={},address={}] SUCCESS", magpieBridgeName, remoteAddress);
                }
                MagpieBridgeMetaData magpieBridgeMetaData = magpieBridgeMetaDataTable.get(magpieBridgeName);
                if (magpieBridgeMetaData == null) {
                    magpieBridgeMetaData = new MagpieBridgeMetaData(magpieBridgeName, remoteAddress);
                    magpieBridgeMetaDataTable.put(magpieBridgeName, magpieBridgeMetaData);
                }

            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Register Magpie Bridge Error", e);
        }
    }


    public void unRegisterMagpieBridge(final Channel channel, final String remoteAddress, final String magpieBridgeName){

        try {
            try {
                this.readWriteLock.writeLock().lockInterruptibly();
                this.magpieBridgeLiveTable.remove(remoteAddress);
                Iterator<Entry<String, MagpieBridgeMetaData>> iterator = this.magpieBridgeMetaDataTable.entrySet()
                    .iterator();
                while (iterator.hasNext()) {
                    Entry<String, MagpieBridgeMetaData> next = iterator.next();
                    MagpieBridgeMetaData metaData = next.getValue();
                    if (StringUtils.equals(metaData.getMagpieBridgeAddress(), remoteAddress)) {
                        iterator.remove();
                        LOGGER.info(
                            "remove MagpieBridge[{}, {}] from magpieBridgeMetaDataTable, because MagpieBridge unregister",
                            magpieBridgeName, remoteAddress);
                    }
                }
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
            NettyUtils.closeChannel(channel);
        } catch (Exception e) {
            LOGGER.error("closeChannelOnException error", e);
        }

    }


    public void scanInactiveMagpieBridge() {

        Iterator<Entry<String, MagpieBridgeLiveInfo>> iterator = magpieBridgeLiveTable.entrySet().iterator();
        long nowTimestamp = System.currentTimeMillis();
        while (iterator.hasNext()) {
            Entry<String, MagpieBridgeLiveInfo> next = iterator.next();
            MagpieBridgeLiveInfo mbLiveInfo = next.getValue();
            long intervalTime = nowTimestamp - mbLiveInfo.getLastUpdateTimestamp();
            if (intervalTime > MAGPIE_BRIDGE_INACTIVE) {
                iterator.remove();
                NettyUtils.closeChannel(mbLiveInfo.getChannel());
                LOGGER.warn("Magpie Bridge[{}] expired! [{}]",next.getKey(),intervalTime);
                closeChannelOnException(next.getKey(),mbLiveInfo.getChannel());
            }
        }
    }

    public void closeChannelOnException(final String remoteAddress, final Channel channel) {

        String mbAddressFound = null;

        if (null != channel) {
            try {
                try {
                    this.readWriteLock.readLock().lockInterruptibly();
                    Iterator<Entry<String, MagpieBridgeLiveInfo>> mbIterator = magpieBridgeLiveTable.entrySet()
                        .iterator();
                    while (mbIterator.hasNext()) {
                        Entry<String, MagpieBridgeLiveInfo> mbNext = mbIterator.next();
                        if (mbNext.getValue().getChannel() == channel) {
                            mbAddressFound = mbNext.getKey();
                            break;
                        }
                    }
                } finally {
                    readWriteLock.readLock().unlock();
                }
            } catch (InterruptedException e) {
                LOGGER.error("close channel ON Exception error", e);
            }
        }

        if (mbAddressFound == null) {
            mbAddressFound = remoteAddress;
        }

        if (StringUtils.isBlank(mbAddressFound)) {
            LOGGER.warn("close channel ON Exception not found ");
            return;
        }

        try {
            try {
                this.readWriteLock.writeLock().lockInterruptibly();
                this.magpieBridgeLiveTable.remove(mbAddressFound);
                Iterator<Entry<String, MagpieBridgeMetaData>> iterator = this.magpieBridgeMetaDataTable.entrySet()
                    .iterator();
                while (iterator.hasNext()) {
                    Entry<String, MagpieBridgeMetaData> next = iterator.next();
                    MagpieBridgeMetaData metaData = next.getValue();
                    if (StringUtils.equals(metaData.getMagpieBridgeAddress(), mbAddressFound)) {
                        iterator.remove();
                        LOGGER.info(
                            "remove MagpieBridge[{}, {}] from magpieBridgeMetaDataTable, because channel destroyed",
                            metaData.getMagpieBridgeName(), metaData.getMagpieBridgeAddress());
                    }
                }
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("closeChannelOnException error", e);
        }
    }

}
