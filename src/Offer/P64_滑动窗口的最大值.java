package Offer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
 * 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}，
 * {2,3,4,2,6,[2,5,1]}。
 * <p>
 * 使用一个双端队列，队列第一个位置保存当前窗口的最大值，每当窗口滑动一次
 * 1.判断当前最大值是否超出滑动窗口范围
 * 2.新增加的值从队尾开始比较，把所有比他小的值删除
 */
public class P64_滑动窗口的最大值 {
    public static void main(String[] args) {
        int[] ints = {2, 3, 4, 2, 6, 2, 5, 1};
        ArrayList<Integer> list = new P64_滑动窗口的最大值().maxInWindows(ints, 3);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

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
