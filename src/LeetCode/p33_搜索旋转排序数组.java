package LeetCode;

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 * 你可以假设数组中不存在重复的元素。
 * 你的算法时间复杂度必须是 O(log n) 级别。
 */
public class p33_搜索旋转排序数组 {
    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 7, 0, 1, 2};
        int[] ints2 = {1, 3};
        int[] ints3 = {3, 1};
        int[] ints4 = {4, 5, 6, 7, 8, 1, 2, 3};
        System.out.println(new p33_搜索旋转排序数组().search(ints, 6));
        System.out.println(new p33_搜索旋转排序数组().search(ints2, 1));
        System.out.println(new p33_搜索旋转排序数组().search(ints3, 1));
        System.out.println(new p33_搜索旋转排序数组().search(ints4, 8));

    }

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        if (target == nums[0]) return 0;
        if (target == nums[nums.length - 1]) return nums.length - 1;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (target <= nums[0]) {
                if (target <= nums[mid] && nums[mid] < nums[0]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && nums[mid] >= nums[0]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        return nums[left] == target ? left : -1;
    }
}
