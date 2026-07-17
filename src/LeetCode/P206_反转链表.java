package LeetCode;

public class P206_反转链表 {
    /**
     * 迭代反转：pre 是已反转部分的头，head 是待处理节点；每轮先保存后继，
     * 再把 head.next 指向 pre。遍历结束后 pre 就是新头节点。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null, cur = head;
        while (head != null) {
            cur = head.next;
            head.next = pre;
            pre = head;
            head = cur;
        }
        return pre;
    }

    /**
     * 递归反转：先让递归返回 head.next 起始部分的新头，再用 head.next.next=head
     * 将当前节点接到末尾，并断开原来的 head.next 以避免成环。
     * 时间复杂度：O(N)；递归额外空间：O(N)。
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newNode = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return newNode;
    }
}
