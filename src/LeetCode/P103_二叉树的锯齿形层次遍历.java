package LeetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行
 */
public class P103_二叉树的锯齿形层次遍历 {
    /**
     * 在普通层序遍历上用 flag 交替每层的记录方向：正向层追加到层列表尾部，
     * 反向层插入到层列表头部。队列始终按左子节点、右子节点的顺序扩展。
     * 当层宽为 K 时，ArrayList 的头插需要 O(K)，因此极端宽树下总时间可达 O(N^2)；
     * 额外空间为 O(W)（不计返回结果）。
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) return lists;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = true;
        while (!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (flag) {
                    list.add(node.val);
                } else {
                    list.add(0, node.val);
                }
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            lists.add(list);
            flag = !flag;
        }
        return lists;
    }
}
