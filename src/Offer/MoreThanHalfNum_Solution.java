package Offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
 *  * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 */
public class MoreThanHalfNum_Solution {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        System.out.println(new MoreThanHalfNum_Solution().MoreThanHalfNum_Solution(ints));
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : array) {
            if (map.containsKey(a)) {
                map.put(a, map.get(a) + 1);
            } else {
                map.put(a, 1);
            }
        }
        for (Integer num : map.keySet()) {
            if (map.get(num) > array.length / 2) {
                return num;
            }
        }
        return 0;
    }
}
