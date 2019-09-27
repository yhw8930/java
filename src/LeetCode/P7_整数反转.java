package LeetCode;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 */
public class P7_整数反转 {
    public int reverse(int x) {
        int rev=0;
        while(x!=0) {
            int pop=x%10;
            x/=10;
            if (rev > Integer.MAX_VALUE/10 )
                return 0;

            if (rev < Integer.MIN_VALUE/10)
                return 0;
            rev=rev*10+pop;
        }
        return rev;
    }
}
