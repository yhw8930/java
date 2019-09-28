package Test;

import java.util.PriorityQueue;
import java.util.Queue;

//[2,4,5],[1,3,9],[6,7,8]]
public class P_合并k个有序数组 {
    public static void main(String[] args) {
        int[][] a = {{2, 4, 5}, {1, 3, 9}, {6, 7, 8}};
        int[] arrays = mergeArrays1(a);
        for (int i : arrays) {
            System.out.print(i + " ");
        }
    }

    public static int[] mergeArrays(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;
        int[] target = new int[row * col];
        int[] p = new int[row];
        int m = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < row; i++) {
            if (!queue.contains(arr[i][p[i]])) {
                queue.offer(arr[i][p[i]]);
            }
        }
        while (!queue.isEmpty()) {
            int temp = queue.poll();
            target[m++] = temp;
            for (int i = 0; i < row; i++) {
                if (p[i] != col && temp == arr[i][p[i]]) {
                    p[i] = ++p[i];
                    if (p[i] == col) {
                        continue;
                    }
                    queue.offer(arr[i][p[i]]);
                }
            }
        }
        return target;
    }

    public static int[] mergeArrays1(int[][] arr) {
        if (arr == null || arr.length == 0) return null;
        return mergeArrays1(arr, 0, arr.length - 1);
    }

    public static int[] mergeArrays1(int[][] arr, int left, int right) {
        if (left == right) return arr[left];
        if (left + 1 == right) return mergeTwoArray(arr[left], arr[right]);
        int mid = left + ((right - left) >> 1);
        int[] a = mergeArrays1(arr, left, mid);
        int[] b = mergeArrays1(arr, mid + 1, right);
        return mergeTwoArray(a, b);
    }

    public static int[] mergeTwoArray(int[] a, int[] b) {
        int[] arr = new int[a.length + b.length];
        int i = 0, j = 0, m = 0;
        while (i < a.length && j < b.length) {
            if (a[i] > b[j]) {
                arr[m++] = b[j++];
            } else {
                arr[m++] = a[i++];
            }
        }
        while (i < a.length) {
            arr[m++] = a[i++];
        }
        while (j < b.length) {
            arr[m++] = b[j++];
        }
        return arr;
    }
}
