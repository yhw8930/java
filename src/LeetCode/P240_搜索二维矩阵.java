package LeetCode;

public class P240_搜索二维矩阵 {
    /**
     * 从右上角开始：当前值是所在行未排除元素中的最大值，也是所在列未排除元素中的最小值。
     * 当前值大于 target 就排除当前列并左移；小于 target 就排除当前行并下移。
     * 时间复杂度：O(M+N)；额外空间：O(1)。代码依赖矩阵非空。
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0;
        int col = n - 1;
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }
}
