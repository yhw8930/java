package DP;

/**
 * str1=helloWorld str2 = loop ,则最长子序列维loo ，长度为3
 * 当i==0 or j==0时，dp[i][j] == 0;
 * 当str1[i]==str2[j],dp[i][j] = dp[i-1][j-1] =1
 * 当str1[i]!=str2[j],dp[i][j] = max(dp[i-1][j],dp[i][j-1])
 */
public class dp_最长公共子序列 {
    public static void main(String[] args) {
        System.out.println(new dp_最长公共子序列().getLCS("helloWorld", "loop"));
    }

    public int getLCS(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2.length() == 0 || s2 == null) return 0;
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        int maxLen = 0, maxEnd = 0;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
