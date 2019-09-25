package Test;

import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();

        if (s1==null||s1.length()==0){
            System.out.println("");
        }else {
            char[] inorder = s1.toCharArray();
            char[] postOrder = s2.toCharArray();
            TreeNode root = main.getTree(inorder, postOrder);
            StringBuffer buffer = new StringBuffer();
            main.preOrder(root,buffer);
            System.out.println(buffer.toString());
        }


    }

    public TreeNode getTree(char[] inOrder, char[] postOrder) {
        TreeNode root = fun(inOrder, 0, inOrder.length - 1, postOrder, 0, postOrder.length - 1);
        return root;
    }

    public TreeNode fun(char[] inOrder, int inl, int inr, char[] postOrder, int postl, int postr) {
        if (inl > inr || postl > postr) {
            return null;
        }
        char val = postOrder[postr];
        int count = 0;
        for (int i = inl; i < inr + 1; i++) {
            if (inOrder[i] == val) {
                count = i;
                break;
            }
        }
        TreeNode root = new TreeNode(val);
        root.left = fun(inOrder, inl, count - 1, postOrder, postl, count - 1 - inl + postl);
        root.right = fun(inOrder, count + 1, inr, postOrder, postr + count - inr, postr - 1);
        return root;
    }

    public void preOrder(TreeNode curRoot, StringBuffer buffer) {
        if (curRoot != null) {
            buffer.append(curRoot.val);
            preOrder(curRoot.left,buffer);
            preOrder(curRoot.right,buffer);
        }
    }
}
class TreeNode {
    char val;
    TreeNode left;
    TreeNode right;

    TreeNode(char x) {
        val = x;
    }
}
//dgbaechf gbdehfca