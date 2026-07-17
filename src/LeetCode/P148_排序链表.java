package LeetCode;

import java.util.Map;

/**
 * NlogN 时间复杂度和常数级空间复杂度下，对链表进行排序
 */
public class P148_排序链表 {
    /**
     * 入口选用链表归并排序：快慢指针将链表从中间断开，递归排序两半，
     * 再通过改变 next 指针线性合并两个有序链表。递归终止于空链表或单节点链表。
     * 时间复杂度：O(N log N)；递归额外空间：O(log N)。
     * 注：这不是严格的 O(1) 额外空间实现，因为使用了递归调用栈。
     */
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
        //quickSort(head, null);
        //return head;
    }

    public ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = null, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            pre = slow;
            fast = fast.next.next;
            slow = slow.next;
        }
        pre.next = null;
        ListNode l1 = mergeSort(head);
        ListNode l2 = mergeSort(slow);
        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
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

    public void quickSort(ListNode begin, ListNode end) {
        if (begin != end) {
            ListNode node = partition(begin, end);
            quickSort(begin, node);
            quickSort(node.next, end);
        }
    }

    public ListNode partition(ListNode begin, ListNode end) {
        if (begin == end) return begin;
        ListNode p = begin;
        ListNode q = begin.next;
        int key = begin.val;
        while (q != end) {
            if (q.val < key) {
                p = p.next;
                swap(p, q);
            }
            q = q.next;
        }
        swap(begin, p);
        return p;
    }

    public void swap(ListNode a, ListNode b) {
        int temp = a.val;
        a.val = b.val;
        b.val = temp;
    }
}
