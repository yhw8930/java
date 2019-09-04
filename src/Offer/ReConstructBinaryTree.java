package Offer;

/**
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
 */


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class ReConstructBinaryTree {
    public static void main(String[] args) {

    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        TreeNode root = fun(pre, 0, pre.length - 1, in, 0, in.length - 1);
        return root;
    }

    public TreeNode fun(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn) {
            return null;
        }
        TreeNode root = new TreeNode(pre[startPre]);
        for (int i = startIn; i <= endIn; i++) {
            if (pre[startPre] == in[i]) {
                root.left = fun(pre, startPre + 1, i - startIn + startPre, in, startIn, i - 1);
                root.right = fun(pre, i - startIn + startPre + 1, endPre, in, i+1, endIn);
                break;
            }
        }
        return root;
    }

}