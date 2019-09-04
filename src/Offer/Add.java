package Offer;

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号
 * 1.两个数异或：相当于每一位相加，而不考虑进位；
 * 2.两个数相与，并左移一位：相当于求得进位；
 * 3.将上述两步的结果相加
 */
public class Add {
    public static void main(String[] args) {
        System.out.println(new Add().Add(23, -78));
    }

    public int Add(int num1, int num2) {
        while (num2 != 0) {
            int a = num1 ^ num2;
            int b = (num1 & num2) << 1;
            num1 = a;
            num2 = b;
        }
        return num1;
    }
}
