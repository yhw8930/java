package DP;

/**
 * m*n阶矩阵从左上角到右下角的所有路径数
 * dp[i][j] = dp[i-1][j]+dp[i][j-1]
 */
public class dp_路径数目 {
    public static void main(String[] args) {
        System.out.println(new dp_路径数目().UnionPath(3, 7, 2, 3));
    }

    public int UnionPath(int m, int n, int a, int b) {
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i != 0 || j != 0) {
                    if (i == a && j == b) {
                        dp[i][j] = 0;
                    } else {
                        if (i == 0) {
                            dp[i][j] = dp[i][j - 1];
                        } else if (j == 0) {
                            dp[i][j] = dp[i - 1][j];
                        } else {
                            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                        }
                    }
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
