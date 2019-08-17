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
public class MaxArea_11 {
    public static void main(String[] args) {
        MaxArea_11 maxArea = new MaxArea_11();
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        //System.out.println(maxArea.maxArea1(arr));
        System.out.println(maxArea.maxArea2(arr));
    }

    public int maxArea1(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            }
        }
        return max;
    }

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
