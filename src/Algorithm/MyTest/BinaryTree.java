package Algorithm.MyTest;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * (1)完全二叉树——若设二叉树的高度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，
 * 第h层有叶子结点，并且叶子结点都是从左到右依次排布，这就是完全二叉树。
 * <p>
 * (2)满二叉树——除了叶结点外每一个结点都有左右子叶且叶子结点都处在最底层的二叉树。
 * <p>
 * (3)平衡二叉树——平衡二叉树又被称为AVL树（区别于AVL算法），它是一棵二叉排序树，且具有以下性质：
 * 它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 * <p>
 * (4)二叉排序树（Binary Sort Tree），又称二叉查找树（Binary Search Tree），亦称二叉搜索树，查找O(logN)
 * 1.若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 2.若右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * 3.左、右子树也分别为二叉排序树；
 * 4.没有键值相等的节点。(本规则可有可无)
 * <p>
 * 性质1：二叉树第i层上的结点数目最多为 2{i-1} (i≥1)。
 * 性质2：深度为k的二叉树至多有2{k}-1个结点(k≥1)。
 * 性质3：包含n个结点的二叉树的高度至少为log2 (n+1)。
 * 性质4：在任意一棵二叉树中，若终端结点的个数为n0，度为2的结点数为n2，则n0=n2+1。
 */
class TreeNode {
    public int data;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

//二叉排序树
public class BinaryTree {
    private static TreeNode root;       //根节点
    private static int count = 0;       //节点数
    private static int leafCount = 0;   //叶子结点数
    private static int maxLen = 0;      //二叉树最远路径

    public BinaryTree() {
        root = null;
    }

    //将data插入到排序二叉树中
    public static void insert(int data) {
        TreeNode treeNode = new TreeNode(data);
        if (root == null) {
            root = treeNode;
        } else {
            TreeNode current = root;
            TreeNode parent;
            while (true) {
                parent = current;
                if (data < current.data) {
                    current = current.left;
                    if (current == null) {
                        parent.left = treeNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = treeNode;
                        return;
                    }
                }
            }
        }
    }

    //将数值输入构建二叉树
    public static void createTree(int[] data) {
        for (int i = 0; i < data.length; i++) {
            insert(data[i]);
        }
    }

    //先序递归遍历
    public static void preOrder(TreeNode curRoot) {
        if (curRoot != null) {
            System.out.print(curRoot.data + "  ");
            preOrder(curRoot.left);
            preOrder(curRoot.right);
        }
    }

    //先序非递归遍历
    public static void preOrder1(TreeNode head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.data + "  ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    //中序递归遍历
    public static void inOrder(TreeNode curRoot) {
        if (curRoot != null) {
            inOrder(curRoot.left);
            System.out.print(curRoot.data + "  ");
            inOrder(curRoot.right);
        }
    }

    //1.当前节点为空，从栈拿一个打印，右移
    //2.不空，节点压栈，向左
    //中序非递归遍历
    public static void inOrder1(TreeNode head) {
        System.out.print("inOrder:  ");
        if (head != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.data + "  ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    //后序递归遍历
    public static void postOrder(TreeNode curRoot) {
        if (curRoot != null) {
            postOrder(curRoot.left);
            postOrder(curRoot.right);
            System.out.print(curRoot.data + "  ");
        }
    }

    //后序非递归遍历
    public static void postOrder1(TreeNode head) {
        System.out.print("postOrder:  ");
        if (head != null) {
            Stack<TreeNode> stack1 = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null) {
                    stack1.push(head.left);
                }
                if (head.right != null) {
                    stack1.push(head.right);
                }
            }
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop().data + "  ");
            }
            System.out.println();
        }
    }

    //二叉树的层次遍历
    public static void layerTranverse() {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.data + "  ");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    //求树中节点数
    public static void countNode(TreeNode treeRoot) {
        if (treeRoot != null) {
            count++;
            preOrder(treeRoot.left);
            preOrder(treeRoot.right);
        }
    }

    //输出所有叶子节点
    public static void printLeaf(TreeNode treeRoot) {

        if (treeRoot != null) {
            if (treeRoot.left == null && treeRoot.right == null) {
                System.out.print(treeRoot.data + "  ");
                leafCount++;
            }
            printLeaf(treeRoot.left);
            printLeaf(treeRoot.right);
        }
    }

    //求二叉树的高度
    public static int getBinaryTreeDepth(TreeNode treeRoot) {
        int h1, h2, h;
        if (treeRoot == null) {
            return 0;
        }
        h1 = getBinaryTreeDepth(treeRoot.left);
        h2 = getBinaryTreeDepth(treeRoot.right);
        h = (h1 > h2 ? h1 : h2) + 1;
        return h;
    }

    //求某节点的双亲
    public static TreeNode getParent(TreeNode treeRoot, TreeNode curNode) {
        if (treeRoot == null) {
            return null;
        }
        if (treeRoot.left == curNode || treeRoot.right == curNode) {
            return treeRoot;
        }

        if (getParent(treeRoot.left, curNode) != null) {
            return getParent(treeRoot.left, curNode);
        }
        return getParent(treeRoot.right, curNode);
    }

    //序列化二叉树
    public static String serializeTreeByPre(TreeNode treeRoot) {
        if (treeRoot == null) {
            return "#!";
        }
        String rs = treeRoot.data + "!";
        rs += serializeTreeByPre(treeRoot.left);
        rs += serializeTreeByPre(treeRoot.right);
        return rs;
    }

    //反序列化二叉树
    public static TreeNode reconTreeByPreString(String str) {
        String[] values = str.split("!");
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < values.length; i++) {
            queue.offer(values[i]);
        }
        return reconPreOrder(queue);
    }

    private static TreeNode reconPreOrder(Queue<String> queue) {
        String value = queue.poll();
        if (value.equals("#")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = reconPreOrder(queue);
        node.right = reconPreOrder(queue);
        return node;
    }

    //查找第k个数
    int index = 0; //计数器

    TreeNode KthNode(TreeNode root, int k) {
        if (root != null) { //中序遍历寻找第k个
            TreeNode node = KthNode(root.left, k);
            if (node != null)
                return node;
            index++;
            if (index == k)
                return root;
            node = KthNode(root.right, k);
            if (node != null)
                return node;
        }
        return null;
    }

    //二叉树节点的最远距离
    public static int findMaxLen(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0;
        int leftMaxLen = findMaxLen(root.left) + 1;
        int righttMaxLen = findMaxLen(root.right) + 1;
        int temp = leftMaxLen + righttMaxLen;
        if (temp > maxLen) {
            maxLen = temp;
        }
        return leftMaxLen > righttMaxLen ? leftMaxLen : righttMaxLen;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        int[] data = {2, 8, 7, 4, 9, 3, 1, 6, 7, 5};
        createTree(data);
        System.out.println(tree.KthNode(root, 10).data);
        System.out.print("二叉树的先序遍历：");
        preOrder(root);
        System.out.println();
        System.out.print("二叉树的中序遍历：");
        inOrder(root);
        System.out.println();
        System.out.print("二叉树的后序遍历：");
        postOrder(root);
        System.out.println();
        System.out.print("二叉树的层次遍历：");
        layerTranverse();
        System.out.println();
        System.out.print("二叉树的最远距离: ");
        System.out.println(findMaxLen(root));
        System.out.println();
        preOrder1(root);
        inOrder1(root);
        postOrder1(root);
        System.out.print("叶子结点： ");
        printLeaf(root);
        System.out.println();
        System.out.println("叶子结点的数目：" + leafCount);
        System.out.println("树的深度为：" + getBinaryTreeDepth(root));
        System.out.println(root.right.left.left.right.data + "的父节点是" + getParent(root, root.right.left.left.right).data);
        String s = serializeTreeByPre(root);
        System.out.println(s);
        TreeNode t = reconTreeByPreString(s);
        postOrder1(t);
    }
}

