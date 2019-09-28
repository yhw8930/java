package LeetCode;

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额
 */
public class P213_打家劫舍II {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int f1 = 0, f2 = 0, cur1 = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            cur1 = Math.max(f1 + nums[i], f2);
            f1 = f2;
            f2 = cur1;
        }
        int p1 = 0, p2 = 0, cur2 = 0;
        for (int i = 1; i < nums.length; i++) {
            cur2 = Math.max(p1 + nums[i], p2);
            p1 = p2;
            p2 = cur2;
        }
        return Math.max(cur1, cur2);
    }
}
