package LeetCode;

import java.util.Stack;

/**
 * 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
 * 比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
 * <p>
 * 给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。
 * 请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数 保存。
 * <p>
 * 子数组 定义为一个数组的 连续 部分。
 * <p>
 * 输入：nums = [1,2,3,2]
 * 输出：14
 * 解释：最小乘积的最大值由子数组 [2,3,2] （最小值是 2）得到。
 * 2 * (2+3+2) = 2 * 7 = 14 。
 *
 * 时间复杂度：O(n)，其中 n 是数组 nums 的长度。计算数组 left 和 right、前缀和以及答案都需要 O(n) 的时间。
 * 空间复杂度：O(n)，即为单调栈和前缀和数组需要使用的空间。
 */
public class P1856_子数组最小乘积的最大值_单调栈 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 2};
        System.out.println(maxSumMinProduct(nums));
    }

    public static int maxSumMinProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int size = nums.length;
        long[] sums = new long[size];
        sums[0] = nums[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }
        long max = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                int j = stack.pop();
                long sum = stack.isEmpty() ? sums[i - 1] : sums[i - 1] - sums[stack.peek()];
                max = Math.max(max, sum * nums[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            long sum = stack.isEmpty() ? sums[size - 1] : sums[size - 1] - sums[stack.peek()];
            max = Math.max(max, sum * nums[j]);
        }
        return (int) (max % 1000000007);
    }
}
