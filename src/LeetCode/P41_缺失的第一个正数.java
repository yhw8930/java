package LeetCode;

/**
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * <p>
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 输入：nums = [1,2,0]
 * 输出：3
 * 解释：范围 [1,2] 中的数字都在数组中。
 *
 */
public class P41_缺失的第一个正数 {
    /**
     * 长度为 N 的数组的答案必在 [1,N+1]。先用原地交换将每个范围内的值 x 放到下标 x-1；
     * 交换条件中排除相同值，避免重复数导致死循环。最后首个 nums[i]!=i+1 的位置即缺少 i+1；
     * 若 1..N 都归位，答案是 N+1。时间 O(N)，额外空间 O(1)。
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // 把 nums[i] 放到它应该出现的位置 nums[i] - 1
        for (int i = 0; i < n; i++) {
            // 因为长度为 n 的数组，答案一定在 [1, n + 1] 中，所以小于 1 或大于 n 的数字可以忽略;同时需要忽略交换index相同数字
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int index = nums[i] - 1;
                int temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
            }
        }
        // 第一个位置不正确的下标 i，说明缺少数字 i + 1
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 1～n 全部存在，最小缺失正整数就是 n + 1
        return n + 1;
    }

}
