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

    /**
     * 一维完全背包：dp[rest] 是凑出 rest 的最少硬币数，不可达用 Integer.MAX_VALUE 表示。
     * 每种硬币的 rest 正序遍历，使 dp[rest-coin] 可以已包含当前硬币，从而允许重复使用。
     * 时间 O(N·amount)，额外空间 O(amount)。
     */
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

    /**
     * 二维优化 DP：dp[index][rest] 表示使用 coins[index..] 凑 rest 的最少枚数。
     * 转移在不用当前硬币和至少用一枚之间取小；后者依赖本行 rest-coin，体现可重复选择。
     * 时间 O(N·amount)，额外空间 O(N·amount)。
     */
    public static int coinChange3(int[] coins, int amount) {
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

    /**
     * 枚举型二维 DP：对每个状态显式枚举当前面值使用 zhang 枚，再接上下一种硬币的结果。
     * 时间最坏 O(N·amount²/minCoin)，额外空间 O(N·amount)。
     */
    public static int coinChange2(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return amount == 0 ? 0 : -1;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        // base case:
        // index == N 时，没有硬币了
        // rest == 0，需要 0 枚硬币
        // rest > 0，不可能，记为 Integer.MAX_VALUE
        dp[N][0] = 0;
        for (int rest = 1; rest <= amount; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * coins[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, zhang + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][amount] == Integer.MAX_VALUE ? -1 : dp[0][amount];
    }


    /**
     * 暴力递归：process(index,rest) 枚举当前面值使用张数，再递归处理后续面值。
     * 无记忆化，存在大量重复子问题，时间复杂度为指数级；递归栈 O(N)。
     */
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
}
