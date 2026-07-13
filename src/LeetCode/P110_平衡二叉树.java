package LeetCode;

/**
 * 给定一个二叉树，判断它是否是 平衡二叉树(平衡二叉树 是指该树所有节点的左右子树的高度相差不超过1)
 *
 */
public class P110_平衡二叉树 {
    /**
     * 解法一：后序遍历计算子树高度，并用 -1 同时表示“当前子树已经不平衡”。
     * process 遇到空节点返回高度 0；左右子树都平衡且高度差不超过 1 时，
     * 返回当前子树的真实高度，否则返回 -1。一旦某棵子树返回 -1，即可提前向上传递，
     * 无需再重复计算它的高度。根节点的最终结果不是 -1，整棵树就平衡。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root) != -1;
    }

    public int process(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = process(node.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = process(node.right);
        if (rightHeight == -1) {
            return -1;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * 解法二：树形 DP。process2 为每棵子树返回两项信息：
     * isBalance 表示子树是否平衡，height 表示子树高度。
     * 当前子树平衡当且仅当左右子树都平衡，并且两者的高度差不超过 1。
     * 空子树视为高度为 0 的平衡树，父节点再根据左右信息合并出自己的 Info。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
     */
    public boolean isBalanced2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process2(root).isBalance;
    }

    public class Info {
        boolean isBalance;
        int height;

        public Info(boolean i, int h) {
            isBalance = i;
            height = h;
        }
    }

    public Info process2(TreeNode node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process2(node.left);
        Info rightInfo = process2(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalance = true;
        if (!leftInfo.isBalance || !rightInfo.isBalance) {
            isBalance = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalance = false;
        }
        return new Info(isBalance, height);
    }
}
