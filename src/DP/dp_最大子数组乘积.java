package DP;

/**
 * 求一个数组中最大子数组乘积
 * 当前最大可能是前一个最大乘以当前，前一个元素最小乘以当前，也可能是当前
 */
public class dp_最大子数组乘积 {
    public static void main(String[] args) {
        int[] ints = {2, 3, -2, 4};
        System.out.println(new dp_最大子数组乘积().getMaxNum(ints));
    }

    public int getMaxNum(int[] nums) {
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
}
