package LeetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给你一个 m x n 的二进制矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
 *给你一个 m x n 的二进制矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
 * 输入：mat = [[1,0,1],[1,1,0],[1,1,0]]
 * 输出：13
 * 解释：
 * 有 6 个 1x1 的矩形。
 * 有 2 个 1x2 的矩形。
 * 有 3 个 2x1 的矩形。
 * 有 1 个 2x2 的矩形。
 * 有 1 个 3x1 的矩形。
 * 矩形数目总共 = 6 + 2 + 3 + 1 + 1 = 13 。
 *
 */
public class P1504_统计全一子矩形_单调栈 {
    public static void main(String[] args) {
        int[][] mat = new int[][]{
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 0}
        };
        System.out.println(numSubmat(mat));
    }

    /**
     * 逐行将矩阵压缩为柱状图：height[j] 表示以当前行为底、第 j 列连续 1 的高度。
     * 对每个柱状图，countFromBottom 用单调栈统计以当前行为底的全 1 子矩形数。
     * 弹栈时，柱子高度从 down+1 到 height[cur] 的每一层都新贡献 n(n+1)/2 个连续区间。
     * 时间复杂度：O(MN)；额外空间：O(N)。
     */
    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        int[] height = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;

    }

    public static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int cur = stack.pop();
                if (height[cur] > height[i]) {
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            int n = height.length - left - 1;
            int down = left == -1 ? 0 : height[left];
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    public static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }
}
