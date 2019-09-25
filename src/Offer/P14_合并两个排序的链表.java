package Offer;

/**
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 */
public class P14_合并两个排序的链表 {
    public static void main(String[] args) {

    }

    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode list = null;
        if (list1 == null && list2 == null) {
            return null;
        } else if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }
        if (list1.val < list2.val) {
            list = list1;
            list.next = Merge(list1.next, list2);
        } else {
            list = list2;
            list.next = Merge(list1, list2.next);
        }
        return list;
    }
}
