package Offer;

import java.util.HashMap;

/**
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
 * 返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 * <p>
 * 使用哈希表，时间复杂度O(N)，额外空间复杂度O(N)
 */
class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

public class RandomListNodeClon {
    public static void main(String[] args) {

    }

    public RandomListNode Clone(RandomListNode pHead) {
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode cur = pHead;
        while (cur != null) {
            map.put(cur, new RandomListNode(cur.label));
            cur = cur.next;
        }
        cur = pHead;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            cur = cur.next;
        }
        RandomListNode head = map.get(pHead);
        cur = pHead;
        while (cur != null) {
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return head;
    }
}
