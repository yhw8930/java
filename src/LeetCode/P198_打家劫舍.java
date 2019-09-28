package LeetCode;

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
 */
public class P198_打家劫舍 {
    public int rob(int[] nums) {
        int f1 = 0, f2 = 0, cur = 0;
        for (int i = 0; i < nums.length; i++) {
            cur = Math.max(f1 + nums[i], f2);
            f1 = f2;
            f2 = cur;
        }
        return cur;
    }
}
