package LeetCode;

import java.util.ArrayList;

/**
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。有效 二叉搜索树定义如下：
 * 节点的左子树只包含 严格小于 当前节点的数。
 * 节点的右子树只包含 严格大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * */
public class P98_验证二叉搜索树 {
    /**
     * 解法一：利用 BST 的中序遍历结果必然严格递增。
     * 先按“左子树、当前节点、右子树”收集所有节点，再检查每对相邻值。
     * 若出现当前值小于或等于前一个值，说明顺序被破坏，或者存在 BST 不允许的重复值。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(N)，中序序列保存所有节点；递归栈另需 O(H)。
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(root, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return false;
            }
        }
        return true;
    }

    public void in(TreeNode node, ArrayList<TreeNode> arr) {
        if (node == null) {
            return;
        }
        in(node.left, arr);
        arr.add(node);
        in(node.right, arr);
    }

    /**
     * 解法二：树形 DP。每棵子树向父节点返回三项信息：
     * 自己是否为 BST、子树最大值和子树最小值。
     * 父节点据此检查整棵左子树和右子树，而不是只检查直接孩子。
     * 当前树合法的条件是：左右子树都是 BST、左子树最大值严格小于 root，
     * 并且右子树最小值严格大于 root。空子树用 null Info 表示。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(H)，为递归调用栈高度。
     */
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).isBST;
    }

    public class Info {
        public boolean isBST;
        public int max;
        public int min;

        public Info(boolean i, int ma, int mi) {
            isBST = i;
            max = ma;
            min = mi;
        }
    }

    public Info process(TreeNode x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max = x.val;
        int min = x.val;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        boolean isBST = true;

        if (leftInfo != null && !leftInfo.isBST || rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }

        if (leftInfo != null && leftInfo.max>=x.val||rightInfo != null && rightInfo.min<=x.val) {
            isBST = false;
        }

        return new Info(isBST, max, min);
    }
}
