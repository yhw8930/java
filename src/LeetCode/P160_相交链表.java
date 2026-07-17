package LeetCode;
/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * 题目数据 保证 整个链式结构中不存在环。
 * 注意，函数返回结果后，链表必须 保持其原始结构
 * */
public class P160_相交链表 {
    /**
     * 双指针分别从两条链表出发，到达末尾后切换到另一条链表的头部。
     * 两个指针都走过 A+B 的等长路程，因此若有公共尾部，它们会在第一个相交节点相遇；
     * 若不相交，则最终同时为 null。比较的是节点引用，不是节点值。
     * 时间复杂度：O(M+N)；额外空间：O(1)。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pa = headA;
        ListNode pb = headB;
        while (pa != pb) {
            pa = pa == null ? headB : pa.next;
            pb = pb == null ? headA : pb.next;
        }
        return pa;
    }

    /**
     * 另一种无环链表解法：先计算两链表的长度差并检查尾节点是否相同；
     * 尾节点不同则不可能相交。让长链表先走长度差，再与短链表同步前进，首次相同的引用就是交点。
     * 时间复杂度：O(M+N)；额外空间：O(1)。
     */
    public static ListNode noLoop(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        // n  :  链表1长度减去链表2长度的值
        cur1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
        cur2 = cur1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }
}
