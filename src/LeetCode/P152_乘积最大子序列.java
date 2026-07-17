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

    /**
     * 数组 DP：max[i] 和 min[i] 分别表示必须以 i 结尾的子数组最大、最小乘积。
     * 由于负数会使大小关系翻转，两个状态都必须保留；每轮可选重新从 nums[i]
     * 开始，或将它接到上一位置的最大/最小乘积后。
     * 时间复杂度：O(N)；额外空间：O(N)。
     */
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

    /**
     * 将上述 DP 压缩为 max、min 两个变量。遇到负数时先交换两者，对应负数使
     * 最大乘积和最小乘积的角色互换；然后判断延长原子数组还是从当前数重新开始。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
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
