package LeetCode;

import java.util.*;
//1(2(3,4(,5)),6(7,)) =>中序遍历 3245176
public class P_小米2重建二叉树 {
    public static void main(String[] args) {
        System.out.println(solution("1(2(3,4(,5)),6(7,))"));
    }

    static String solution(String input) {
        if(input==null||input.length()<2){
            return input;
        }
        Stack<Character> number=new Stack<Character>();
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<input.length();i++){
            char c=input.charAt(i);
            if(c>='0'&&c<='9'){
                char c1=input.charAt(i+1);
                if(c1==','){
                    builder.append(c);
                    builder.append(number.pop());
                    i++;
                    continue;
                }
                number.push(c);
            }
            if(c=='('){
                char c1=input.charAt(i+1);
                if(c1==','){
                    builder.append(number.pop());
                    i++;
                }
            }
            if(c==')'){
                builder.append(number.isEmpty()?"":number.pop());
            }
        }
        return builder.toString();
    }
}
