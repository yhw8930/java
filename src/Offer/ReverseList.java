package Offer;

/**
 * 输入一个链表，反转链表后，输出新链表的表头
 */
public class ReverseList {
    public static void main(String[] args) {

    }

    public ListNode ReverseList(ListNode head) {
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
