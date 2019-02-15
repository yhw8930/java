package net;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.Selector;

public class NonBlockingServer {
    public static void main(String[] args) throws IOException {
        InetAddress localhost = InetAddress.getByName("localhost");
        int port = 9000;
        Selector open = Selector.open();

    }
}
