package LeetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 */
public class P6_Z字形变换 {
    public static void main(String[] args) {
        System.out.println(new P6_Z字形变换().convert("LEETCODEISHIRING", 3));
    }

    /**
     * 为每一行建立 StringBuilder，curRows 指向当前行，flag 表示向下还是向上移动。
     * 到达第一行或最后一行时翻转方向，将字符按 Z 形路径分配后逐行拼接。
     * 时间复杂度：O(N)；额外空间：O(N)。
     */
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> list = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            list.add(new StringBuilder());
        }
        int curRows = 0;
        boolean flag = false;
        for (char c : s.toCharArray()) {
            list.get(curRows).append(c);
            if (curRows == 0 || curRows == numRows - 1) flag = !flag;
            curRows += flag ? 1 : -1;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : list) {
            res.append(row);
        }
        return res.toString();
    }
}
