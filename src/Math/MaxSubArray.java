package Math;

/**
 * 求最大子数组之和,时间复杂度为O(n)
 */
public class MaxSubArray {
    private static int start = 0, end = 0;
    public static int maxSubArray(int[] a) {
        int sum = 0;
        int maxSum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            if (sum > maxSum) {
                maxSum = sum;
                end = i;
            } else if (sum < 0) {
                sum = 0;
                start = i+1;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] a = {1, -2, 4, 8, -4, 7, -1, 3};
        System.out.println(maxSubArray(a));
        System.out.println(start + "--" + end);
    }
}
