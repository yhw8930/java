
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        for(int i= 0;i<100;i++) {
            new Thread() {
                public void run() {
                    for(int j=0;j<100;j++) {
                        count.getAndIncrement();
                    }
                }
            }.start();
        }
        Thread.sleep(1000);
        System.out.println("AtomicInteger count: " + count);
    }

}
