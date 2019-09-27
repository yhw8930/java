package LeetCode;

/**
 * 9. 回文数
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数
 * 时间复杂度 ：O(log10n)空间复杂度：O(1)
 */
public class P9_回文数 {

    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int temp = 0;
        while (x > temp) {
            temp = 10 * temp + x % 10;
            x /= 10;
        }
        return temp == x || temp / 10 == x;
    }

    public static void main(String[] args) {
        boolean b = new P9_回文数().isPalindrome(123);
        System.out.println(b);
    }
}
