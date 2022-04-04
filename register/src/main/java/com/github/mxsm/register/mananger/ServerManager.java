package com.github.mxsm.register.mananger;

import com.github.mxsm.common.Symbol;
import com.github.mxsm.protocol.protobuf.ServerMetadata;
import com.github.mxsm.protocol.protobuf.constant.ServerType;
import com.github.mxsm.register.exception.ServerHandleException;
import com.github.mxsm.remoting.common.NetUtils;
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
 * @Since 1.0.0
 */
public class ServerManager {

    public static final long SERVER_INACTIVE = 30 * 1000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Map<String /*Server address*/, ServerLiveInfo> serverLiveInfoTable = new HashMap<>(128);

    public void registryServer(final Channel channel, final ServerMetadata serverMetadata) {
        final String remoteAddress = NetUtils.intIpAddress2String(serverMetadata.getServerIp());
        final int port = serverMetadata.getServerPort();
        final String serverName = serverMetadata.getName();
        String addrWithPort = remoteAddress + Symbol.COLON + port;
        com.github.mxsm.common.enums.ServerType serverType =
            serverMetadata.getServerType() == ServerType.MAGPIE_BRIDGE
                ? com.github.mxsm.common.enums.ServerType.MAGPIE_BRIDGE
                : com.github.mxsm.common.enums.ServerType.GALAXY;
        try {
            readWriteLock.writeLock().lockInterruptibly();
            //get request server base info
            ServerLiveInfo liveInfo = serverLiveInfoTable.get(addrWithPort);
            long currentTimeMillis = System.currentTimeMillis();
            if (null != liveInfo) {
                liveInfo.setOnline(true);
                liveInfo.setLastUpdateTimestamp(currentTimeMillis);
                liveInfo.setClientNums(serverMetadata.getClientNums());
                liveInfo.addClientMetadatas(serverMetadata.getClientMetadataList());
                LOGGER.info("update magpie bridge Live[name={},address={}]", serverName, remoteAddress);
                return;
            }

            liveInfo = new ServerLiveInfo(currentTimeMillis, currentTimeMillis, channel,
                serverType);
            liveInfo.setClientNums(serverMetadata.getClientNums());
            liveInfo.addClientMetadatas(serverMetadata.getClientMetadataList());
            serverLiveInfoTable.put(remoteAddress, liveInfo);
            LOGGER.info("register magpie bridge Live[name={},address={}] SUCCESS", serverName, remoteAddress);

        } catch (Exception e){
            String errorMsg = String.format("Register Server [Type=%s,Name=%s,Address=%s]Error", serverType.name(),serverName,addrWithPort);
            throw new ServerHandleException(errorMsg, addrWithPort);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }


    public void unRegistryServer(final Channel channel, final ServerMetadata serverMetadata) {

        final String remoteAddress = NetUtils.intIpAddress2String(serverMetadata.getServerIp());
        final int port = serverMetadata.getServerPort();        final String serverName = serverMetadata.getName();
        String addrWithPort = remoteAddress + Symbol.COLON + port;
            try {
                this.readWriteLock.writeLock().lockInterruptibly();
                this.serverLiveInfoTable.remove(addrWithPort);
                NettyUtils.closeChannel(channel);
            }catch (Exception e) {
                LOGGER.error("closeChannelOnException error", e);
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
    }



    public void scanInactiveServer() {

        Iterator<Entry<String, ServerLiveInfo>> iterator = serverLiveInfoTable.entrySet().iterator();
        long nowTimestamp = System.currentTimeMillis();
        while (iterator.hasNext()) {
            Entry<String, ServerLiveInfo> next = iterator.next();
            ServerLiveInfo mbLiveInfo = next.getValue();
            long intervalTime = nowTimestamp - mbLiveInfo.getLastUpdateTimestamp();
            if (intervalTime > SERVER_INACTIVE) {
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
                    Iterator<Entry<String, ServerLiveInfo>> mbIterator = serverLiveInfoTable.entrySet()
                        .iterator();
                    while (mbIterator.hasNext()) {
                        Entry<String, ServerLiveInfo> mbNext = mbIterator.next();
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
                this.serverLiveInfoTable.remove(mbAddressFound);
                LOGGER.info("server[{}] removed from serverLiveInfoTable",mbAddressFound);
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("closeChannelOnException error", e);
        }
    }

}
