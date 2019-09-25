package Offer;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class P9_变态跳台阶 {
    public static void main(String[] args) {

    }

    public int JumpFloorII(int target) {
        if (target <= 2) return target;
        int cur = 0, f = 2;
        for (int i = 3; i <= target; i++) {
            cur = 2 * f;
            f = cur;
        }
        return cur;
    }
}
