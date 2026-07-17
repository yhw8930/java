package LeetCode;

import java.util.Arrays;

/**
 * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
 * <p>
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 * <p>
 * 每个孩子至少分配到 1 个糖果。
 * 相邻的孩子中，评分高的孩子必须获得更多的糖果。
 * 那么这样下来，老师至少需要准备多少颗糖果呢？
 * <p>
 * 输入: [1,0,2]
 * 输出: 5
 * 解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
 */
public class P135_分发糖果 {
    public static void main(String[] args) {
        int[] arr = {1, 0, 2};
        System.out.println(new P135_分发糖果().candy(arr));
    }

    /**
     * left[i] 满足第 i 个孩子与左邻居的约束，right[i] 满足它与右邻居的约束。
     * 两次相反方向的遍历分别计算这两个下界，每个位置取 max(left[i], right[i])
     * 才能同时满足两侧，且正好是该位置的最小可行糖果数。
     * 时间复杂度：O(N)；额外空间：O(N)。
     */
    public int candy(int[] ratings) {
        int sum = 0;
        int[] left = new int[ratings.length];
        int[] right = new int[ratings.length];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }
        for (int i = 0; i < ratings.length; i++) {
            sum += Math.max(left[i], right[i]);
        }
        return sum;
    }

}
