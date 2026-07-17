package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 */
public class P15_三数之和 {
    public static void main(String[] args) {
        int[] ints = {-1, 0, 1, 2, -1, -4};
        System.out.println(new P15_三数之和().threeSum(ints));
    }

    /**
     * 先排序，再枚举三元组的第一个数 nums[i]，用左右指针在其后查找两数之和 -nums[i]。
     * 和太小时右移 left，和太大时左移 right；命中后同时跳过两端重复值。
     * 外层也跳过重复的 nums[i]，从而保证结果不重复。该方法会原地重排 nums。
     * 时间复杂度：O(N^2)；除排序调用栈和返回结果外，额外空间：O(log N)。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // 第一步：排序
        for (int i = 0; i < nums.length - 2; i++) {
            // 剪枝：排序后最小的数都大于0，后面不可能凑出0
            if (nums[i] > 0) {
                break;
            }
            // 去重：跳过重复的 i，避免第一个数重复导致三元组重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];  // 转化为在 [left, right] 里找两数之和等于 target

            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 找到一组解后，跳过重复的 left 和 right
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;  // 和太小，左指针右移，增大sum
                } else {
                    right--; // 和太大，右指针左移，减小sum
                }
            }
        }
        return result;
    }
}
