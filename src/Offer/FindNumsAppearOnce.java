package Offer;

import java.util.*;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字
 */
public class FindNumsAppearOnce {
    public static void main(String[] args) {
        int[] ints = new int[]{2,4,3,6,3,2,5,5};
        int[] ints1 = new int[1];
        int[] ints2 = new int[1];

        new FindNumsAppearOnce().FindNumsAppearOnce(ints,ints1,ints2);
    }

    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        if (array == null || array.length == 0) return;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                map.put(array[i], map.get(array[i]) + 1);
            } else {
                map.put(array[i], 1);
            }
        }
        List<Integer> list = new ArrayList<>(2);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                list.add(entry.getKey());
            }
        }
        num1[0] = list.get(0);
        num2[0] = list.get(1);
    }
}
