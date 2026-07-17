package LeetCode;
/**
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
 *
 * 输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * 输出：[[1,0,1],[0,0,0],[1,0,1]]
 * */
public class P73_矩阵置零 {
    /**
     * 实现正确，使用两个布尔数组分别记录哪些行、哪些列在原矩阵中出现过 0。
     * 第一次遍历只收集标记，不修改矩阵，避免新写入的 0 被当成原始 0 继续传播；
     * 第二次遍历中，若 row[i] 或 col[j] 为 true，就将 matrix[i][j] 置零。
     *
     * 该方法会直接修改原矩阵，但使用了 O(M+N) 的辅助标记空间。
     * 时间复杂度：O(MN)；额外空间复杂度：O(M+N)。
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = col[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * 实现正确，复用矩阵的第一列和第一行作为标记数组，将额外空间降为 O(1)。
     * matrix[i][0] == 0 表示第 i 行最终需要置零；matrix[0][j] == 0 表示第 j 列
     * 最终需要置零。由于 matrix[0][0] 无法同时独立表示第一行和第一列的状态，
     * flagRow0 和 flagCol0 分别保存它们在原矩阵中是否含 0。
     *
     * 处理顺序是：先保存第一行、第一列的状态；再扫描内部区域写入行列标记；
     * 然后根据标记修改内部区域；最后才根据两个 flag 修改第一行和第一列，
     * 以免过早破坏存放在边界中的标记。
     *
     * 时间复杂度：O(MN)；额外空间复杂度：O(1)。
     */
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean flagCol0 = false, flagRow0 = false;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                flagCol0 = true;
            }
        }
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                flagRow0 = true;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (flagCol0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (flagRow0) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
    }

    /**
     * 实现正确，是 setZeroes2 的精简写法：仍用第一行记录列标记、第一列记录行标记，
     * 但只额外保留 flagCol0。flagCol0 记录原第一列是否含 0；matrix[0][0]
     * 则作为第一行是否需要置零的标记。
     *
     * 第一轮从上到下扫描原矩阵并写入标记。第二轮必须从下到上恢复结果：
     * 如果先处理第一行，其元素可能因第一行需置零而全部变为 0，从而破坏各列的原标记，
     * 导致后续行被误判。倒序遍历可以先让其他行读取完第一行的标记，最后再修改第一行。
     * 每行的第一列元素也在该行内部元素处理完后，再根据 flagCol0 置零。
     *
     * 时间复杂度：O(MN)；额外空间复杂度：O(1)。
     */
    public void setZeroes3(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean flagCol0 = false;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                flagCol0 = true;
            }
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (flagCol0) {
                matrix[i][0] = 0;
            }
        }
    }

}
