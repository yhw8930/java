package LeetCode;

/**
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 * 假设每一种面额的硬币有无限个。题目数据 保证 最终 结果符合 32 位 带符号整数。
 * <p>
 * 输入：amount = 5, coins = [1, 2, 5]
 * 输出：4
 * 解释：有四种方式可以凑成总金额：
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 */
public class P518_零钱兑换II_完全背包dp {
    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 5};
        System.out.println(change(5, ints));
    }

    /**
     * dp[index][rest] 表示使用 coins[index..] 凑出 rest 的组合数。转移为不用当前面值，
     * 加上至少用一枚当前面值的方案；后者依赖本行 rest-coin，体现可重复使用。
     * 时间 O(N·amount)，额外空间 O(N·amount)。
     */
    public static int change(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
            }
        }
        return dp[0][amount];
    }

    /**
     * 一维完全背包：dp[rest] 是凑 rest 的组合数。先枚举硬币、再正序枚举金额，
     * 既允许重复使用当前硬币，又避免将选取顺序不同的同一组合重复计数。
     * 时间 O(N·amount)，额外空间 O(amount)。
     */
    public int change3(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        int[] dp = new int[amount + 1];
        // 组成0元的方法数
        dp[0] = 1;
        for (int coin : coins) {
            for (int rest = coin; rest <= amount; rest++) {
                dp[rest] += dp[rest - coin];
            }
        }
        return dp[amount];
    }

    /**
     * 未优化的二维 DP，每个状态显式枚举当前面值使用张数。
     * 时间最坏 O(N·amount²/minCoin)，额外空间 O(N·amount)。
     */
    public static int change1(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * coins[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][amount];
    }

    /**
     * 暴力递归枚举每种面值使用张数，并把剩余金额交给后续面值。
     * 无记忆化，时间复杂度为指数级；递归空间 O(N)。
     */
    public static int change2(int amount, int[] coins) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        return process(coins, 0, amount);
    }

    public static int process(int[] coins, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; coins[index] * zhang <= rest; zhang++) {
            ways += process(coins, index + 1, rest - coins[index] * zhang);
        }
        return ways;
    }


}
