package Offer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印.
 */
public class P56_按之字形顺序打印二叉树 {
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (pRoot == null) return lists;
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        int level = 1;
        stack1.push(pRoot);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            if (level++ % 2 != 0) {
                while (!stack1.isEmpty()) {
                    TreeNode node = stack1.pop();
                    list.add(node.val);
                    if (node.left != null) stack2.push(node.left);
                    if (node.right != null) stack2.push(node.right);
                }
            } else {
                while (!stack2.isEmpty()) {
                    TreeNode node = stack2.pop();
                    list.add(node.val);
                    if (node.right != null) stack1.push(node.right);
                    if (node.left != null) stack1.push(node.left);
                }
            }
            lists.add(list);
        }
        return lists;
    }
}
