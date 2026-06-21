package LeetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 */
public class P84_柱状图中最大的矩形_单调栈 {
    public static void main(String[] args) {
        int[] heights = new int[]{2, 1, 5, 6, 2, 3};
        System.out.println(largestRectangleArea(heights));
    }

    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int size = heights.length;
        int max = Integer.MIN_VALUE;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, (i - k - 1) * heights[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, (size - k - 1) * heights[j]);
        }
        return max;
    }
}
