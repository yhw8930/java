package Offer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 * <p>
 * 1.用最大堆保存这k个数，每次只和堆顶比，如果比堆顶小，删除堆顶，新数入堆
 */
public class GetLeastNumbers {
    public static void main(String[] args) {
        int[] ints = {4, 5, 1, 6, 2, 7, 3, 8};
        System.out.println(new GetLeastNumbers().GetLeastNumbers_Solution(ints, 4));
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length) return list;
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, (o1, o2) -> o2.compareTo(o1));
        for (int i = 0; i < input.length; i++) {
            if (queue.size() < k) {
                queue.offer(input[i]);
            } else if (queue.peek() > input[i]) {
                queue.poll();
                queue.offer(input[i]);
            }
        }
        for (Integer integer : queue) {
            list.add(integer);
        }
        return list;
    }
}
