package Algorithm.MyTest;

import java.util.Stack;

public class MyQueue<E> {
    private Stack<E> s1 = new Stack<>();
    private Stack<E> s2 = new Stack<>();
    public synchronized void put(E e){
        s1.push(e);
    }
    public synchronized E pop(){
        if (s2.isEmpty()){
            while (!s1.isEmpty()){
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }
    public synchronized boolean empty(){
        return s1.isEmpty()&&s2.isEmpty();
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue=new MyQueue<>();
        queue.put(43);
        queue.put(32);
        queue.put(23);
        queue.put(87);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}
