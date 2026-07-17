package LeetCode;

import java.util.*;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 *
 */
public class P49_字母异位词分组 {
    /**
     * 解法一：将每个字符串的字符排序后作为哈希表的分组键。
     * 字母异位词包含完全相同的字符及出现次数，因此排序后必然得到相同序列；
     * 反过来，排序键相同也说明两个原字符串互为字母异位词。
     * 遍历数组时把原字符串加入对应键的列表，最后返回哈希表中的所有分组。
     * 当 strs 为 null 时返回空列表；方法假设数组内的字符串本身不为 null。
     *
     * 时间复杂度：O(ΣLi log Li)，Li 为第 i 个字符串的长度；若最大长度为 K，最坏为 O(NK log K)。
     * 额外空间复杂度：O(NK)，用于分组键、哈希表和结果列表。
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null) {
            return new ArrayList<>();
        }
        Map<String, List<String>> m = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String k = Arrays.toString(chars);
            List<String> list = m.getOrDefault(k, new ArrayList<>());
            list.add(strs[i]);
            m.put(k, list);
        }
        return new ArrayList<>(m.values());
    }

    /**
     * 解法二：用 26 位字母频次代替排序结果作为分组特征。
     * 对每个字符串统计 a 到 z 的出现次数，再按字母顺序把“字母 + 次数”拼成键。
     * 两个字符串的键相同，当且仅当每种字母的出现次数都相同，因此它们应该进入同一组。
     * 该实现假设 strs 不为 null，且所有字符都是小写英文字母；
     * 否则 str.charAt(i) - 'a' 可能产生越界下标。
     *
     * 时间复杂度：O(ΣLi + 26N)，若最大长度为 K，可写为 O(NK)。
     * 额外空间复杂度：O(NK)，用于分组键、哈希表和结果列表；每次计数额外使用 O(26) 空间。
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] counts = new int[26];
            int length = str.length();
            for (int i = 0; i < length; i++) {
                counts[str.charAt(i) - 'a']++;
            }
            // 将每个出现次数大于 0 的字母和出现次数按顺序拼接成字符串，作为哈希表的键
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
