package Algorithm.MyTest;

import java.util.Hashtable;

class Node {
    //public方法不需要get、set
    public Node next = null;
    public int data;

    public Node(int data) {
        this.data = data;
    }
}

public class LinkedList {
    Node head = null; //链表头的引用

    /**
     * 添加一个节点
     *
     * @param node 节点data
     */
    public void addNode(int node) {
        Node newNode = new Node(node);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    /**
     * 求链表长度
     *
     * @return length
     */
    public int getLength() {
        int length = 0;
        Node temp = head;
        while (temp != null) {
            temp = temp.next;
            length++;
        }
        return length;
    }

    /**
     * 删除下标为index的节点
     *
     * @param index 删除下标
     * @return true 成功；false 失败
     */
    public Boolean deleteNode(int index) {
        //删除元素位置不合理
        if (index < 0 || index > getLength()) {
            return false;
        }
        //删除链表第一个元素
        if (index == 1) {
            head = head.next;
            return true;
        }
        int i = 2;
        Node temp = head;
        while (temp.next != null) {
            if (index == i) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
            i++;
        }
        return true;
    }

    /**
     * 冒泡排序
     *
     * @return 头结点head
     */
    public Node orderList() {
        int temp = 0;
        if (head.next == null) {
            return head;
        }
        for (Node p = head; p.next != null; p = p.next) {
            for (Node q = head; q.next != null; q = q.next) {
                if (q.data > q.next.data) {
                    temp = q.next.data;
                    q.next.data = q.data;
                    q.data = temp;
                }
            }
        }
        return head;
    }

    /**
     * 获取值对应的下标
     *
     * @param data 值
     * @return 值对应的下标
     */
    public int getIndex(int data) {
        if (head == null) {
            System.out.println("List is null! return -1!");
            return -1;
        }
        int index = 1;
        Node temp = head;
        while (temp != null) {
            if (temp.data == data) {
                return index;
            }
            temp = temp.next;
            index++;
        }
        System.out.println("Can't find! return -1!");
        return -1;
    }

    /**
     * 打印整个链表
     */
    public void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * 从尾到头输出单链表
     *
     * @param head 头结点
     */
    public void printListReversely(Node head) {
        if (head != null) {
            printListReversely(head.next);
            System.out.println(head.data);
        }
    }

    /**
     * 遍历链表，把遍历的值存储到一个Hashtable中，在遍历过程中，若当前访问的值在Hashtable中已经存在，则说明这个数据是重复的，因此就可以删除。
     * 优点：时间复杂度较低O(n)
     * 缺点：在遍历过程中需要额外的存储空间来保存已遍历过的值
     * 从链表中删除重复数据
     *
     * @param head 节点
     */
    public void deleteDuplecate_1(Node head) {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        Node temp = head;
        Node p = null;
        while (temp != null) {
            if (hashtable.containsKey(temp.data)) {
                p.next = temp.next;
            } else {
                hashtable.put(temp.data, 1);
                p = temp;
            }
            temp = temp.next;
        }

    }

    /**
     * 对链表进行双重循环遍历，外循环正常遍历链表，假设外循环当前遍历的结点为cur，内循环从cur开始遍历，若碰到与cur所指向结点值相同，则删除这个重复结点
     * 优点：不需要额外的存储空间
     * 缺点：时间复杂度较高O(n^2)
     *
     * @param head 节点
     */
    public void deleteDuplecate_2(Node head) {
        Node p = head;
        while (p != null) {
            Node q = p;
            while (q.next != null) {
                if (p.data == q.next.data) {
                    q.next = q.next.next;
                } else {
                    q = q.next;
                }
            }
            p = p.next;
        }
    }

    /**
     * 找出单链表中倒数第k个元素
     *
     * @param node 头结点
     * @param k    倒数第k个节点
     * @return node
     */
    public Node findElem(Node node, int k) {
        if (k < 1) {
            return null;
        }
        Node p1 = head;
        Node p2 = head;
        for (int i = 0; i < k - 1 && p1 != null; i++) {
            p1 = p1.next;
        }
        if (p1 == null) {
            System.out.println("k不合法！");
            return null;
        }
        while (p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    /**
     * 遍历反转链表
     *
     * @param node 头结点
     * @return 新的头结点
     */
    public Node reverseList1(Node node) {
        if (node == null) {
            return null;
        }
        Node pRrev = null;
        Node newNode = null;
        Node pNode = node;
        while (pNode != null) {
            Node pNext = pNode.next;
            if (pNext == null) {
                newNode = pNode;
            }
            pNode.next = pRrev;
            pRrev = pNode;
            pNode = pNext;
        }
        return newNode;
    }
    public static Node reverseList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 递归反转链表
     *
     * @param node 头结点
     * @return 新的头结点
     */
    public Node reverseList2(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node newNode = reverseList2(node.next);
        node.next.next = node;
        node.next = null; //防止环链
        return newNode;
    }

    /**
     * 寻找单链表的中点节点
     *
     * @param head 头结点
     * @return 中间节点
     */
    public Node searchMid(Node head) {
        Node p = head;
        Node q = head;
        while (p != null && p.next != null) {
            p = p.next.next;
            q = q.next;
        }
        return q;
    }

    /**
     * 检测链表是否有环
     * @param head 头节点
     * @return true 有环；false 无环
     */
    public boolean isLoop(Node head) {
        Node fast = head;
        Node slow = head;
        if (fast == null) {
            return false;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return !(fast.next == null || fast == null);
    }

    /**
     * 找到链表环的入口
     * @param head 头结点
     * @return 环节点
     */
    public Node findLoopPort(Node head){
        Node fast=head;
        Node slow=head;
        while (fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
            if (fast==slow){
                break;
            }
        }
        if (fast==null||fast.next==null){
            return null;
        }
        slow=head;
        while (slow!=fast){
            fast=fast.next;
            slow=slow.next;
        }
        return slow;
    }

    /**
     * 不知道头指针的情况下删除指定节点
     * @param node 被删除节点
     * @return true 删除成功；false 删除失败
     */
    public boolean deleteNode(Node node){
        if (node==null||node.next==null){
            return false;
        }
        node.data=node.next.data;
        node.next=node.next.next;
        return true;
    }

    /**
     * 判断两个链表是否相交，若相交，那么它们一定有相同的尾节点
     * @param head1 头结点1
     * @param head2 头结点2
     * @return true false
     */
    public boolean isIntersect(Node head1,Node head2){
        if (head1==null||head2==null){
            return false;
        }
        Node tail1=head1;
        while (tail1.next!=null){
            tail1=tail1.next;
        }
        Node tail2=head2;
        while (tail2.next!=null){
            tail2=tail2.next;
        }
        return tail1==tail2;
    }

    /**
     * 找两个相交链表的第一个节点
     * @param head1 头结点1
     * @param head2 头结点1
     * @return 第一个交点
     */
    public Node getFirstMeetNode(Node head1,Node head2){
        if (head1==null||head2==null){
            return null;
        }
        Node tail1=head1;
        int length1=1;
        while (tail1.next!=null){
            tail1=tail1.next;
            length1++;
        }
        Node tail2=head2;
        int length2=1;
        while (tail2.next!=null){
            tail2=tail2.next;
            length2++;
        }
        Node t1=head1;
        Node t2=head2;
        if (length1>length2){
            int d=length1-length2;
            while (d!=0){
                t1=t1.next;
                d--;
            }
        }else {
            int d=length2-length1;
            while (d!=0){
                t2=t2.next;
                d--;
            }
        }
        while (t1!=t2){
            t1=t1.next;
            t2=t2.next;
        }
        return t1;
    }
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addNode(6);
        list.addNode(2);
        list.addNode(55);
        list.addNode(1);
        list.addNode(9);
        //list.deleteNode(list.head.next.next);
        list.printListReversely(list.head);
        //System.out.println(list.searchMid(list.head).data);
        //System.out.println(list.isLoop(list.head));
        //list.deleteDuplecate_2(list.head);
        //Algorithm.MyTest.Node node1 = list.reverseList1(list.head);
        // list.printList(node1);
        // Algorithm.MyTest.Node node2 = list.reverseList2(node1);
        // list.printList(node2);
        //list.printList(list.head);
        //System.out.println(list.findElem(list.head,3).data);
        /*System.out.println("list length:"+list.getLength());
        list.orderList();
        list.deleteNode(3);
        list.printList();
        System.out.println(list.getIndex(55));*/
    }
}
