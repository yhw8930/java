package DesignPattern.SingletonPattern;

public class MyThread extends Thread{
    @Override
    public void run(){
        //System.out.println(MyObject.getInstance().hashCode());
        System.out.println(Singleton.getSingleton().hashCode());
    }
}
