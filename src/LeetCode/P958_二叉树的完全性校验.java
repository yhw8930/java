package LeetCode;

import java.util.LinkedList;

/**
 * 给你一棵二叉树的根节点 root ，请你判断这棵树是否是一棵 完全二叉树 。
 * 在一棵 完全二叉树 中，除了最后一层外，所有层都被完全填满，并且最后一层中的所有节点都尽可能靠左。最后一层（第 h 层）中可以包含 1 到 2h 个节点。
 *
 * 输入：root = [1,2,3,4,5,6]
 * 输出：true
 * 解释：最后一层前的每一层都是满的（即，节点值为 {1} 和 {2,3} 的两层），且最后一层中的所有节点（{4,5,6}）尽可能靠左。
 * */
public class P958_二叉树的完全性校验 {
    /**
     * 解法：按层遍历二叉树，leaf 表示是否已经进入“后续节点必须都是叶节点”的收尾阶段。
     * 遍历中有两种情况可以直接判定不是完全二叉树：
     * 1. 一个节点没有左孩子却有右孩子；
     * 2. 已经遇到孩子不双全的节点后，后续节点仍然有孩子。
     * 只要未出现这两种情况，层序遍历结束时即可确认该树完全。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(W)，W 为树的最大宽度，最坏为 O(N)。
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        boolean leaf = false;
        TreeNode l = null;
        TreeNode r = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            l = root.left;
            r = root.right;
            // 如果之前已经出现过"孩子不双全"的节点（即已经进入了树的"收尾阶段"），但当前节点却还有孩子 → 说明后面还有节点，不符合"空缺只能出现在最后"的要求。
            // 一个节点只有右孩子没有左孩子 → 直接不满足完全二叉树。
            if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 解法二：树形 DP。process 为每棵子树返回是否为满二叉树、
     * 是否为完全二叉树以及子树高度，父节点根据左右子树信息合并答案。
     * 当前树为完全二叉树的情况有三类：
     * 1. 左右子树都是等高的满二叉树，当前树也是满二叉树；
     * 2. 左子树是完全二叉树、右子树是满二叉树，且左子树比右子树高一层；
     * 3. 左子树是满二叉树、右子树是完全二叉树，且两者等高。
     * 空子树视为高度为 0 的满二叉树和完全二叉树，便于统一处理边界。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
     */
    public static boolean isCBT2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).isCBT;
    }

    // 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

    public static Info process(TreeNode x) {
        if (x == null) return new Info(true, true, 0);
        Info l = process(x.left);
        Info r = process(x.right);

        int height = Math.max(l.height, r.height) + 1;
        boolean isFull = l.isFull && r.isFull && l.height == r.height;

        boolean isCBT;
        if (isFull) {
            isCBT = true;                       // 整棵满
        } else if (l.isCBT && r.isCBT) {
            isCBT = (r.isFull && l.height == r.height + 1)   // 左完全(含满)、右满、左高一层
                    || (l.isFull && l.height == r.height);      // 左满、右完全、等高
        } else {
            isCBT = false;
        }
        return new Info(isFull, isCBT, height);
    }
}
