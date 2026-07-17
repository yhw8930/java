package LeetCode;

/**
 * 给定一个整数数组Nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 时间复杂度为N^2
 */
public class P53_最大子序和 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new P53_最大子序和().maxSubArray(nums));
    }

    /**
     * Kadane 算法：sum 保存以当前位置结尾且值为正时对后续有帮助的前缀和。
     * 每次先用 sum 更新全局答案，再在 sum<0 时丢弃它，因此全负数组也能正确返回最大的单个元素。
     * 实际时间复杂度为 O(N)（类注释中的 O(N²) 不准确），额外空间 O(1)。
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > maxSum) {
                maxSum = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }
}
