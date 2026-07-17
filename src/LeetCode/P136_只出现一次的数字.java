package LeetCode;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素
 */
public class P136_只出现一次的数字 {
    public static void main(String[] args) {
        int[] ints = {4, 1, 2, 1, 2};
        System.out.println(new P136_只出现一次的数字().singleNumber(ints));
    }

    /**
     * 利用异或的交换律、结合律，以及 x ^ x = 0、0 ^ x = x。
     * 所有成对数字异或后相互抵消，最终只剩唯一出现一次的数字。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }

}
