package LeetCode;

/**
 * 11. 盛最多水的容器
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点
 * 分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 暴力法1：时间复杂度O(n^2)，空间复杂度O(1)
 * 双指针法2：两个指针往中间移动，其中每次让较小的往中间移
 * 时间复杂度：O(n) 空间复杂度：O(1)
 */
public class P11_盛最多水的容器 {
    public static void main(String[] args) {
        P11_盛最多水的容器 maxArea = new P11_盛最多水的容器();
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        //System.out.println(maxArea.maxArea1(arr));
        System.out.println(maxArea.maxArea2(arr));
    }

    /**
     * 解法一：暴力枚举所有两条垂直线的组合。
     * 对于下标 i 和 j，容器宽度为 j - i，水面高度取决于较短的那条线，
     * 因此面积为 (j - i) * min(height[i], height[j])。
     * 遍历每一对 i < j 并取最大值，不会遗漏任何可能的容器。
     *
     * 时间复杂度：O(N^2)。
     * 额外空间复杂度：O(1)。
     */
    public int maxArea1(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            }
        }
        return max;
    }

    /**
     * 解法二：双指针从数组两端向中间收缩。
     * 每次先用当前左右边界计算面积，然后移动高度较小的那个指针。
     * 正确性关键是：当 height[i] < height[j] 时，若保留 i 而移动 j，宽度一定变小，
     * 而新容器的高度仍不可能超过 height[i]，所以面积不可能更大。
     * 只有舍弃较短边并向内寻找更高的边，才可能弥补宽度减小的损失。
     * 两边等高时移动任意一边都安全，本实现选择移动右指针。
     *
     * 时间复杂度：O(N)，每个指针最多移动 N - 1 次。
     * 额外空间复杂度：O(1)。
     */
    public int maxArea2(int[] height) {
        int maxArea = 0, i = 0, j = height.length - 1;
        while (i < j) {
            maxArea = Math.max(maxArea, Math.min(height[i], height[j]) * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return maxArea;
    }
}
