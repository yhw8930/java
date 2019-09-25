package Offer;

/**
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 */
public class P15_树的子结构 {
    public static void main(String[] args) {

    }

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean res = false;
        if (root1 == null || root2 == null) return false;
        if (root1.val == root2.val) {
            res = fun(root1, root2);
        }
        if (!res) res = HasSubtree(root1.left, root2);
        if (!res) res = HasSubtree(root1.right, root2);
        return res;
    }

    public boolean fun(TreeNode root1, TreeNode root2) {
        if (root2 == null) return true;
        if (root1 == null) return false;
        if (root1.val != root2.val) return false;
        return fun(root1.left, root2.left) && fun(root1.right, root2.right);
    }
}
