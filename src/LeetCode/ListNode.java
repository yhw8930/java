package LeetCode;


import java.util.*;

public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }

    /**
     * 该方法与 ListNode 数据结构无关，实际是字母异位词分组题的一个实现。
     * 将每个字符串排序后作为哈希键，异位词的排序结果相同，因此会进入同一分组。
     * 时间 O(ΣLi log Li)，额外空间 O(ΣLi)。从职责划分看，建议将它移到 P49 题解类中。
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
