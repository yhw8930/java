package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 */
public class P1_两数之和 {
    /**
     * 从左到右遍历，哈希表保存已见数字到其下标的映射。处理 nums[i] 时先查找
     * target-nums[i]，再存入当前元素，保证不会重复使用同一个下标。
     * 时间复杂度：平均 O(N)；额外空间：O(N)。无解时返回 null。
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (m.containsKey(num)) {
                return new int[]{m.get(num), i};
            }
            m.put(nums[i], i);
        }
        return null;
    }
}
