package LeetCode;

/**
 * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数
 * 当前最大可能是前一个最大乘以当前，前一个元素最小乘以当前，也可能是当
 */
public class P152_乘积最大子序列 {
    public static void main(String[] args) {
        int[] ints = {2, 3, -2, 4};
        System.out.println(new P152_乘积最大子序列().maxProduct1(ints));
    }

    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = min[0] = nums[0];
        int target = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max[i] = Math.max(nums[i], Math.max(max[i - 1] * nums[i], min[i - 1] * nums[i]));
            min[i] = Math.min(nums[i], Math.min(max[i - 1] * nums[i], min[i - 1] * nums[i]));
            target = Math.max(target, max[i]);
        }
        return target;
    }

    public int maxProduct1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = nums[0], min = nums[0], target = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                int temp = max;
                max = min;
                min = temp;
            }
            max = Math.max(nums[i], max * nums[i]);
            min = Math.min(nums[i], min * nums[i]);
            target = Math.max(target, max);
        }
        return target;
    }
}
