package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Administrator on 2016-12-28.
 */
public class NetUtils {
    protected static final Logger log = LoggerFactory.getLogger("NetUtils");
    /**
     * 判断ip地址是否是本机地址
     * @param ipV4 ip地址
     * @return
     */
    public static boolean isLocal(String ipV4) {
        if ("127.0.0.1".equals(ipV4)) {
            return true;
        }
        boolean access = false;
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces
                    .hasMoreElements(); ) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && ip.getHostAddress().indexOf(".") != -1) {
                        String machineIp = ip.getHostAddress();
                        if (ipV4.equals(machineIp)) {
                            access = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("checkMachineIPAccessForTask failed, caused by: " + e.getMessage());
            access = false;
        }
        return access;
    }
    
    /**
     * 判断机器的IP地址是否在白名单中
     * 如果白名单不设置值，表示都不通过
     * @return
     */
    public static boolean checkMachineIPAccessForTask(String ips) {
        boolean access = false;
        try {
            List<String> ipWhiteList = Arrays.asList(ips.split(","));
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces
                    .hasMoreElements();) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && ip.getHostAddress().indexOf(".") != -1) {
                        String machineIp = ip.getHostAddress();
                        if (ipWhiteList.contains(machineIp)) {
                            access = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("checkMachineIPAccessForTask failed, caused by: " + e.getMessage());
            access = false;
        }
        return access;
    }
}
