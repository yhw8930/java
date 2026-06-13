package LeetCode;

//给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
//子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
//输入：s = "bbbab"
//输出：4
//解释：一个可能的最长回文子序列为 "bbbb" 。

public class P516_最长回文子序列 {
    public static void main(String[] args) {
        String s = "bbbab";
        System.out.println(longestPalindromeSubseq(s));
    }

    public static int longestPalindromeSubseq(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int l = N - 3; l >= 0; l--) {
            for (int r = l + 2; r < N; r++) {
                dp[l][r] = Math.max(dp[l + 1][r], dp[l][r - 1]);
                if (str[l] == str[r]) {
                    dp[l][r] = Math.max(dp[l][r], 2 + dp[l + 1][r - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    public int longestPalindromeSubseq3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            int leftDown = 0;
            for (int j = i + 1; j < n; j++) {
                int backup = dp[j];
                if (str[i] == str[j]) {
                    dp[j] = 2 + leftDown;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                leftDown = backup;
            }
        }
        return dp[n - 1];
    }

    public static int longestPalindromeSubseq1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process(str, 0, str.length - 1);
    }

    public static int process(char[] str, int l, int r) {
        if (l == r) {
            return 1;
        }
        if (l == r - 1) {
            return str[l] == str[r] ? 2 : 1;
        }
        int p1 = process(str, l + 1, r - 1);
        int p2 = process(str, l + 1, r);
        int p3 = process(str, l, r - 1);
        int p4 = str[l] != str[r] ? 0 : (2 + p1);
        return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
    }
}
