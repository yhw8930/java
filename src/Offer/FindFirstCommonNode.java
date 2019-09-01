package Offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 输入两个链表，找出它们的第一个公共结点
 */
public class FindFirstCommonNode {
    public static void main(String[] args) {

    }

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1==null||pHead2==null){
            return null;
        }
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        Map<ListNode,Integer> map = new HashMap<>();
        while(p1!=null){
            map.put(p1,1);
            p1=p1.next;
        }
        while(p2!=null){
            if(map.containsKey(p2)){
                return p2;
            }
            p2=p2.next;
        }
        return null;
    }
}
