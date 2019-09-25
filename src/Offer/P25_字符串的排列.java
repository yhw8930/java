package Offer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,
 * 则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 */
public class P25_字符串的排列 {
    public static void main(String[] args) {

    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str != null && str.length() > 0) {
            fun(str.toCharArray(), 0, res);
            Collections.sort(res);
        }
        return res;
    }

    public void fun(char[] chars, int i, ArrayList<String> list) {
        if (i == chars.length - 1) {
            String s = String.valueOf(chars);
            if (!list.contains(s)) {
                list.add(s);
            }
        } else {
            for (int j = i; j < chars.length; j++) {
                swap(chars, i, j);
                fun(chars, i + 1, list);
                swap(chars, i, j);
            }
        }
    }

    public void swap(char[] cs, int i, int j) {
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }
}
