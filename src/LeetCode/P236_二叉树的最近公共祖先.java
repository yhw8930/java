package LeetCode;

import AlgorithmBasic.class13.Code03_lowestAncestor;

import java.util.HashMap;
import java.util.HashSet;

public class P236_二叉树的最近公共祖先 {
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
