package Offer;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 */
public class P8_跳台阶 {
    public static void main(String[] args) {

    }

    public int JumpFloor(int target) {
        if (target <= 2) return target;
        int cur = 0, f1 = 1, f2 = 2;
        for (int i = 3; i <= target; i++) {
            cur = f1 + f2;
            f1 = f2;
            f2 = cur;
        }
        return cur;
    }
}
