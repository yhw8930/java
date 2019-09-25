package Offer;

import java.util.ArrayList;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵：
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class P17_顺时针打印矩阵 {
    public static void main(String[] args) {
        int[][] ints = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] ints1 = {{1}, {2}, {3}, {4}};
        ArrayList<Integer> list = new P17_顺时针打印矩阵().printMatrix(ints1);
        for (Integer i : list) {
            System.out.print(i + " ");
        }
    }

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;
        if (row == 0 || col == 0) return list;
        int top = 0, left = 0, right = col - 1, bottom = row - 1;
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            for (int i = top + 1; i <= bottom; i++) {
                list.add(matrix[i][right]);
            }
            for (int i = right - 1; i >= left && bottom > top; i--) {
                list.add(matrix[bottom][i]);
            }
            for (int i = bottom - 1; i > top && right > left; i--) {
                list.add(matrix[i][left]);
            }
            left++;
            top++;
            right--;
            bottom--;
        }
        return list;
    }
}
