package Thread.sellTickets;
//模拟火车票联网售票系统,要求多个线程同时出票，保证每张出票的编号连续且不重复
//要求创建3个线程，名字为线程1、线程2、线程3,输出当前线程+买票+id
public class SaleDemo {
    public static void main(String[] args) throws InterruptedException {
        SellTicket sellTicket1=new SellTicket();
        sellTicket1.setName("线程1");
        SellTicket sellTicket2=new SellTicket();
        sellTicket2.setName("线程2");
        SellTicket sellTicket3=new SellTicket();
        sellTicket3.setName("线程3");
        sellTicket1.start();
        sellTicket2.start();
        sellTicket3.start();
    }

}
class SellTicket extends Thread{
    private static int id=0;
    public void run(){
        for (int i=0;i<20;i++){
            sell();
        }

    }
    public synchronized void sell(){
        id++;
        System.out.println(currentThread().getName()+"买票"+"id"+id);
    }

}
