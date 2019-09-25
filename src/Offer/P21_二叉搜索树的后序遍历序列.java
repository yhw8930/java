package Offer;

/**
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 */
public class P21_二叉搜索树的后序遍历序列 {
    public static void main(String[] args) {

    }

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return isBST(sequence, 0, sequence.length - 1);
    }

    public boolean isBST(int[] sequence, int start, int end) {
        if (start >= end) return true;
        int i = 0;
        for (i = 0; i < end; i++) {
            if (sequence[i] > sequence[end]) {
                break;
            }
        }
        for (int j = i; j < end; j++) {
            if (sequence[j] < sequence[end]) return false;
        }
        return isBST(sequence, start, i - 1) && isBST(sequence, i, end - 1);
    }

}
