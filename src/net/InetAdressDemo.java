package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAdressDemo {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.getHostAddress());

        InetAddress[] addresses = InetAddress.getAllByName("LAPTOP-OM0RTP46");
        for (InetAddress address : addresses) {
            System.out.println(address.getHostAddress());
        }
    }
}
