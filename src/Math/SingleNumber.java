package Math;

/**
 * 找出数组中只出现一次的元素
 * 考虑32位的数字，只计算每个位中有多少1，并且sum %= 3一旦达到3就会清除它。运行每个位的所有数字后，
 * 如果我们有1，那么那个1属于单个数字，我们可以通过这样做简单地将其移回现场ans |= sum << i;
 */
public class SingleNumber {
    public static int singleNumber(int[] nums) {
        int ans = 0;
        for(int i = 0; i < 32; i++) {
            int sum = 0;
            for(int j = 0; j < nums.length; j++) {
                if(((nums[j] >> i) & 1) == 1) {
                    sum++;
                    sum %= 3;
                }
            }
            if(sum != 0) {
                ans |= sum << i;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] a={1,2,1,2,4,2,4,4,1,3};
        System.out.println(singleNumber(a));
    }
}
