package com.product.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.util.StringUtils;

/**
 * InetAddress.getLocalHost().getHostAddress().toString()// 10.250.75.167, get
 * IP address
 * 
 * @return
 */
public class HostUtils {

    private static String hostName;

    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }

    public static String getHostName() {
        if (StringUtils.isEmpty(hostName)) {
            if (System.getenv("COMPUTERNAME") != null) {
                hostName = System.getenv("COMPUTERNAME");
            } else {
                hostName = getHostNameForLiunx();
            }
        }
        return hostName;
    }

}