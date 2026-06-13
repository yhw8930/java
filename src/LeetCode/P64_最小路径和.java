package LeetCode;

import java.util.Arrays;

/**
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 * <p>
 * 时间复杂度：O(mn)；空间复杂度：O(mn)
 */
public class P64_最小路径和 {
    public static void main(String[] args) {

    }

    public int minPathSum1(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m > n) {
            int[] dp = new int[n];
            dp[0] = grid[0][0];
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j - 1] + grid[0][j];
            }
            for (int i = 1; i < m; i++) {
                dp[0] += grid[i][0];
                for (int j = 1; j < n; j++) {
                    dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
                }
            }
            return dp[n - 1];
        }
        int[] dp = new int[m];
        dp[0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i] = dp[i - 1] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0] += grid[0][j];

            for (int i = 1; i < m; i++) {
                dp[i] = Math.min(dp[i], dp[i - 1]) + grid[i][j];
            }
        }
        return dp[m - 1];
    }


    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }
}
