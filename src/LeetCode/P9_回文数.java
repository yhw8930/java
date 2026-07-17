package LeetCode;

/**
 * 9. 回文数
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数
 * 时间复杂度 ：O(log10n)空间复杂度：O(1)
 */
public class P9_回文数 {

    /**
     * 只反转数字的后半部分，避免整个反转可能溢出。负数不是回文，非 0 且末位为 0 的数也不可能是回文。
     * 当原数 x≤已反转部分 temp 时到达中点；偶数位比较 x==temp，奇数位通过 temp/10 忽略中间位。
     * 时间 O(log|x|)，空间 O(1)。
     */
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
