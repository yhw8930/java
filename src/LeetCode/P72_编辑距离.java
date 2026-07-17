package LeetCode;

/**
 * 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 */
public class P72_编辑距离 {
    public static void main(String[] args) {
        System.out.println(new P72_编辑距离().minDistance("horse", "ros"));
    }

    /**
     * dp[i][j] 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符的最少操作数。
     * 末尾字符相同时直接沿用 dp[i-1][j-1]；不同时分别考虑删除、插入、替换三个前驱状态并加 1。
     * 时间复杂度：O(MN)；额外空间：O(MN)。
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
