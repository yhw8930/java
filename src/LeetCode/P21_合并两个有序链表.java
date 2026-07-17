package LeetCode;

/**
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class P21_合并两个有序链表 {

    /**
     * 递归选择两个当前头节点中较小的一个作为结果头，它的 next 接上剩余部分的合并结果。
     * 任意一条链表为空时，直接返回另一条。时间 O(M+N)，递归空间 O(M+N)。
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode list = null;
        if (l1 == null && l2 == null) {
            return list;
        } else if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            list = l1;
            list.next = mergeTwoLists(l1.next, l2);
        } else {
            list = l2;
            list.next = mergeTwoLists(l1, l2.next);
        }
        return list;
    }

    /**
     * 迭代版使用哑节点，每次摘下两条链表中较小的头节点接到结果尾部，
     * 最后将未耗尽链表整段接上。时间 O(M+N)，额外空间 O(1)。
     */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return pre.next;
    }
}
