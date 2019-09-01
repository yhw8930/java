package LeetCode;

/**
 * 给定一个整数数组Nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 时间复杂度为N^2
 */
public class MaxSubArray_53 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new MaxSubArray_53().maxSubArray(nums));
    }

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
