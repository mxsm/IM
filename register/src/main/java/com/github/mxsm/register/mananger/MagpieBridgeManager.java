package com.github.mxsm.register.mananger;

import com.alibaba.fastjson.JSON;
import com.github.mxsm.common.magpiebridge.MagpieBridgeMetadata;
import com.github.mxsm.common.register.RegisterMagpieBridgeResult;
import com.github.mxsm.common.utils.GeneralUtils;
import com.github.mxsm.protocol.protobuf.RemotingCommand;
import com.github.mxsm.protocol.utils.RemotingCommandBuilder;
import com.github.mxsm.register.strategy.SelectMagpieBridgeStrategy;
import com.github.mxsm.remoting.common.NettyUtils;
import com.github.mxsm.remoting.common.RequestCode;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 1.0.0
 */
public class MagpieBridgeManager {

    public static final long MAGPIE_BRIDGE_INACTIVE = 30 * 1000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieBridgeManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Map<String /*MagpieBridge cluster Name*/, MagpieBridgeClusterMetaData> magpieBridgeMetaDataTable = new HashMap<>(
        128);

    private final Map<String /*MagpieBridge address*/, MagpieBridgeLiveInfo> magpieBridgeLiveTable = new HashMap<>(128);

    public RegisterMagpieBridgeResult registerMagpieBridge(final Channel channel, final MagpieBridgeMetadata mbInfo) {
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
                    liveInfo.setClientNums(mbInfo.getClientNums());
                    liveInfo.addClientMetadata(mbInfo.getClientMetadataSet());
                    LOGGER.info("update magpie bridge Live[name={},address={}]", magpieBridgeName, remoteAddress);
                } else {
                    liveInfo = new MagpieBridgeLiveInfo(System.currentTimeMillis(), System.currentTimeMillis(), channel,
                        true);
                    liveInfo.setClientNums(mbInfo.getClientNums());
                    liveInfo.addClientMetadata(mbInfo.getClientMetadataSet());
                    liveInfo.setMagpieBridgeAddress(remoteAddress);
                    liveInfo.setMagpieBridgeName(magpieBridgeName);
                    liveInfo.setMagpieBridgeId(magpieBridgeId);
                    liveInfo.setMagpieBridgeClusterName(clusterName);
                    liveInfo.setPort(mbInfo.getPort());
                    liveInfo.setIp(mbInfo.getIp());
                    magpieBridgeLiveTable.put(remoteAddress, liveInfo);
                    LOGGER.info("register magpie bridge Live[name={},address={}] SUCCESS", magpieBridgeName,
                        remoteAddress);
                    isFirstRegister = true;
                }

                MagpieBridgeClusterMetaData magpieBridgeClusterMetaData = magpieBridgeMetaDataTable.get(clusterName);
                if (magpieBridgeClusterMetaData == null) {
                    magpieBridgeClusterMetaData = new MagpieBridgeClusterMetaData(clusterName);
                    magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                        .put(magpieBridgeId, new MagpieBridgeMetaData(magpieBridgeName, remoteAddress));
                    magpieBridgeMetaDataTable.put(clusterName, magpieBridgeClusterMetaData);
                    LOGGER.info("register magpie bridge metadata[name={},address={}] SUCCESS", magpieBridgeName,
                        remoteAddress);
                } else {
                    //make sure new register mb id is largest
                    if (isFirstRegister) {
                        Long lastMbId = magpieBridgeClusterMetaData.getMagpieBridgeAddresses().lastKey();
                        if (magpieBridgeId <= lastMbId.longValue()) {
                            magpieBridgeId = lastMbId + 1;
                            magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                                .put(magpieBridgeId, new MagpieBridgeMetaData(magpieBridgeName, remoteAddress));
                        }
                    }
                }
                magpieBridgeClusterMetaData.getMagpieBridgeNameSet().add(magpieBridgeName);
                Entry<Long, MagpieBridgeMetaData> mbAdressEntry = magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
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


    public void unRegisterMagpieBridge(final Channel channel, final MagpieBridgeMetadata mbInfo) {

        try {
            try {
                this.readWriteLock.writeLock().lockInterruptibly();
                this.magpieBridgeLiveTable.remove(mbInfo.getMagpieBridgeAddress());

                MagpieBridgeClusterMetaData magpieBridgeClusterMetaData = this.magpieBridgeMetaDataTable
                    .get(mbInfo.getMagpieBridgeClusterName());
                if (null != magpieBridgeClusterMetaData) {
                    long magpieBridgeIdToRemove = mbInfo.getMagpieBridgeId();
                    long masterMbId = magpieBridgeClusterMetaData.getMagpieBridgeAddresses().firstKey();

                    MagpieBridgeMetaData removeMb = magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                        .remove(magpieBridgeIdToRemove);
                    LOGGER.info("Unregister MagpieBridge from MagpieBridgeAddresses table ID[{}],MagpieBridge[{}]",
                        magpieBridgeIdToRemove, removeMb.getMagpieBridgeAddress());
                    if (magpieBridgeClusterMetaData.getMagpieBridgeAddresses().isEmpty()) {
                        this.magpieBridgeMetaDataTable.remove(mbInfo.getMagpieBridgeClusterName());
                    }

                    // remove mb name for MagpieBridgeMetaData mb set
                    Set<Entry<Long, MagpieBridgeMetaData>> entries = magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                        .entrySet();
                    Iterator<Entry<Long, MagpieBridgeMetaData>> iterator = entries.iterator();
                    boolean removeMbNameFromMbSet = true;
                    while (iterator.hasNext()) {
                        Entry<Long, MagpieBridgeMetaData> next = iterator.next();
                        if (StringUtils.equals(next.getValue().getMagpieBridgeName(), removeMb.getMagpieBridgeName())) {
                            removeMbNameFromMbSet = false;
                            break;
                        }
                    }
                    if (removeMbNameFromMbSet) {
                        magpieBridgeClusterMetaData.getMagpieBridgeNameSet().remove(removeMb.getMagpieBridgeName());
                    }

                    if (magpieBridgeIdToRemove == masterMbId && !magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                        .isEmpty()) {
                        Entry<Long, MagpieBridgeMetaData> mbAdressEntry = magpieBridgeClusterMetaData.getMagpieBridgeAddresses()
                            .firstEntry();
                        final long newMasterMbId = mbAdressEntry.getKey();
                        final String newMasterMbAddress = mbAdressEntry.getValue().getMagpieBridgeAddress();
                        magpieBridgeClusterMetaData.getMagpieBridgeAddresses().entrySet()
                            .forEach(doHandleMbMasterRoleChange(newMasterMbId, newMasterMbAddress));
                    }

                }
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("closeChannelOnException error", e);
        }

    }

    private Consumer<Entry<Long, MagpieBridgeMetaData>> doHandleMbMasterRoleChange(long newMasterMbId,
        String newMasterMbAddress) {
        return entry -> {
            MagpieBridgeLiveInfo magpieBridgeLiveInfo = this.magpieBridgeLiveTable.get(
                entry.getValue().getMagpieBridgeAddress());
            if (magpieBridgeLiveInfo != null && null != magpieBridgeLiveInfo.getChannel()
                && magpieBridgeLiveInfo.getChannel().isActive()) {
                RegisterMagpieBridgeResult result = new RegisterMagpieBridgeResult(entry.getKey(),
                    newMasterMbId, newMasterMbAddress);
                byte[] resultBytes = JSON.toJSONBytes(result);
                RemotingCommand masterRoleChange = RemotingCommandBuilder.buildRequestCommand(true)
                    .setPayload(ByteString.copyFrom(resultBytes))
                    .setCode(RequestCode.MAGPIE_BRIDGE_MASTER_CHANGE)
                    .setPayloadCrc32(GeneralUtils.crc32(resultBytes)).build();
                magpieBridgeLiveInfo.getChannel().writeAndFlush(masterRoleChange)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            LOGGER.info(
                                "Send mb role change note success.[toAddress={},masterId={},masterAddress={}]",
                                newMasterMbId, newMasterMbAddress);
                        } else {
                            LOGGER.info(
                                "Send mb role change note fail.[toAddress={},masterId={},masterAddress={}]",
                                newMasterMbId, newMasterMbAddress);
                        }
                    });
            }
        };
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
                //remove mb from live table
                this.magpieBridgeLiveTable.remove(mbAddressFound);

                String magpieBridgeNameFound = null;
                Iterator<Entry<String, MagpieBridgeClusterMetaData>> iterator = this.magpieBridgeMetaDataTable.entrySet()
                    .iterator();
                while (iterator.hasNext() && (magpieBridgeNameFound == null)) {
                    Entry<String, MagpieBridgeClusterMetaData> next = iterator.next();
                    MagpieBridgeClusterMetaData clusterMetaData = next.getValue();
                    TreeMap<Long, MagpieBridgeMetaData> magpieBridgeAddresses = clusterMetaData.getMagpieBridgeAddresses();
                    Iterator<Entry<Long, MagpieBridgeMetaData>> mbIt = magpieBridgeAddresses.entrySet().iterator();

                    while (mbIt.hasNext()) {
                        final long masterId = magpieBridgeAddresses.firstKey();
                        Entry<Long, MagpieBridgeMetaData> metaNext = mbIt.next();
                        if (StringUtils.equals(metaNext.getValue().getMagpieBridgeAddress(), mbAddressFound)) {
                            magpieBridgeNameFound = metaNext.getValue().getMagpieBridgeName();
                            long mbMasterIdToRemove = metaNext.getKey();
                            mbIt.remove();
                            LOGGER.info(
                                "remove MagpieBridge address[{}, {}] from magpieBridgeAddresses, because channel destroyed",
                                metaNext.getValue().getMagpieBridgeName(), mbAddressFound);
                            if(masterId == mbMasterIdToRemove){
                                Entry<Long, MagpieBridgeMetaData> entry = magpieBridgeAddresses.firstEntry();
                                //magpieBridgeAddresses is empty(all mb removed)
                                if(null == entry){
                                    break;
                                }
                                final long newMbMasterId = entry.getKey();
                                final String newMbMasterAddress = entry.getValue().getMagpieBridgeAddress();
                                magpieBridgeAddresses.entrySet()
                                    .forEach(doHandleMbMasterRoleChange(newMbMasterId, newMbMasterAddress));
                            }
                            break;
                        }
                    }
                    if (magpieBridgeAddresses.isEmpty()) {
                        iterator.remove();
                    }else{
                        boolean removeMbNameFromSet = true;
                        Iterator<Entry<Long, MagpieBridgeMetaData>> mbAddressIt = magpieBridgeAddresses.entrySet()
                            .iterator();
                        while (mbAddressIt.hasNext()){
                            Entry<Long, MagpieBridgeMetaData> itNext = mbAddressIt.next();
                            if(StringUtils.equals(itNext.getValue().getMagpieBridgeName(), magpieBridgeNameFound)){
                                removeMbNameFromSet = false;
                                break;
                            }
                        }
                        if(removeMbNameFromSet){
                            clusterMetaData.getMagpieBridgeNameSet().remove(magpieBridgeNameFound);
                        }
                    }
                }
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("closeChannelOnException error", e);
        }
    }

    public MagpieBridgeMetadata getMagpieBridge(SelectMagpieBridgeStrategy strategy){
        MagpieBridgeMetadata selectMb = strategy.select(this.magpieBridgeLiveTable.values());
        return selectMb;
    }

}
