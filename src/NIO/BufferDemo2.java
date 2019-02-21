package NIO;

import java.nio.ByteBuffer;

public class BufferDemo2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //buffer = buffer.asReadOnlyBuffer(); //设为只读
        //buffer.duplicate(); //复制
        //buffer.slice(); //从后分割
        for (int i = 1; i <= buffer.limit(); i++) {
            buffer.put((byte) i);
        }
        buffer.position(0); //拨回为0
        System.out.println(buffer.position());
        buffer.limit(5);
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.print(buffer.get());
            if (i==2){
                buffer.mark();
            }
        }
        System.out.println(buffer.position());
        buffer.reset();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.flip());
    }
}
