package DP;

import java.util.Arrays;

/**
 * 从左上角到右下角最短路径和
 */
public class dp_最小路径和 {
    public static void main(String[] args) {
        int[][] arr = {{2, 4, 3, 7}, {5, 3, 2, 1}, {4, 8, 6, 2}};
        System.out.println(new dp_最小路径和().getMinPath(arr));
    }

    public int getMinPath(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = arr[0][0];
        for (int i = 1; i < arr.length; i++) {
            dp[i][0] = arr[i][0] + dp[i - 1][0];
        }
        for (int j = 1; j < arr[0].length; j++) {
            dp[0][j] = arr[0][j] + dp[0][j - 1];
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[0].length; j++) {
                dp[i][j] = Math.min(arr[i][j] + dp[i - 1][j], arr[i][j] + dp[i][j - 1]);

            }
        }
        return dp[arr.length - 1][arr[0].length - 1];
    }
}
