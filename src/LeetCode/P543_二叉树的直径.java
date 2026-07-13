package LeetCode;

/**
 *
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
 * 两节点之间路径的 长度 由它们之间边数表示。
 *
 *
 */
public class P543_二叉树的直径 {
    /**
     * 解法：树形 DP。process 为每棵子树返回两项信息：
     * maxDistance 是子树内的最大直径，height 是子树高度，高度按节点数计算。
     * 当前子树的最大直径有三种来源：
     * 1. 最长路径完全位于左子树，答案是 leftInfo.maxDistance；
     * 2. 最长路径完全位于右子树，答案是 rightInfo.maxDistance；
     * 3. 最长路径穿过当前节点，连接左右子树的最深节点，
     *    边数为 leftInfo.height + rightInfo.height。
     * 对三种可能取最大值，就得到当前子树的 maxDistance。
     * 空子树的高度和直径都为 0，因此单节点树的直径为 0 条边。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(H)，H 为树高，来自递归调用栈。
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxDistance;
    }

    public class Info {
        int maxDistance;
        int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
    }

    public Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int m1 = leftInfo.maxDistance;
        int m2 = rightInfo.maxDistance;
        int m3 = leftInfo.height + rightInfo.height;
        int maxDistance = Math.max(Math.max(m1, m2), m3);
        return new Info(maxDistance, height);
    }
}
