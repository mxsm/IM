package com.github.mxsm.remoting.common;

import com.github.mxsm.common.Symbol;
import io.netty.channel.Channel;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since 1.0.0
 */
public abstract class NetUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetUtils.class);

    public static SocketAddress ip2SocketAddress(final String addr) {
        int split = addr.lastIndexOf(":");
        String host = addr.substring(0, split);
        String port = addr.substring(split + 1);
        InetSocketAddress isa = new InetSocketAddress(host, Integer.parseInt(port));
        return isa;
    }

    public static String parseChannelRemoteAddress(final Channel channel) {
        if (null == channel) {
            return "";
        }
        SocketAddress remote = channel.remoteAddress();
        final String addr = remote != null ? remote.toString() : "";

        if (addr.length() > 0) {
            int index = addr.lastIndexOf("/");
            if (index >= 0) {
                return addr.substring(index + 1);
            }

            return addr;
        }
        return "";
    }

    public static String getLocalAddress() {
        try {
            // Traversal Network interface to get the first non-loopback and non-private address
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4Result = new ArrayList<>();
            ArrayList<String> ipv6Result = new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = enumeration.nextElement();
                final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(normalizeHostAddress(address));
                        } else {
                            ipv4Result.add(normalizeHostAddress(address));
                        }
                    }
                }
            }

            // prefer ipv4
            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0") ) {
                        continue;
                    }

                    return ip;
                }

                return ipv4Result.get(ipv4Result.size() - 1);
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            }
            //If failed to find,fall back to localhost
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (Exception e) {
            LOGGER.error("Failed to obtain local address", e);
        }
        return null;
    }

    public static int getLocalAddress4Int() {
        int[] ip = new int[4];
        String[] ipSec = Optional.ofNullable(getLocalAddress()).orElse("0.0.0.0").split(Symbol.DOT);
        for (int k = 0; k < 4; k++) {
            ip[k] = Integer.valueOf(ipSec[k]);
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static int address4Int(String address) {
        int[] ip = new int[4];
        String[] ipSec = Optional.ofNullable(address).orElse("0.0.0.0").split(Symbol.DOT);
        for (int k = 0; k < 4; k++) {
            ip[k] = Integer.valueOf(ipSec[k]);
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

    public static String parseSocketAddress2Address(SocketAddress socketAddress) {
        if (socketAddress != null) {
            final String address = socketAddress.toString();

            if (address.length() > 0) {
                return address.substring(1);
            }
        }
        return "";
    }

    public static String intIpAddress2String(int ip) {
        return String.format("%d.%d.%d.%d", (ip >> 24 & 0xff), (ip >> 16 & 0xff), (ip >> 8 & 0xff), (ip & 0xff));
    }

    public static Set<String> getIpAddressByName(String domain){
        try {
            InetAddress[] allByName = InetAddress.getAllByName(domain);
            return Arrays.stream(allByName).map(item->normalizeHostAddress(item)).collect(Collectors.toSet());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            LOGGER.error("get domain ips error",e);
        }
        return new HashSet<>();
    }

}
