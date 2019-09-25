import java.util.*;

public class Main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] s = str.split(",");
        String[] split = s[s.length - 1].split(";");
        s[s.length - 1] = split[0];
        int n = Integer.valueOf(split[1]);
        int m = 0;
        String[] target = new String[n];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            list.add(Integer.parseInt(s[i]));
        }
        Collections.sort(list);
        Collections.reverse(list);
        for (int i = 0; i < s.length && m < n; i++) {
            if (list.get(i) %2 == 0) {
                target[m++] = String.valueOf(list.get(i));
            }
        }

        for (int i = 0; i < s.length && m < n; i++) {
            if (list.get(i) % 2 != 0) {
                target[m++] = String.valueOf(list.get(i));
            }
        }
        String ss="";
        for (int i = 0; i < target.length-1; i++) {
            ss+=target[i];
            ss+=",";
        }
        ss+=target[target.length-1];
        System.out.print(ss);
    }

    static int solution(int[] prices, int budget) {
        if (prices==null||prices.length==0||budget<1) return 0;
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int num : prices) {
            for (int i = num; i <= budget; i++) {
                if (dp[i - num] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - num] + 1);
                }
            }
        }
        return dp[budget] == Integer.MAX_VALUE ? -1 : dp[budget];
    }


    public int coinChange(int[] coins, int amount) {
        if (amount < 1) return 0;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                if (dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
