package Math;

/**
 * 查找数组中第一大与第二大的数，时间复杂度为O(n)
 */
public class FindMaxAndSecondMax {
    public static void findMaxAndSecondMax(int[] a) {
        int max = a[0];
        int secondMax = Integer.MIN_VALUE;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                secondMax = max;
                max = a[i];
            } else if (a[i] > secondMax) {
                secondMax = a[i];
            }
        }
        System.out.println("最大数是：" + max + "第二大数是：" + secondMax);
    }

    public static void main(String[] args) {
        int[] arr = {34, 71, 43, -4, 33, 55};
        findMaxAndSecondMax(arr);
    }
}
