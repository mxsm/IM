package com.github.mxsm.common.magpiebridge;

import com.github.mxsm.common.Address;
import com.github.mxsm.common.client.ClientMetadata;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mxsm
 * @Date 2021/7/3
 * @Since 0.1
 */
public class MagpieBridgeMetadata extends Address {

    //name
    private String magpieBridgeName;

    //adress: ip:port
    private String magpieBridgeAddress;

    //id
    private long magpieBridgeId;

    //createTime
    private long magpieBridgeCreateTimestamp;

    private String magpieBridgeClusterName;

    private MagpieBridgeRole magpieBridgeRole;

    private Set<ClientMetadata> clientMetadataSet = new HashSet<>();

    /**
     * connection the mb server clients numbers
     */
    private long clientNums;

    public String getMagpieBridgeName() {
        return magpieBridgeName;
    }

    public void setMagpieBridgeName(String magpieBridgeName) {
        this.magpieBridgeName = magpieBridgeName;
    }

    public String getMagpieBridgeAddress() {
        return magpieBridgeAddress;
    }

    public void setMagpieBridgeAddress(String magpieBridgeAddress) {
        this.magpieBridgeAddress = magpieBridgeAddress;
    }

    public long getMagpieBridgeId() {
        return magpieBridgeId;
    }

    public void setMagpieBridgeId(long magpieBridgeId) {
        this.magpieBridgeId = magpieBridgeId;
    }

    public long getMagpieBridgeCreateTimestamp() {
        return magpieBridgeCreateTimestamp;
    }

    public void setMagpieBridgeCreateTimestamp(long magpieBridgeCreateTimestamp) {
        this.magpieBridgeCreateTimestamp = magpieBridgeCreateTimestamp;
    }

    public String getMagpieBridgeClusterName() {
        return magpieBridgeClusterName;
    }

    public void setMagpieBridgeClusterName(String magpieBridgeClusterName) {
        this.magpieBridgeClusterName = magpieBridgeClusterName;
    }

    public MagpieBridgeRole getMagpieBridgeRole() {
        return magpieBridgeRole;
    }

    public void setMagpieBridgeRole(MagpieBridgeRole magpieBridgeRole) {
        this.magpieBridgeRole = magpieBridgeRole;
    }

    public long getClientNums() {
        return clientNums;
    }

    public void setClientNums(long clientNums) {
        this.clientNums = clientNums;
    }

    public Set<ClientMetadata> getClientMetadataSet() {
        return clientMetadataSet;
    }

    public void setClientMetadataSet(Set<ClientMetadata> clientMetadataSet) {
        this.clientMetadataSet = clientMetadataSet;
    }
}
