package LeetCode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 * <p>
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 *
 */
public class P239_滑动窗口最大值_单调栈 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println(Arrays.toString(maxSlidingWindow(nums, k)));
    }

    // 用一个双端队列保存下标，队列中对应的值从头到尾严格递减；队头始终是当前窗口最大值下标。
    // 时间复杂度：O(N)，每个下标最多进队、出队一次。
    // 空间复杂度：O(k)，双端队列空间。
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || nums.length < k) {
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        int index = 0;
        for (int r = 0; r < nums.length; r++) {
            while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[r]) {
                qmax.pollLast();
            }
            qmax.addLast(r);
            if (qmax.peekFirst() == r - k) {
                qmax.pollFirst();
            }
            if (r >= k - 1) {
                res[index++] = nums[qmax.peekFirst()];
            }
        }
        return res;
    }

    // 时间复杂度：O(N*W)。
    // 空间复杂度：O(1)，不计算返回数组。
    public static int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || nums.length < k) {
            return null;
        }
        int[] res = new int[nums.length - k + 1];
        int l = 0;
        int r = k - 1;
        int index = 0;
        while (r < nums.length) {
            int maxValue = nums[l];
            for (int i = l + 1; i <= r; i++) {
                if (nums[i] >= maxValue) {
                    maxValue = nums[i];
                }
            }
            res[index++] = maxValue;
            l++;
            r++;
        }
        return res;
    }
}
