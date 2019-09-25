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
