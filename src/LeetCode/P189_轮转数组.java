package LeetCode;

/**
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 *
 * */
public class P189_轮转数组 {
    /**
     * 三次反转原地完成右转：先反转全部，再分别反转前 k 个和后 n-k 个元素。
     * k % n 将转动次数归一化；原题保证 n > 0，否则这里会除零。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;  // 防止k比n还大（比如k=7, n=3，等价于轮转1位）

        reverse(nums, 0, n - 1);       // 第一步：整体反转
        reverse(nums, 0, k - 1);       // 第二步：反转前k个
        reverse(nums, k, n - 1);       // 第三步：反转剩下的
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 使用辅助数组，将原下标 i 的元素放到 (i+k)%n，再整体拷回 nums。
     * 时间复杂度：O(N)；额外空间：O(N)。
     */
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = nums[i];
        }
        System.arraycopy(result, 0, nums, 0, n);
    }

    /**
     * 环状替换：每次沿 (current+k)%n 追踪一个下标环，用临时变量依次搬运元素。
     * 下标共分成 gcd(k,n) 个不相交的环，从每个环的起点处理一次即可覆盖全部元素。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public void rotate3(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int count = gcd(k, n);
        for (int start = 0; start < count; ++start) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
            } while (start != current);
        }
    }

    public int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }

}
