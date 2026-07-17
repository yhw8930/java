package LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 子数组是数组中元素的连续非空序列。
 * <p>
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 *
 */
public class P560_和为k的子数组 {
    /**
     * 当前实现正确，使用“前缀和 + 哈希表”在一次遍历中统计答案。
     *
     * 设当前位置的前缀和为 sum，某个更早位置的前缀和为 oldSum，两者之间的
     * 连续子数组元素和就是 sum - oldSum。要使它等于 k，需要 oldSum = sum - k。
     * 因此，prefixCount 记录当前位置之前每种前缀和的出现次数；遍历到一个元素后，
     * sum - k 出现过几次，就新增几个以当前位置结尾的合法子数组。
     *
     * prefixCount.put(0, 1) 将“第一个元素之前的空前缀”记为一次前缀和 0，
     * 这样当 sum == k 时，从下标 0 开始的子数组也能被统计。每轮必须先查询答案，
     * 再记录当前 sum，否则 k == 0 时会把空子数组误算进去。
     *
     * 例如 nums=[1,1,1]、k=2：三次的 sum 依次为 1、2、3，查找目标依次为
     * -1、0、1；后两个目标各出现一次，所以答案为 2。
     *
     * 时间复杂度：O(N)；额外空间复杂度：O(N)。
     */
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);  // 关键初始化：前缀和为0，出现1次（表示"空前缀"）
        int sum = 0;
        int count = 0;
        for (int num : nums) {
            sum += num;
            // 如果 sum - k 之前出现过，说明存在子数组和为k
            count += prefixCount.getOrDefault(sum - k, 0);
            // 记录当前前缀和出现的次数
            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
