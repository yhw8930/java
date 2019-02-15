package net;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        String msg = "hello";
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length);
        datagramPacket.setAddress(InetAddress.getByName("localhost"));
        datagramPacket.setPort(9000);
        datagramSocket.send(datagramPacket);
        datagramSocket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        int length = datagramPacket.getLength();
        int offset = datagramPacket.getOffset();
        String s = new String(data, offset, length);
        System.out.println(s);
    }
}
