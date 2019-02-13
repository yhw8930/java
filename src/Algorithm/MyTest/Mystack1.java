package Algorithm.MyTest;

import java.util.Stack;

/**
 * O(1)时间复杂度求栈中最小元素
 */
public class Mystack1 {
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;
    public Mystack1(){
        this.stackData=new Stack<>();
        this.stackMin=new Stack<>();
    }
    public void push(int data){
        if (stackMin.isEmpty()){
            stackMin.push(data);
        }else if (data<stackMin.peek()){
            stackMin.push(data);
        }
        stackData.push(data);
    }
    public int pop(){
        int topData=stackData.peek();
        stackData.pop();
        if (topData==this.getMin()){
            stackMin.pop();
        }
        return topData;
    }
    public int getMin(){
        if (stackMin.isEmpty()){
            return Integer.MAX_VALUE;
        }else {
            return stackMin.peek();
        }
    }

    public static void main(String[] args) {
        Mystack1 mystack1=new Mystack1();
        mystack1.push(43);
        mystack1.push(4);
        mystack1.push(28);
        mystack1.push(34);
        mystack1.pop();
        mystack1.pop();
        System.out.println(mystack1.pop());
        System.out.println("最小值："+mystack1.getMin());
    }
}
