package Offer;

import java.util.ArrayList;

/**
 * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
 * <p>
 * 对应每个测试案例，输出两个数，小的先输出。
 */
public class FindNumbersWithSum {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(new FindNumbersWithSum().FindNumbersWithSum(ints, 9));
    }

    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (array == null || array.length < 2) return list;
        int start = 0, end = array.length - 1;
        while (start < end) {
            int total = array[start] + array[end];
            if (total == sum) {
                list.add(array[start]);
                list.add(array[end]);
                break;
            } else if (total > sum) {
                end--;
            } else {
                start++;
            }
        }
        return list;
    }
}
