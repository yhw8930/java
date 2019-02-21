package NIO;

import java.nio.ByteBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        byte[] bytes = new byte[10];
        ByteBuffer allocate = ByteBuffer.allocate(10);
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{1, 2, 3});
        //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        int limit = allocate.limit();
        for (int i = 0; i < limit; i++) {
            allocate.put(i, (byte) i);
        }
        System.out.println(limit);
        for (int i = 0; i < limit; i++) {
            System.out.print(allocate.position() + ":");
            System.out.println(allocate.get(i));
        }
    }
}
