package Offer;

import java.util.PriorityQueue;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个
 * 数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。
 */
public class P60_数据流中的中位数 {
    int count = 0;
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(11,(o1, o2) -> o2.compareTo(o1));

    public void Insert(Integer num) {
        count++;
        if ((count & 1) == 1) {
            if (!minHeap.isEmpty() && num > minHeap.peek()) {
                minHeap.offer(num);
                num = minHeap.poll();
            }
            maxHeap.offer(num);
        } else {
            if (!maxHeap.isEmpty() && num < maxHeap.peek()) {
                maxHeap.offer(num);
                num = maxHeap.poll();
            }
            minHeap.offer(num);
        }
    }

    public Double GetMedian() {
        if (count == 0) {
            throw new RuntimeException("no number!");
        } else if ((count & 1) == 1) {
            return (double) maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
}
