package DesignPattern.SingletonPattern;

/**
 * 立即加载==饿汉模式
 * 立即加载就是使用类的时候已经将对象实例化
 */
public class MyObject {
    private static MyObject myObject = new MyObject();
    private MyObject(){}
    public static MyObject getInstance(){
        //不能有其它实例变量，因为该方法没有同步，可能出现非线程安全问题
        return myObject;
    }
}
