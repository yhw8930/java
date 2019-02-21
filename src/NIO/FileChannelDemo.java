package NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        read();
        write();
    }

    private static void write() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("Users/didi/javasource/java/src/NIO/text");
        FileChannel channel = fileOutputStream.getChannel();
        String text = "abcdefg";
        ByteBuffer byteBuffer = ByteBuffer.wrap(text.getBytes());
        channel.write(byteBuffer);
    }

    private static void read() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/didi/javasource/java/src/NIO/text");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (channel.read(buffer)>0){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.print((char)buffer.get());
            }
            buffer.clear();
        }
    }
}
