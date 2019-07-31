package Algorithm.MyTest;

import java.util.*;

/**
 * 找出"abcdabceabc"中重复次数最多的字符串且长度不小于3
 */
public class FindMostString {

    public static void main(String[] args) {
        String s = "abcdabceabc";
        System.out.println(findMostString(s));
    }

    public static String findMostString(String str) {
        if (str == null || str.length() < 3) {
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        char[] array = str.toCharArray();
        int maxCount = 0;
        int count = 1;
        boolean flag = true;
        String target = null;
        for (int num = 3; num < (str.length()) / 2; num++) {
            for (int i = 0; i < str.length(); i++) {
                String ss = "";
                for (int j = i; j < num; j++) {
                    ss += String.valueOf(array[j]);
                }
                if (flag) {
                    map.put(ss, count++);
                    flag = false;
                } else if (map.containsKey(ss) && !flag) {
                    map.put(ss, count++);
                }
            }
        }
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                target = entry.getKey();
            }
        }

        return target;
    }
}