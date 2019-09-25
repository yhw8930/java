package Offer;

/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 */
public class P12_链表中倒数第k个结点 {
    public static void main(String[] args) {

    }

    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k <= 0) return null;
        ListNode p1 = head, p2 = head;
        for (int i = 0; i < k - 1; i++) {
            if (p1.next != null) {
                p1 = p1.next;
            } else {
                return null;
            }
        }
        while (p1.next != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        return p2;
    }
}
