package LeetCode;

import java.util.Arrays;

/**
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 * <p>
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 */
public class P300_最长上升子序列 {
    public static void main(String[] args) {
        int[] ints = {10, 9, 2, 5, 3, 7, 101, 18};
        //int[] ints = {-2,-1};
        System.out.println(new P300_最长上升子序列().lengthOfLIS(ints));
    }

    /**
     * dp[i] 表示必须以 nums[i] 结尾的最长严格上升子序列长度，初始为 1。
     * 枚举所有 j<i，若 nums[j]<nums[i]，就可将 i 接在以 j 结尾的序列后，用 dp[j]+1 更新。
     * 时间复杂度：O(N^2)；额外空间：O(N)。
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
