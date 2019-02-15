package net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket Socket = new Socket("localhost",9000);
        OutputStream outputStream = Socket.getOutputStream();
        outputStream.write("hello java".getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
