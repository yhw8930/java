package LeetCode;

/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
 * <p>
 * 输入: [7,1,5,3,6,4]
 * 输出: 7
 */
public class P122_买卖股票的最佳时机II {
    public static void main(String[] args) {
        int[] ints = {7, 1, 5, 3, 6, 4};
        System.out.println(new P122_买卖股票的最佳时机II().maxProfit(ints));
    }

    /**
     * 贪心累加每两个相邻交易日之间的正收益。任意一段上涨区间的“低买高卖”收益，
     * 都等于该区间内每天正差值之和，所以这样不会错过任何利润。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] > 0) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }
}
