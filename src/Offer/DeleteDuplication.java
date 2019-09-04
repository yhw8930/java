package Offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
 * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class DeleteDuplication {
    public static void main(String[] args) {

    }

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) return pHead;
        ListNode head = new ListNode(0);
        head.next = pHead;
        ListNode pre = head;
        ListNode p = head.next;
        while (p != null) {
            if (p.next != null && p.val == p.next.val) {
                while (p.next != null && p.val == p.next.val) {
                    p = p.next;
                }
                pre.next = p.next;
                p = p.next;
            } else {
                pre = pre.next;
                p = p.next;
            }
        }
        return head.next;
    }
}
