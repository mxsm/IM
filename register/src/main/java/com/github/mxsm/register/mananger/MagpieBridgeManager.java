package com.github.mxsm.register.mananger;

import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.remoting.common.NettyUtils;
import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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

    private final Map<String/*cluster name*/, Set<String> /*mb name*/> clusterTable = new HashMap<>(128);

    private final Map<String /*MagpieBridge address*/, MagpieBridgeLiveInfo> magpieBridgeLiveTable = new HashMap<>(128);

    public RegisterMagpieBridgeResult registerMagpieBridge(final Channel channel, final MagpieBridgeInfo mbInfo) {
        try {
            try {
                readWriteLock.writeLock().lockInterruptibly();

                //get request mb(MagpieBridge) base info
                final String remoteAddress = mbInfo.getMagpieBridgeAddress();
                final String magpieBridgeName = mbInfo.getMagpieBridgeName();
                final String clusterName = mbInfo.getMagpieBridgeClusterName();
                long magpieBridgeId = mbInfo.getMagpieBridgeId();

                Set<String> mbNames = clusterTable.get(clusterName);
                if(mbNames == null){
                    mbNames = new HashSet<>();
                    clusterTable.put(clusterName, mbNames);
                }
                mbNames.add(magpieBridgeName);

                MagpieBridgeMetaData magpieBridgeMetaData = magpieBridgeMetaDataTable.get(magpieBridgeName);
                if (magpieBridgeMetaData == null) {
                    magpieBridgeMetaData = new MagpieBridgeMetaData(magpieBridgeName,
                        clusterName);
                    magpieBridgeMetaData.getMagpieBridgeAddresses().put(magpieBridgeId, remoteAddress);
                    magpieBridgeMetaDataTable.put(magpieBridgeName, magpieBridgeMetaData);
                    LOGGER.info("register magpie bridge metadata[name={},address={}] SUCCESS", magpieBridgeName,
                        remoteAddress);
                }else{
                    Set<Long> mbIdSet = magpieBridgeMetaData.getMagpieBridgeAddresses().keySet();
                    if(!mbIdSet.contains(magpieBridgeId)){
                        //make sure new register mb id is largest
                        Long lastMbId = magpieBridgeMetaData.getMagpieBridgeAddresses().lastKey();
                       if(magpieBridgeId <= lastMbId.longValue()){
                           magpieBridgeId = lastMbId + 1;
                           magpieBridgeMetaData.getMagpieBridgeAddresses().put(magpieBridgeId,remoteAddress);
                       }
                    }
                }
                MagpieBridgeLiveInfo liveInfo = magpieBridgeLiveTable.get(remoteAddress);
                if (null != liveInfo) {
                    liveInfo.setOnline(true);
                    long lastUpdateTimestamp = System.currentTimeMillis();
                    liveInfo.setLastUpdateTimestamp(lastUpdateTimestamp);
                    LOGGER.info("update magpie bridge Live[name={},address={}]", magpieBridgeName, remoteAddress);
                } else {
                    liveInfo = new MagpieBridgeLiveInfo(System.currentTimeMillis(), System.currentTimeMillis(), channel,
                        true);
                    magpieBridgeLiveTable.put(remoteAddress, liveInfo);
                    LOGGER.info("register magpie bridge Live[name={},address={}] SUCCESS", magpieBridgeName,
                        remoteAddress);
                }

                Entry<Long, String> mbAdressEntry = magpieBridgeMetaData.getMagpieBridgeAddresses().firstEntry();
                return new RegisterMagpieBridgeResult(magpieBridgeId,mbAdressEntry.getKey(),mbAdressEntry.getValue());
            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Register Magpie Bridge Error", e);

        }
        return new RegisterMagpieBridgeResult();
    }


    public void unRegisterMagpieBridge(final Channel channel, final MagpieBridgeInfo mbInfo) {

        try {
            try {
                this.readWriteLock.writeLock().lockInterruptibly();
                this.magpieBridgeLiveTable.remove(mbInfo.getMagpieBridgeAddress());

                MagpieBridgeMetaData magpieBridgeMetaData = this.magpieBridgeMetaDataTable
                    .get(mbInfo.getMagpieBridgeName());
                if (null != magpieBridgeMetaData) {
                    long magpieBridgeId = mbInfo.getMagpieBridgeId();
                    String address = magpieBridgeMetaData.getMagpieBridgeAddresses().remove(magpieBridgeId);
                    LOGGER.info("Unregister MagpieBridge from MagpieBridgeAddresses table ID[{}],MagpieBridge[{}]",
                        magpieBridgeId, address);
                    if (magpieBridgeMetaData.getMagpieBridgeAddresses().isEmpty()) {
                        this.magpieBridgeMetaDataTable.remove(mbInfo.getMagpieBridgeName());
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
                LOGGER.warn("Magpie Bridge[{}] expired! [{}]", next.getKey(), intervalTime);
                closeChannelOnException(next.getKey(), mbLiveInfo.getChannel());
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
                String magpieBridgeNameFound = null;
                Iterator<Entry<String, MagpieBridgeMetaData>> iterator = this.magpieBridgeMetaDataTable.entrySet()
                    .iterator();
                while (iterator.hasNext() && (magpieBridgeNameFound == null)) {
                    Entry<String, MagpieBridgeMetaData> next = iterator.next();
                    MagpieBridgeMetaData metaData = next.getValue();
                    Map<Long, String> magpieBridgeAddresses = metaData.getMagpieBridgeAddresses();
                    Iterator<Entry<Long, String>> mbIt = magpieBridgeAddresses.entrySet().iterator();
                    while (mbIt.hasNext()) {
                        Entry<Long, String> metaNext = mbIt.next();
                        if (StringUtils.equals(metaNext.getValue(), mbAddressFound)) {
                            magpieBridgeNameFound = next.getKey();
                            mbIt.remove();
                            LOGGER.info(
                                "remove MagpieBridge address[{}, {}] from magpieBridgeAddresses, because channel destroyed",
                                metaData.getMagpieBridgeName(), mbAddressFound);
                            break;
                        }
                    }
                    if (magpieBridgeAddresses.isEmpty()) {
                        iterator.remove();
                        LOGGER.info(
                            "remove MagpieBridge Name[{}, {}] from magpieBridgeMetaDataTable, because channel destroyed",
                            metaData.getMagpieBridgeName(), mbAddressFound);
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
