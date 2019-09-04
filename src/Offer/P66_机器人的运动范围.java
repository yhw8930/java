package Offer;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
 * 但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），
 * 因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
 */
public class P66_机器人的运动范围 {
    public static void main(String[] args) {

    }

    public int movingCount(int threshold, int rows, int cols) {
        int[][] flag = new int[rows][cols];
        return moving(threshold, rows, cols, flag, 0, 0);
    }

    public int moving(int threshold, int rows, int cols, int[][] flag, int i, int j) {
        if (threshold <= 0 || i >= rows || i < 0 || j >= cols || j < 0 || (flag[i][j] == 1) || (sum(i) + sum(j) > threshold)) {
            return 0;
        }
        flag[i][j] = 1;
        return moving(threshold, rows, cols, flag, i - 1, j)
                + moving(threshold, rows, cols, flag, i + 1, j)
                + moving(threshold, rows, cols, flag, i, j - 1)
                + moving(threshold, rows, cols, flag, i, j + 1)
                + 1;
    }

    public int sum(int i) {
        if (i == 0) {
            return i;
        }
        int sum = 0;
        while (i != 0) {
            sum += i % 10;
            i /= 10;
        }
        return sum;
    }
}
