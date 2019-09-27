package LeetCode;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 */
public class P20_有效的括号 {
    public static void main(String[] args) {
        P20_有效的括号 p = new P20_有效的括号();
        System.out.println(p.isValid("{[]}"));
    }

    public boolean isValid(String s) {
        if (s == "") return true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char pop = stack.pop();
                if (ch == ')' && pop != '(') {
                    return false;
                } else if (ch == '}' && pop != '{') {
                    return false;
                } else if (ch == ']' && pop != '[') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
