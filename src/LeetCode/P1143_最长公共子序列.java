package LeetCode;

//给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
//
//一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
//
//例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
//两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。

//输入：text1 = "abcde", text2 = "ace"
//输出：3
//解释：最长公共子序列是 "ace" ，它的长度为 3

public class P1143_最长公共子序列 {
    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "ace";
        System.out.println(longestCommonSubsequence1(s1, s2));
        System.out.println(longestCommonSubsequence(s1, s2));
    }
    // longestCommonSubsequence 返回 s1 和 s2 的最长公共子序列长度，动态规划一维版本
    // dp[i][j - 1]      // 左边
    // dp[i - 1][j]      // 上边
    // dp[i - 1][j - 1]  // 左上角
    // 时间：O(N × M)
    // 空间：O(min(N, M))
    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.isEmpty() || text2.isEmpty()) {
            return 0;
        }
        // 让短字符串做列，省空间
        if (text1.length() < text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[] dp = new int[M];
        //up     = 老的 dp[j]     = 二维表的上方
        //left   = 新的 dp[j-1]   = 二维表的左方
        //leftUp = 老的 dp[j-1]   = 二维表的左上角
        for (int i = 0; i < N; i++) {
            int leftUp = 0; // 代表左上角dp[i-1][j-1]
            for (int j = 0; j < M; j++) {
                int up = dp[j]; // 更新前的 dp[j]，代表二维表里的 dp[i-1][j]
                if (str1[i] == str2[j]) {
                    dp[j] = leftUp + 1;
                } else {
                    int left = j > 0 ? dp[j - 1] : 0;
                    dp[j] = Math.max(up, left);
                }
                leftUp = up;
            }
        }
        return dp[M - 1];
    }


    // longestCommonSubsequence1 返回 s1 和 s2 的最长公共子序列长度，是递归尝试的动态规划版本。
    // dp[i][j] 表示 str1[0..i] 和 str2[0..j] 的最长公共子序列长度，按依赖从左上到右下填表。
    // 时间复杂度：O(N*M)。
    // 空间复杂度：O(N*M)。
    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i - 1][j];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];
    }

    // LongestCommonSubsequence1 返回 s1 和 s2 的最长公共子序列长度。
    // 递归含义：只考虑 str1[0..i] 和 str2[0..j]，答案来自跳过 str1[i]、跳过 str2[j]、
    // 或在两字符相等时同时保留它们这三种可能。
    //
    // 时间复杂度：O(3^(N+M))，存在大量重复子问题。
    // 空间复杂度：O(N+M)，递归调用栈深度。
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process(str1, str2, str1.length - 1, str2.length - 1);
    }

    public static int process(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            }
            return 0;
        }
        if (i == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            }
            return process(str1, str2, i, j - 1);
        }
        if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            }
            return process(str1, str2, i - 1, j);
        }
        int p1 = process(str1, str2, i, j - 1);
        int p2 = process(str1, str2, i - 1, j);
        int p3 = str1[i] == str2[j] ? (1 + process(str1, str2, i - 1, j - 1)) : 0;
        return Math.max(p1, Math.max(p2, p3));
    }
}