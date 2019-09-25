package Offer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6
 * 个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}，
 * {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
 */
public class P61_滑动窗口的最大值 {
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<>();
        if (num == null || num.length == 0 || size < 1 || size > num.length) return list;
        for (int i = 0; i < size; i++) {
            while (!deque.isEmpty() && num[i] > num[deque.getLast()]) {
                deque.removeLast();
            }
            deque.addLast(i);
        }
        list.add(num[deque.getFirst()]);
        for (int i = size; i < num.length; i++) {
            if (!deque.isEmpty() && deque.getFirst() <= i - size) {
                deque.removeFirst();
            }
            while (!deque.isEmpty() && num[i] > num[deque.getLast()]) {
                deque.removeLast();
            }
            deque.addLast(i);
            list.add(num[deque.getFirst()]);
        }
        return list;
    }
}
