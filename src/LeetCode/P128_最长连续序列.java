package LeetCode;

import java.util.HashSet;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 *
 */
public class P128_最长连续序列 {
    /**
     * 解法：先把所有数字放入 HashSet，既去除重复值，又能在平均 O(1) 时间内判断某个数是否存在。
     * 遍历集合时，只有当 num - 1 不存在时，才把 num 当作一段连续序列的起点，
     * 然后不断查找 num + 1、num + 2 等后继数字，计算该序列长度。
     * 跳过所有存在前驱的数字是线性复杂度的关键：每个数最多只会在自己所属序列从起点扩展时被访问一次。
     * 该实现假设 nums 不为 null，且 num - 1 和 curNum + 1 不会超出 int 范围。
     * 若允许 Integer.MIN_VALUE 和 Integer.MAX_VALUE，Java 整数溢出可能把两者误判为连续；
     * 例如输入 [Integer.MIN_VALUE, Integer.MAX_VALUE] 时当前代码会返回 2，而正确答案是 1。
     *
     * 时间复杂度：平均 O(N)。
     * 额外空间复杂度：O(N)，用于保存去重后的数字集合。
     */
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int maxLength = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int curNum = num;
                int curLen = 1;
                while (set.contains(curNum + 1)) {
                    curNum++;
                    curLen++;
                }
                maxLength = Math.max(maxLength, curLen);
            }
        }
        return maxLength;
    }
}
