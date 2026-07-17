package LeetCode;

import AlgorithmBasic.class13.Code03_lowestAncestor;

import java.util.HashMap;
import java.util.HashSet;

public class P236_二叉树的最近公共祖先 {
    /**
     * 树形 DP：process 为每棵子树返回是否找到 p、是否找到 q，以及已确定的最近公共祖先。
     * 若左右子树已有答案则向上传递；否则当当前子树首次同时包含 p 和 q 时，当前节点就是答案。
     * 时间复杂度：O(N)；递归额外空间：O(H)。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return process(root, p, q).ans;
    }

    public class Info {
        boolean findP;
        boolean findQ;
        TreeNode ans;

        public Info(boolean fp, boolean fq, TreeNode a) {
            findP = fp;
            findQ = fq;
            ans = a;
        }
    }

    public Info process(TreeNode node, TreeNode q, TreeNode p) {
        if (node == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(node.left, q, p);
        Info rightInfo = process(node.right, q, p);
        boolean findQ = node == q || leftInfo.findQ || rightInfo.findQ;
        boolean findP = node == p || leftInfo.findP || rightInfo.findP;
        TreeNode ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else if (findP && findQ) {
            ans = node;
        }
        return new Info(findP, findQ, ans);
    }

    /**
     * 先遍历整棵树建立“节点 -> 父节点”映射，将 p 到根的所有祖先放入集合；
     * 再q 不断向上，首个出现在该集合中的节点就是最近公共祖先。
     * 时间复杂度：O(N)；额外空间：O(N)。
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // key的父节点是value
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(root, null);
        fillParentMap(root, parentMap);
        HashSet<TreeNode> o1Set = new HashSet<>();
        TreeNode cur = p;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = q;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public void fillParentMap(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }
}
