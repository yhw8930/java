package LeetCode;

/**
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 子数组是数组中的一个连续部分。
 *
 */
public class P53_最大子数组和 {
    /**
     * 解法一：Kadane 动态规划，并将状态压缩为两个变量。
     * curSum 表示“必须以当前位置 i 结尾”的最大子数组和，转移时只有两种选择：
     * 若之前的 curSum 对当前和有帮助，就延续原子数组，得到 curSum + nums[i]；
     * 否则从 nums[i] 重新开始一段子数组。因此转移为 curSum = max(nums[i], curSum + nums[i])。
     * maxSum 记录截止当前位置已经出现过的全局最大和。
     * 两个状态都用 nums[0] 初始化，可以正确处理所有元素都为负数的情况；
     * 该实现假设 nums 非 null 且至少包含一个元素。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(1)。
     */
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int curSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            curSum = Math.max(nums[i], curSum + nums[i]);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }

    /**
     * 解法二：分治。将区间 [left, right] 从 mid 处分成左右两半，
     * 该区间的最大子数组和只有三种来源：完全位于左半区、完全位于右半区，或者横跨中点。
     * 前两种情况递归求解；横跨中点的情况由 maxCrossingSum 计算，
     * 它向左寻找以 mid 结尾的最大和，再向右寻找以 mid + 1 开始的最大和，最后将两者相加。
     * 对三种情况取最大值，就得到当前区间的答案。单元素区间是递归基础，答案就是该元素。
     * 空数组返回 0；该实现假设 nums 不为 null。
     *
     * 时间复杂度：O(N log N)，每层递归合计扫描 O(N)，共有 O(log N) 层。
     * 额外空间复杂度：O(log N)，来自递归调用栈。
     */
    public int maxSubArray2(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        return maxSubArraySum(nums, 0, len - 1);
    }

    private int maxCrossingSum(int[] nums, int left, int mid, int right) {
        // 一定会包含 nums[mid] 这个元素
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        // 左半边包含 nums[mid] 元素，最多可以到什么地方
        // 走到最边界，看看最值是什么
        // 计算以 mid 结尾的最大的子数组的和
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
            }
        }
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        // 右半边不包含 nums[mid] 元素，最多可以到什么地方
        // 计算以 mid+1 开始的最大的子数组的和
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
            }
        }
        return leftSum + rightSum;
    }

    private int maxSubArraySum(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        int mid = left + (right - left) / 2;
        return max3(maxSubArraySum(nums, left, mid),
                maxSubArraySum(nums, mid + 1, right),
                maxCrossingSum(nums, left, mid, right));
    }

    private int max3(int num1, int num2, int num3) {
        return Math.max(num1, Math.max(num2, num3));
    }
}
