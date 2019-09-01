package Offer;

/**
 * 最小连续子数组之和
 */
public class FindGreatestSumOfSubArray {
    public static void main(String[] args) {
        int[] ints = {6, -3, -2, 7, -15, 1, 2, 2};
        System.out.println(new FindGreatestSumOfSubArray().FindGreatestSumOfSubArray(ints));
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (sum > max) {
                max = sum;
            } else if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }
}
