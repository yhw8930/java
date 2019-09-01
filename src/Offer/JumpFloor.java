package Offer;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 * <p>
 * 首先可知，第一阶有只能一步，一种；，第二阶可以两次一步、一次两步两种
 * <p>
 * 若楼梯阶级 n = 3
 * 跳 2 步到 3：剩下的是第一步没跳，起始跳到第一步只有一种
 * 跳 1 步到 3：剩下的是第二步没跳，起始跳到第二步有两种
 * 通过分类讨论，问题规模就减少了:
 * <p>
 * 若楼梯阶级 n = n
 * 跳 2 步到 n：剩下的是第 n - 2 步没跳，起始跳到第 n - 2 步设它为 pre2 种
 * 跳 1 步到 n：剩下的是第 n - 1 步没跳，起始跳到第 n - 1 步设它为 pre1 种
 * 同时可以发现第 n 阶的解法，只要用到 n - 1 和 n - 2 阶是多少，其他的不用考虑，因此用两个变量临时存下来即可
 * <p>
 * dp(i) = dp(i-2) + dp(i-1)
 */
public class JumpFloor {
    public static void main(String[] args) {
        System.out.println(new JumpFloor().JumpFloor(10));
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
