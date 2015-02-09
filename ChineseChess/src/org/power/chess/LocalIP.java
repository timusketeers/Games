package org.power.chess;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * @author zhengjianbing 获取本机IP，非127.0.0.1
 */
public class LocalIP
{

    public static String getIp()
    {
        String ip = null;

        Collection<InetAddress> colInetAddress = getAllHostAddress();

        for (InetAddress address : colInetAddress)
        {
            if (!address.isLoopbackAddress())
            {
                ip = address.getHostAddress();
                if (ip != null)
                {
                    break;
                }
            }
        }
        System.out.println("返回IP：" + ip);
        return ip;
    }

    public static Collection<InetAddress> getAllHostAddress()
    {
        try
        {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();

            while (networkInterfaces.hasMoreElements())
            {
                NetworkInterface networkInterface = networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface
                        .getInetAddresses();
                while (inetAddresses.hasMoreElements())
                {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    addresses.add(inetAddress);
                }
            }

            return addresses;
        }
        catch (SocketException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}