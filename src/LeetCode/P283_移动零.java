package LeetCode;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * <p>
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 */
public class P283_移动零 {
    /**
     * slow 指向下一个非零元素应写入的位置；fast 从左到右扫描，将非零元素稳定地压缩到前部。
     * 压缩完成后将 [slow,n) 全部补 0。读取位置 fast 永远不小于 slow，因此原地覆盖不会丢失未读数据。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public void moveZeroes(int[] nums) {
        int slow = 0;  // 指向"下一个非零元素应该放置的位置"

        // 第一步：把所有非零元素依次搬到数组前面
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        // 第二步：slow之后的位置，全部补0
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
