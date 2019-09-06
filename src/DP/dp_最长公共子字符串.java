package DP;

/**
 * 给定两个字符串，求其最长公共子字符串
 * 递推公式 dp[i][j]=dp[i-1][j-1]+1
 */
public class dp_最长公共子字符串 {
    public static void main(String[] args) {
        System.out.println(new dp_最长公共子字符串().getLCString("acbcbcef", "abcbced"));
    }

    public String getLCString(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) return "";
        int[][] dp = new int[s1.length()][s2.length()];
        int maxLen = 0, maxEnd = 0;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
                if (dp[i][j] > maxLen) {
                    maxLen = dp[i][j];
                    maxEnd = i + 1;
                }
            }
        }
        return s1.substring(maxEnd - maxLen, maxEnd);
    }
}
