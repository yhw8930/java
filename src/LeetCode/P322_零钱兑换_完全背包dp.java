package LeetCode;

import java.util.Arrays;

/**
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回-1。
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 */
public class P322_零钱兑换_完全背包dp {
    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 5};
        System.out.println(coinChange(ints, 11));
    }

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 组成0元需要0枚硬币
        dp[0] = 0;
        for (int coin : coins) {
            // 完全背包：正序
            for (int rest = coin; rest <= amount; rest++) {
                if (dp[rest - coin] != Integer.MAX_VALUE) {
                    dp[rest] = Math.min(dp[rest], dp[rest - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public static int coinChange2(int[] coins, int amount) {
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        for (int rest = 1; rest <= amount; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest >= coins[index] && dp[index][rest - coins[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - coins[index]] + 1);
                }
            }
        }
        return dp[0][amount] == Integer.MAX_VALUE ? -1 : dp[0][amount];
    }


    public static int coinChange1(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return amount == 0 ? 0 : -1;
        }

        int ans = process(coins, 0, amount);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // coins[index...] 每种硬币可以任意张
    // 组成 rest，最少需要多少枚硬币
    public static int process(int[] coins, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
            int next = process(coins, index + 1, rest - zhang * coins[index]);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, zhang + next);
            }
        }
        return ans;
    }


//    public int coinChange(int[] coins, int amount) {
//        if (coins == null || coins.length == 0) return -1;
//        int[] dp = new int[amount + 1];
//        Arrays.fill(dp, amount + 1);
//        dp[0] = 0;
//        for (int i = 1; i <= amount; i++) {
//            for (int j = 0; j < coins.length; j++) {
//                if (coins[j] <= i) {
//                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
//                }
//            }
//        }
//        return dp[amount] > amount ? -1 : dp[amount];
//    }

}
