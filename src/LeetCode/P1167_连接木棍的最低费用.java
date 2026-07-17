package LeetCode;

import java.util.PriorityQueue;

/**
 * 你有一些长度为正整数的木棍。这些长度以数组 sticks 的形式给出， sticks[i] 是第 i 个木棍的长度。
 * 你可以通过支付 x + y 的成本将任意两个长度为 x 和 y 的木棍连接成一个木棍。你必须连接所有的木棍，直到剩下一个木棍。
 * 返回以这种方式将所有给定的木棍连接成一个木棍的 最小成本 。
 *
 * 思路：总是选择两个最小的棍子进行连接，继续这样做直到只剩下一个棍子. 贪心+堆
 * 时间复杂度：O(NlogN); 空间复杂度：O(N)
 * */
public class P1167_连接木棍的最低费用 {
    public static void main(String[] args) {
         int[] sticks = {1,8,3,5};
        System.out.println(connectSticks(sticks));
    }

    /**
     * 使用小根堆反复取出当前最短的两根木棍合并，将本次代价加入答案，
     * 再把新木棍放回堆。越早合并的长度会被重复计入后续代价，因此每次选最小的两根最优。
     * 时间复杂度：O(N log N)；额外空间：O(N)。
     */
    public static int connectSticks(int[] sticks) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int stick : sticks) {
            pQ.add(stick);
        }
        int sum = 0;
        int cur = 0;
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll();
            sum += cur;
            pQ.add(cur);
        }
        return sum;
    }

}
