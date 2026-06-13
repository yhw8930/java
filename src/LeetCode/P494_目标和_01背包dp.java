package LeetCode;

/**
 * 给你一个非负整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * <p>
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 *
 *
 */
public class P494_目标和_01背包dp {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 1, 1};
        System.out.println(findTargetSumWays(nums, 3));
    }

    // P = 所有加号数字之和, N = 所有减号数字之和, 则 P-N = target；
    // 把P= target+N 代入P + N = sum，得到 target + N + N = sum，即 2N = sum - target， N = (sum - target) / 2
    // N = (sum - target)/2 问题转换为从nums中选若干个数组成N的方法数

    public static int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        int m = nums.length;
        int n = (sum - target) / 2;
        if (sum - target < 0 || ((sum - target) & 1) != 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        dp[m][0] = 1;
        for (int index = m - 1; index >= 0; index--) {
            for (int rest = 0; rest <= n; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - nums[index] >= 0) {
                    dp[index][rest] += dp[index + 1][rest - nums[index]];
                }
            }
        }
        return dp[0][n];
    }

    public static int findTargetSumWays2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int diff = sum - target;
        if (diff < 0 || (diff & 1) != 0) {
            return 0;
        }
        int bag = diff / 2;
        int[] dp = new int[bag + 1];
        // 什么都不选组成0
        dp[0] = 1;
        for (int num : nums) {
            // 0/1背包必须倒序
            for (int rest = bag; rest >= num; rest--) {
                dp[rest] += dp[rest - num];
            }
        }
        return dp[bag];
    }

    public static int findTargetSumWays1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return process(nums, 0, target);
    }

    public static int process(int[] nums, int index, int target) {
        if (nums.length == index) {
            return target == 0 ? 1 : 0;
        }
        return process(nums, index + 1, target + nums[index]) + process(nums, index + 1, target - nums[index]);
    }


}
