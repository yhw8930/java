package Algorithm.MyTest;

import java.util.Arrays;

public class MyStack<E> {
    private Object[] stack;
    private int size;//数组中存储元素的个数

    public MyStack() {
        stack = new Object[10];//初始长度为10
    }

    //判断堆栈是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //获取元素
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) stack[size - 1];
    }

    //出栈
    public E pop() {
        E e = peek();
        stack[size - 1] = null;
        size--;
        return e;
    }

    //入栈
    public E push(E item) {
        ensureCapacity(size + 1);
        stack[size++] = item;
        return item;
    }

    //检查容量
    private void ensureCapacity(int size) {
        int len = stack.length;
        if (size > len) {
            int newLen = 10;
            stack = Arrays.copyOf(stack, newLen+len);
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.push(5);
        myStack.push(6);
        myStack.push(7);
        myStack.push(8);
        myStack.push(9);
       myStack.push(10);
       myStack.push(11);
        myStack.push(12);
        System.out.println("个数：" + myStack.size);
        System.out.println("栈顶元素：" + myStack.pop());
        System.out.println("栈顶元素：" + myStack.pop());
    }
}
