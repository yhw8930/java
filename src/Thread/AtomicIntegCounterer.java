package Thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类实现线程安全的计数器
 */
public class AtomicIntegCounterer {
    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        for(int i= 0;i<100;i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        count.getAndIncrement();
                    }
                }
            }.start();
        }
        Thread.sleep(1000);
        System.out.println("AtomicInteger count: " + count);
    }
}
