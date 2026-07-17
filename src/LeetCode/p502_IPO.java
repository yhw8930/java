package LeetCode;

import java.util.PriorityQueue;

/**
 * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。
 * 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
 * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
 * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
 * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
 *
 * 每次只做：钱够 + 利润最高的项目！小根堆：按成本，锁所有项目; 大根堆：按利润，选当前最优
 *
 * 时间复杂度：O((n+k)logn); 空间复杂度：O(n)
 */

public class p502_IPO {
    public static void main(String[] args) {
        int k = 2, w = 0;
        int[] profits = {1, 2, 3};
        int[] capital = {0, 1, 1};
        System.out.println(findMaximizedCapital(k, w, profits, capital));
    }

    public static class Program {
        public int p;
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    /**
     * 小根堆按启动资本保存尚未解锁的项目，大根堆按利润保存当前资本 w 已可执行的项目。
     * 每轮先把所有 capital≤w 的项目转入利润堆，再选利润最大者；因为利润非负，当前多获得资本不会减少后续选择。
     * 时间 O((N+K)log N)，额外空间 O(N)。
     */
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>((o1, o2) -> o1.c - o2.c);
        PriorityQueue<Program> maxPriorityQ = new PriorityQueue<>((o1, o2) -> o2.p - o1.p);
        for (int i = 0; i < profits.length; i++) {
            minCostQ.add(new Program(profits[i], capital[i]));
        }
        for (int i = 0; i < k; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().c <= w) {
                maxPriorityQ.add(minCostQ.poll());
            }
            if (maxPriorityQ.isEmpty()) {
                return w;
            }
            w += maxPriorityQ.poll().p;
        }
        return w;
    }
}
