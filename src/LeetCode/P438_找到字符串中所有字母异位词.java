package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *
 *
 */
public class P438_找到字符串中所有字母异位词 {
    /**
     * 解法：固定长度滑动窗口。字母异位词的长度和每种字母的出现次数都完全相同，
     * 因此只需在 s 中维护一个长度为 p.length() 的窗口，比较窗口频次和 p 的频次。
     * need 记录 p 中 26 个小写字母的出现次数，window 记录当前窗口的出现次数。
     * 先构造第一个窗口，之后每向右移动一位，就将新字符计数加一、离开窗口的字符计数减一。
     * Arrays.equals(need, window) 成立时，当前窗口就是 p 的字母异位词，记录其起始下标。
     * 该实现假设 s 和 p 非 null、p 非空，且两个字符串都只包含小写英文字母。
     *
     * 时间复杂度：O(|s| + |p|)，每次比较长度固定为 26 的数组。
     * 额外空间复杂度：O(26)，不计返回结果时可视为 O(1)。
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length(), m = p.length();
        if (n < m) return result;

        int[] need = new int[26];   // p 中每个字符出现的次数
        int[] window = new int[26]; // 当前窗口中每个字符出现的次数

        for (char c : p.toCharArray()) {
            need[c - 'a']++;
        }

        // 先填满第一个窗口（长度为m）
        for (int i = 0; i < m; i++) {
            window[s.charAt(i) - 'a']++;
        }

        if (Arrays.equals(need, window)) {
            result.add(0);
        }

        // 滑动窗口：每次右边进一个字符，左边出一个字符
        for (int i = m; i < n; i++) {
            window[s.charAt(i) - 'a']++;           // 新字符进入窗口
            window[s.charAt(i - m) - 'a']--;        // 最左边的字符离开窗口

            if (Arrays.equals(need, window)) {
                result.add(i - m + 1);  // 窗口起始下标
            }
        }

        return result;
    }

    /**
     * 解法：当前实现与 findAnagrams 完全相同，仍然是长度固定为 p.length() 的滑动窗口。
     * need 和 window 分别保存 p 与当前窗口的 26 位字母频次；
     * 窗口每次右移时加入一个新字符、移除一个旧字符，两个频次数组相等就记录窗口起点。
     * 该方法并没有提供与方法一不同的算法或复杂度优势，目前属于重复实现。
     * 同样假设 s 和 p 非 null、p 非空，且字符范围为 a 到 z。
     *
     * 时间复杂度：O(|s| + |p|)。
     * 额外空间复杂度：O(26)，不计返回结果时可视为 O(1)。
     */
    public List<Integer> findAnagrams2(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length(), m = p.length();
        if (n < m) return result;

        int[] need = new int[26];   // p 中每个字符出现的次数
        int[] window = new int[26]; // 当前窗口中每个字符出现的次数

        for (char c : p.toCharArray()) {
            need[c - 'a']++;
        }

        // 先填满第一个窗口（长度为m）
        for (int i = 0; i < m; i++) {
            window[s.charAt(i) - 'a']++;
        }

        if (Arrays.equals(need, window)) {
            result.add(0);
        }

        // 滑动窗口：每次右边进一个字符，左边出一个字符
        for (int i = m; i < n; i++) {
            window[s.charAt(i) - 'a']++;           // 新字符进入窗口
            window[s.charAt(i - m) - 'a']--;        // 最左边的字符离开窗口

            if (Arrays.equals(need, window)) {
                result.add(i - m + 1);  // 窗口起始下标
            }
        }

        return result;
    }
}
