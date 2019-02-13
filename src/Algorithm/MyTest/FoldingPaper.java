package Algorithm.MyTest;

public class FoldingPaper {
    //二叉树中序遍历
    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    public static void printProcess(int i, int N, boolean flag) {
        if (i > N) {
            return;
        }
        printProcess(i + 1, N, true);
        System.out.print(flag ? "down  " : "up  ");
        printProcess(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}
