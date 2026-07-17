package LeetCode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
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


    /**
     * 单调双端队列保存下标，对应元素值从队头到队尾单调不增。新元素入队前，
     * 队尾中更小的元素不可能再成为后续窗口最大值，可直接删除；队头过期下标也要删除。
     * 因此窗口形成后，队头始终是当前最大值下标。时间 O(N)，额外空间 O(K)。
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || nums.length < k) {
            return null;
        }
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // 存下标，值从队头到队尾递减
        for (int i = 0; i < n; i++) {
            // 第一步：从队尾开始，把比当前数小的都弹出去（它们没用了）
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            // 第二步：如果队头的下标已经滑出窗口范围，弹出
            if (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            // 第三步：窗口形成后（i >= k-1），记录当前窗口最大值（队头）
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }

    /**
     * 暴力枚举每个长度为 k 的窗口，并线性扫描窗口求最大值。
     * 时间复杂度：O((N-K+1)K)；除返回数组外的额外空间：O(1)。
     */
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
