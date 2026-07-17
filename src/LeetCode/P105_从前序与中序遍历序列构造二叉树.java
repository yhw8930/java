package LeetCode;

/**
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 * <p>
 * 注意:
 * 你可以假设树中没有重复的元素。
 * <p>
 * 例如，给出
 * <p>
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 */
public class P105_从前序与中序遍历序列构造二叉树 {
    public static void main(String[] args) {

    }

    /**
     * 前序区间的第一个值是当前根；在中序区间找到它后，左右两侧分别对应
     * 左、右子树，中序左区间的长度用来划分前序区间。区间为空时返回 null。
     * 代码依赖节点值互不相同，且两个遍历序列合法一致。
     * 时间复杂度：O(N^2)，最坏每层都线性搜索中序区间；递归空间：O(H)。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode root = fun(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        return root;
    }

    public TreeNode fun(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn) return null;
        TreeNode root = new TreeNode(pre[startPre]);
        for (int i = startIn; i <= endIn; i++) {
            if (pre[startPre] == in[i]) {
                root.left = fun(pre, startPre + 1, i - startIn + startPre, in, startIn, i - 1);
                root.right = fun(pre, i - startIn + startPre + 1, endPre, in, i + 1, endIn);
                break;
            }
        }
        return root;
    }
}
