package Offer;

/**
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
 * n<=39
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(new Fibonacci().Fibonacci(10));
    }

    public int Fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int f0 = 0, f1 = 1, fn = 1;
        for (int i = 2; i <= n; i++) {
            fn = f0 + f1;
            f0 = f1;
            f1 = fn;
        }
        return fn;
    }
    /*public int Fibonacci(int n){
        if (n==0||n==1){
            return n;
        }
        return Fibonacci(n-1)+Fibonacci(n-2);
    }*/
}
