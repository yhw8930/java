package Offer;

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号
 */
public class P46_用加减乘除做加法 {
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
