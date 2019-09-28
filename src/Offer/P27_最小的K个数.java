package Offer;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 */
public class P27_最小的K个数 {
    public static void main(String[] args) {
        int[] ints = {4, 5, 1, 6, 2, 7, 3, 8};
        ArrayList<Integer> list = new P27_最小的K个数().GetLeastNumbers_Solution(ints, 4);
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length) return list;
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
        for (int i = 0; i < input.length; i++) {
            if (queue.size() < k) {
                queue.offer(input[i]);
            } else if (queue.peek() > input[i]) {
                queue.poll();
                queue.offer(input[i]);
            }
        }
        list.addAll(queue);
        return list;
    }

    public ArrayList<Integer> getLeastNumbers(int[] input, int k) {

        ArrayList<Integer> list = new ArrayList<>();
        while (input == null || input.length == 0 || k <= 0 || k > input.length)
            return list;
        int start = 0;
        int end = input.length - 1;
        int index = partition(input, start, end);
        while (index != k - 1) {
            if (index < k - 1) {
                index = partition(input, index + 1, end);
            } else {
                index = partition(input, start, index - 1);
            }
        }
        for (int i = 0; i < k; i++) {
            list.add(input[i]);
        }
        return list;
    }

    public int partition(int[] arr, int left, int right) {
        int less = left - 1;
        int more = right;
        while (left < more) {
            if (arr[left] < arr[right]) {
                swap(arr, ++less, left++);
            } else if (arr[left] > arr[right]) {
                swap(arr, --more, left);
            } else {
                left++;
            }
        }
        swap(arr, more, right);
        return left;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
