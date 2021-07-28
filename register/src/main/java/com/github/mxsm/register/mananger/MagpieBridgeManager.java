package com.github.mxsm.register.mananger;

import com.github.mxsm.common.magpiebridge.MagpieBridgeInfo;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.register.mananger.MagpieBridgeMetaData.MagpieBridgeInnerMetaData;
import com.github.mxsm.remoting.common.NettyUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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

    public static final long MAGPIE_BRIDGE_INACTIVE = 30 * 1000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Map<String /*MagpieBridge cluster Name*/, MagpieBridgeMetaData> magpieBridgeMetaDataTable = new HashMap<>(
        128);

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

                boolean isFirstRegister = false;
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
                    isFirstRegister = true;
                }

                MagpieBridgeMetaData magpieBridgeMetaData = magpieBridgeMetaDataTable.get(magpieBridgeName);
                if (magpieBridgeMetaData == null) {
                    magpieBridgeMetaData = new MagpieBridgeMetaData(clusterName);
                    magpieBridgeMetaData.getMagpieBridgeAddresses()
                        .put(magpieBridgeId, new MagpieBridgeInnerMetaData(magpieBridgeName, remoteAddress));
                    magpieBridgeMetaDataTable.put(magpieBridgeName, magpieBridgeMetaData);
                    LOGGER.info("register magpie bridge metadata[name={},address={}] SUCCESS", magpieBridgeName,
                        remoteAddress);
                } else {
                    //make sure new register mb id is largest
                    if (isFirstRegister) {
                        Long lastMbId = magpieBridgeMetaData.getMagpieBridgeAddresses().lastKey();
                        if (magpieBridgeId <= lastMbId.longValue()) {
                            magpieBridgeId = lastMbId + 1;
                            magpieBridgeMetaData.getMagpieBridgeAddresses()
                                .put(magpieBridgeId, new MagpieBridgeInnerMetaData(magpieBridgeName, remoteAddress));
                        }
                    }
                }
                magpieBridgeMetaData.getMagpieBridgeNameSet().add(magpieBridgeName);
                Entry<Long, MagpieBridgeInnerMetaData> mbAdressEntry = magpieBridgeMetaData.getMagpieBridgeAddresses()
                    .firstEntry();
                return new RegisterMagpieBridgeResult(magpieBridgeId, mbAdressEntry.getKey(),
                    mbAdressEntry.getValue().getMagpieBridgeAddress());
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
                    .get(mbInfo.getMagpieBridgeClusterName());
                if (null != magpieBridgeMetaData) {
                    long magpieBridgeId = mbInfo.getMagpieBridgeId();
                    long masterMbId = magpieBridgeMetaData.getMagpieBridgeAddresses().firstKey();

                    MagpieBridgeInnerMetaData removeMb = magpieBridgeMetaData.getMagpieBridgeAddresses()
                        .remove(magpieBridgeId);
                    LOGGER.info("Unregister MagpieBridge from MagpieBridgeAddresses table ID[{}],MagpieBridge[{}]",
                        magpieBridgeId, removeMb.getMagpieBridgeAddress());
                    if (magpieBridgeMetaData.getMagpieBridgeAddresses().isEmpty()) {
                        this.magpieBridgeMetaDataTable.remove(mbInfo.getMagpieBridgeClusterName());
                    }

                    // remove mb name for MagpieBridgeMetaData mb set
                    Set<Entry<Long, MagpieBridgeInnerMetaData>> entries = magpieBridgeMetaData.getMagpieBridgeAddresses()
                        .entrySet();
                    Iterator<Entry<Long, MagpieBridgeInnerMetaData>> iterator = entries.iterator();
                    boolean removeMbNameFromMbSet = true;
                    while (iterator.hasNext()) {
                        Entry<Long, MagpieBridgeInnerMetaData> next = iterator.next();
                        if (StringUtils.equals(next.getValue().getMagpieBridgeName(), removeMb.getMagpieBridgeName())) {
                            removeMbNameFromMbSet = false;
                            break;
                        }
                    }
                    if (removeMbNameFromMbSet) {
                        magpieBridgeMetaData.getMagpieBridgeNameSet().remove(removeMb.getMagpieBridgeName());
                    }

                    if (magpieBridgeId == masterMbId) {
                        Entry<Long, MagpieBridgeInnerMetaData> mbAdressEntry = magpieBridgeMetaData.getMagpieBridgeAddresses()
                            .firstEntry();
                        final long newMasterMbId = mbAdressEntry.getKey();
                        final String newMasterMbAddress = mbAdressEntry.getValue().getMagpieBridgeAddress();
                        magpieBridgeMetaData.getMagpieBridgeAddresses().entrySet().forEach(entry -> {
                            MagpieBridgeLiveInfo magpieBridgeLiveInfo = magpieBridgeLiveTable.get(
                                entry.getValue().getMagpieBridgeAddress());
                            if (magpieBridgeLiveInfo != null && null != magpieBridgeLiveInfo.getChannel()
                                && magpieBridgeLiveInfo.getChannel().isActive()) {
                                magpieBridgeLiveInfo.getChannel().writeAndFlush(null)
                                    .addListener((ChannelFutureListener) future -> {

                                    });
                            }
                        });
                    }

                }
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
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
                    Map<Long, MagpieBridgeInnerMetaData> magpieBridgeAddresses = metaData.getMagpieBridgeAddresses();
                    Iterator<Entry<Long, MagpieBridgeInnerMetaData>> mbIt = magpieBridgeAddresses.entrySet().iterator();
                    while (mbIt.hasNext()) {
                        Entry<Long, MagpieBridgeInnerMetaData> metaNext = mbIt.next();
                        if (StringUtils.equals(metaNext.getValue().getMagpieBridgeAddress(), mbAddressFound)) {
                            magpieBridgeNameFound = next.getKey();
                            mbIt.remove();
                            LOGGER.info(
                                "remove MagpieBridge address[{}, {}] from magpieBridgeAddresses, because channel destroyed",
                                metaNext.getValue().getMagpieBridgeName(), mbAddressFound);
                            break;
                        }
                    }
                    if (magpieBridgeAddresses.isEmpty()) {
                        iterator.remove();
                       /* LOGGER.info(
                            "remove MagpieBridge Name[{}, {}] from magpieBridgeMetaDataTable, because channel destroyed",
                            metaNext.getMagpieBridgeName(), mbAddressFound);*/
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
