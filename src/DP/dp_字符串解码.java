package DP;

/**
 * A -> 1 b ->2 z - >26,输入数字1231725输出可能的解码个数
 */
public class dp_字符串解码 {
    public static void main(String[] args) {
        System.out.println(new dp_字符串解码().getDecode("1231725"));
    }

    public int getDecode(String str) {
        if (str == null || str.length() == 0) return 0;
        int[] dp = new int[str.length()];
        if (str.length() < 2) return 1;
        dp[0] = 1;
        if (str.charAt(0) == '1' || (str.charAt(0) == '2' && str.charAt(1) <= '6')) {
            dp[1] = 2;
        }
        for (int i = 2; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                dp[i] = dp[i - 1];
            } else {
                return 0;
            }
            int temp = str.charAt(i - 1) - '0';
            temp = temp * 10 + str.charAt(i) - '0';
            if (str.charAt(i - 1) != '0' && temp <= 26) {
                dp[i] = dp[i] + dp[i - 2];
            }
        }
        return dp[str.length() - 1];
    }
}
