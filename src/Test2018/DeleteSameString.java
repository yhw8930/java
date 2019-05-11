package Test2018;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 */
public class DeleteSameString {
    public static void main(String[] args) {
        int[] arr = new int[]{3,32,321};
        System.out.println(new DeleteSameString().PrintMinNumber(arr));
    }

    public String PrintMinNumber(int [] numbers) {
        if (numbers == null || numbers.length==0){
            return "";
        }
        int len = numbers.length;
        String[] s = new String[len];
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            s[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(s, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String ss1 = s1+s2;
                String ss2 = s2+s1;
                return ss1.compareTo(ss2);
            }
        });
        for (int i = 0; i < len; i++) {
            sb.append(s[i]);
        }
        return sb.toString();
    }
}
