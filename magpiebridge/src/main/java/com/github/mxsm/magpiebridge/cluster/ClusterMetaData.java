package com.github.mxsm.magpiebridge.cluster;

/**
 * @author mxsm
 * @Date 2021/7/21
 * @Since 1.0.0
 */
public class ClusterMetaData {

    private String masterAddress;


    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    @Override
    public String toString() {
        return "ClusterMetaData{" +
            "masterAddress='" + masterAddress + '\'' +
            '}';
    }
}
