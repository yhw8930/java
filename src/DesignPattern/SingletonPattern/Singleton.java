package DesignPattern.SingletonPattern;

/**
 * 延迟加载==懒汉模式
 * DCL双检查锁机制
 */
public class Singleton  {
    private volatile static Singleton singleton;
    public static Singleton getSingleton(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton==null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
