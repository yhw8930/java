package LeetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 *
 */
public class P42_接雨水 {
    /**
     * 解法一：双指针。某个位置能接的水量由它左侧最高柱和右侧最高柱中的较小值决定。
     * leftMax 和 rightMax 分别记录两个指针已经遇到的最高柱。
     * 当 height[left] < height[right] 时，右边已有一根比当前左柱更高的边界，
     * 因此可以立即结算 left：若当前高度低于 leftMax，就累加 leftMax - height[left]。
     * 反之结算 right。每次都只移动当前较短的一侧，直到两个指针相遇。
     * 该实现假设 height 不为 null；空数组会直接返回 0。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(1)。
     */
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int total = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // 左边较矮，此时leftMax一定就是"左边最大值"，且必然 <= rightMax
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    total += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    total += rightMax - height[right];
                }
                right--;
            }
        }
        return total;
    }

    /**
     * 解法二：前缀最大值和后缀最大值。
     * leftMax[i] 记录从 0 到 i 的最高柱，rightMax[i] 记录从 i 到末尾的最高柱。
     * 位置 i 的水面高度是 min(leftMax[i], rightMax[i])，
     * 所以该位置的接水量为 min(leftMax[i], rightMax[i]) - height[i]。
     * 先分别预处理两个数组，再累加每个位置的贡献。
     * 该实现假设 height 不为 null，并对空数组直接返回 0。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(N)。
     */
    public int trap2(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return total;
    }

    /**
     * 解法三：单调栈。栈中保存柱子下标，对应高度从栈底到栈顶单调不增。
     * 当前柱高于栈顶柱时，说明找到了凹槽的右边界：弹出的 top 是凹槽底部，
     * 弹出后的新栈顶是左边界，当前下标 i 是右边界。
     * 这一层水的宽度为 i - left - 1，高度为 min(height[left], height[i]) - height[top]。
     * 一次循环可能连续弹栈，相当于从低到高逐层结算凹槽中的雨水；
     * 若弹出后栈为空，说明缺少左边界，当前层无法接水。
     * 该实现假设 height 不为 null；空数组会直接返回 0。
     *
     * 时间复杂度：O(N)，每个下标最多入栈和出栈各一次。
     * 额外空间复杂度：O(N)。
     */
    public int trap3(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();  // 存下标，栈中对应高度从底到顶递减
        int total = 0;

        for (int i = 0; i < height.length; i++) {
            // 只要当前柱子比栈顶柱子高，就说明可以结算一层水
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();  // 弹出的是"凹槽的底部"

                if (stack.isEmpty()) {
                    break;  // 左边没有墙了，无法接水
                }

                int left = stack.peek();      // 凹槽左边的墙
                int width = i - left - 1;      // 凹槽的宽度
                int boundedHeight = Math.min(height[left], height[i]) - height[top]; // 这一层的高度

                total += width * boundedHeight;
            }
            stack.push(i);
        }

        return total;
    }
}
