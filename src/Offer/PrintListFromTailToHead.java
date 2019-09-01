package Offer;

import java.util.ArrayList;

/**
 * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
 */
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

public class PrintListFromTailToHead {
    public static void main(String[] args) {

    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode){
        ArrayList<Integer> list = new ArrayList<>();
        while (listNode!=null){
            list.add(0,listNode.val);
            listNode= listNode.next;
        }
        return list;
    }
    /*ArrayList<Integer> list = new ArrayList<>();
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.val);
        }
        return list;
    }*/

}
