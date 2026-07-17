package LeetCode;

/**
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除了 nums[i] 之外其余各元素的乘积 。
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 输入: nums = [1,2,3,4]
 * 输出: [24,12,8,6]
 *
 */
public class P238_除了自身以外数组的乘积 {
    /**
     * 第一遍让 answer[i] 保存 i 左侧所有元素乘积；第二遍从右到左，用 right
     * 累积 i 右侧乘积并乘入 answer[i]。两部分相乘恰好排除 nums[i]，也能自然处理 0。
     * 时间复杂度：O(N)；除返回数组外的额外空间：O(1)。
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        // 第一遍：answer[i] 保存 nums[i] 左边所有元素的乘积
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        // right 保存当前位置右边所有元素的乘积
        int right = 1;
        // 第二遍：乘上右侧所有元素的乘积
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= right;
            right *= nums[i];
        }
        return answer;
    }
}
