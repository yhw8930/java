package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道流生产者消费者模型
 */
public class PipedStreamDemo{
    public static void main(String[] args) throws IOException {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        pipedInputStream.connect(pipedOutputStream);
        new Thread(()->produce(pipedOutputStream)).start();
        new Thread(()->consume(pipedInputStream)).start();
    }
    private static void produce(PipedOutputStream pipedOutputStream){
        try {
            for (int i = 0; i < 50; i++) {
                pipedOutputStream.write(i);
                pipedOutputStream.flush();
                System.out.println("写入: " + i);
                Thread.sleep(500);
            }
            pipedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void consume(PipedInputStream pipedInputStream){
        try {
            int num = -1;
            while ((num=pipedInputStream.read())!=-1){
                System.out.println("读取: " + num);
            }
            pipedInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
