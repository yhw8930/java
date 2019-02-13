package Algorithm.MyTest;

/**
 * 打印数组问题
 */
public class PrintMatrix {
    /**
     * 旋转打印数组
     *
     * @param matrix 数组引用
     */
    public static void spiralOrderPrint(int[][] matrix) {
        int aRow = 0;
        int aColumn = 0;
        int bRow = matrix.length - 1;
        int bColumn = matrix[0].length - 1;
        while (aRow <= bRow && aColumn <= bColumn) {
            printEdge(matrix, aRow++, aColumn++, bRow--, bColumn--);
        }
    }

    public static void printEdge(int[][] matrix, int aRow, int aColumn, int bRow, int bColumn) {
        if (aRow == bRow) {
            for (int i = aColumn; i <= bColumn; i++) {
                System.out.println(matrix[aRow][i] + " ");
            }
        } else if (aColumn == bColumn) {
            for (int i = aRow; i <= bRow; i++) {
                System.out.println(matrix[i][aColumn] + " ");
            }
        } else {
            int tempRow = aRow;
            int tempColumn = aColumn;
            while (tempColumn != bColumn) {
                System.out.print(matrix[tempRow][tempColumn++] + " ");
            }
            while (tempRow != bRow) {
                System.out.print(matrix[tempRow++][tempColumn] + " ");
            }
            while (tempColumn != aColumn) {
                System.out.print(matrix[tempRow][tempColumn--] + " ");
            }
            while (tempRow != aRow) {
                System.out.print(matrix[tempRow--][tempColumn] + " ");
            }
        }
    }

    /**
     * 请把该矩阵调整成 顺时针旋转90度的样子。
     * 转置法实现
     *
     * @param matrix 数组引用
     */
    public static void rotate(int[][] matrix) {
        int temp;
        //转置矩阵
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        //前后调换
        for (int i = 0; i < matrix.length; i++) {
            for (int m = 0, n = matrix.length - 1; m < matrix.length / 2; m++, n--) {
                temp = matrix[i][m];
                matrix[i][m] = matrix[i][n];
                matrix[i][n] = temp;
            }
        }
    }

    /**
     * 边界法旋转
     *
     * @param matrix
     */
    public static void rotate1(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR < dR) {
            rotateEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
        int times = dC - tC;
        int tmp = 0;
        for (int i = 0; i != times; i++) {
            tmp = m[tR][tC + i];
            m[tR][tC + i] = m[dR - i][tC];
            m[dR - i][tC] = m[dR][dC - i];
            m[dR][dC - i] = m[tR + i][dC];
            m[tR + i][dC] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }

    /**
     * 之字打印数组
     * @param matrix
     */
    public static void printMatrixZigZag(int[][] matrix) {
        int aRow = 0;
        int aColumn = 0;
        int bRow = 0;
        int bColumn = 0;
        int endRow = matrix.length - 1;
        int endColumn = matrix[0].length;
        boolean fromUp = false;
        while (aRow != endRow + 1) {
            aRow = aColumn == endColumn ? aRow + 1 : aRow;
            aColumn = aColumn == endColumn ? aColumn : aColumn + 1;
            bRow = bRow == endRow ? bRow : bRow + 1;
            bColumn = bRow == endRow ? bColumn + 1 : bColumn;
        }
    }

    public static void printLevel(int[][] m, int aRow, int aColumn, int bRow, int bColumn, boolean f) {
        if (f){
            while (aRow!=bRow+1){
                System.out.println(m[aRow++][aColumn--]+" ");
            }
        }else {
            while (bRow!=aRow-1){
                System.out.println(m[bRow--][bColumn--]);
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
                {13, 14, 15, 16}};
        System.out.println(matrix.length + "====" + matrix[0].length);
        //spiralOrderPrint(matrix);
        //rotate(matrix);
        //rotate1(matrix);
        //printMatrix(matrix);
        printMatrixZigZag(matrix);
    }
}
