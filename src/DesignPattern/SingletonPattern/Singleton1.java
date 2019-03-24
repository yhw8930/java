package DesignPattern.SingletonPattern;

/**
 * 静态内部类
 */
public class Singleton1 {
    private Singleton1() {}

    private static class SingletonInstance {
        private static final Singleton1 INSTANCE = new Singleton1();
    }

    public static Singleton1 getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
