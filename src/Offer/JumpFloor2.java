package Offer;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 * <p>
 * 易知 f(n)=f(n-1)+f(n-2)+……f(1)
 * f(n-1)=f(n-2)+……f(1)
 * 两式相减得f(n)=2f(n-1)
 */
public class JumpFloor2 {
    public static void main(String[] args) {
        System.out.println(new JumpFloor2().JumpFloorII(10));
    }

    public int JumpFloorII(int target) {
        if (target <= 2) return target;
        int cur = 0, fn = 2;
        for (int i = 3; i <= target; i++) {
            cur = 2 * fn;
            fn = cur;
        }
        return cur;
    }
}
