package com.github.mxsm.common;

import java.util.Objects;

/**
 * @author mxsm
 * @date 2021/12/18 23:08
 * @Since 1.0.0
 */
public class Address {

    private String ip;

    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return port == address.port && Objects.equals(ip, address.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(ip).append(":").append(port).toString();
    }
}
