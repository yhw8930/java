package Offer;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323
 */
public class PrintMinNumber {
    public static void main(String[] args) {

    }

    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        String[] s = new String[numbers.length];
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < numbers.length; i++) {
            s[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(s, (o1, o2) -> {
            String s1 = o1 + o2;
            String s2 = o2 + o1;
            return s1.compareTo(s2);
        });
        for (String ss : s) {
            buffer.append(ss);
        }
        return buffer.toString();
    }
}
