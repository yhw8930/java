package LeetCode;

/**
 * 给定一个二叉树，找出其最大深度。
 * <p>
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 */
public class P104_二叉树的最大深度 {
    /**
     * 递归定义：maxDepth(root) 返回以 root 为根的子树最大深度。
     * 空树深度为 0；非空树的深度是左右子树较大深度加 1。
     * 时间复杂度：O(N)；递归额外空间：O(H)，H 为树高。
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }
}
