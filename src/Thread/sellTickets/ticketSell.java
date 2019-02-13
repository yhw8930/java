package Thread.sellTickets;
//实现通过4个售票点发售某日某次列车的100张车票，一个售票点用一个线程表示
//要求用4个线程出售200张票，sleep(20),输出格式如下
//窗口4出售车票id199
//窗口1出售车票id198
//窗口3出售车票id197
//窗口3出售车票id196
//窗口2出售车票id195
public class ticketSell implements Runnable{
    int num=200;
    public void run(){
        while (true){
            synchronized (this){
                if (num>0){
                    System.out.println(Thread.currentThread().getName()+"出售车票id"+num--);
                }else {
                    return;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ticketSell ticketSell=new ticketSell();
        Thread thread1=new Thread(ticketSell);
        thread1.setName("窗口1");
        Thread thread2=new Thread(ticketSell);
        thread2.setName("窗口2");
        Thread thread3=new Thread(ticketSell);
        thread3.setName("窗口3");
        Thread thread4=new Thread(ticketSell);
        thread4.setName("窗口4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
