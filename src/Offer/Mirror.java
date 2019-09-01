package Offer;

/**
 * 操作给定的二叉树，将其变换为源二叉树的镜像，左右节点交换
 */
public class Mirror {
    public static void main(String[] args) {

    }

    public void Mirror(TreeNode root) {
        if (root == null) return;
        TreeNode temp = null;
        temp = root.right;
        root.right = root.left;
        root.left = temp;
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
    }
}
