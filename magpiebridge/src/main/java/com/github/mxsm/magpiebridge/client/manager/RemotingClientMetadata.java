package com.github.mxsm.magpiebridge.client.manager;

import com.github.mxsm.protocol.protobuf.constant.TerminalType;

/**
 * @author mxsm
 * @Date 2021/7/11
 * @Since 0.1
 */
public class RemotingClientMetadata {

    //IP:port
    private String remoteAddress;

    private TerminalType terminalType;

    public RemotingClientMetadata(String remoteAddress, TerminalType terminalType) {
        this.remoteAddress = remoteAddress;
        this.terminalType = terminalType;
    }

    public RemotingClientMetadata() {
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }
}
