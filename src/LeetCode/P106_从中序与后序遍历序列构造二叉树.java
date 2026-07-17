package LeetCode;

/**
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * <p>
 * 注意:
 * 你可以假设树中没有重复的元素。
 * <p>
 * 例如，给出
 * <p>
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 */
public class P106_从中序与后序遍历序列构造二叉树 {

    public static void main(String[] args) {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        TreeNode root = new P106_从中序与后序遍历序列构造二叉树().buildTree(inorder, postorder);
        preOrder(root);
    }

    /**
     * 后序区间的最后一个值是当前根；在中序区间找到根后，左右两侧分别是
     * 左、右子树，再按中序左区间长度划分后序区间并递归构建。
     * 代码依赖节点值互不相同，且两个遍历序列合法一致。
     * 时间复杂度：O(N^2)；递归额外空间：O(H)。
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        TreeNode root = fun(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
        return root;
    }

    public TreeNode fun(int[] post, int startPost, int endPost, int[] in, int startIn, int endIn) {
        if (startPost > endPost || startIn > endIn) return null;
        TreeNode root = new TreeNode(post[endPost]);
        for (int i = startIn; i <= endIn; i++) {
            if (post[endPost] == in[i]) {
                root.left = fun(post, startPost, i - startIn + startPost - 1, in, startIn, i - 1);
                root.right = fun(post, i - startIn + startPost, endPost - 1, in, i + 1, endIn);
                break;
            }
        }
        return root;
    }

    public static void preOrder(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }
}
