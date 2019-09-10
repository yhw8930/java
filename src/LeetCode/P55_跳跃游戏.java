package LeetCode;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个位置。
 * <p>
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 从位置 0 到 1 跳 1 步, 然后跳 3 步到达最后一个位置。
 */
public class P55_跳跃游戏 {
    public static void main(String[] args) {
        int[] ints = {2, 3, 1, 1, 4};
        System.out.println(new P55_跳跃游戏().canJump(ints));
    }

    public boolean canJump(int[] nums) {
        int last = nums.length - 1;
        for (int i = last; i >= 0; i--) {
            if (i + nums[i] >= last) {
                last = i;
            }
        }
        return last == 0;
    }
}
