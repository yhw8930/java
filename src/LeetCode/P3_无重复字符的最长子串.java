package LeetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
 * 1.暴力法
 * 时间复杂度：O(n^3) ; 空间复杂度：O(n)
 * 2.滑动窗口
 * 时间复杂度：O(n) ; 空间复杂度：O(n)
 * 3.优化的滑动窗口（HashMap）
 * 时间复杂度：O(n) ; 空间复杂度：O(n)
 */
public class P3_无重复字符的最长子串 {

    /**
     * 解法一：暴力枚举所有子串 [i, j)，再用 allUnique 逐个检查子串中是否存在重复字符。
     * 外层两重循环枚举 O(N^2) 个子串，每次检查最多需要 O(N) 时间，
     * 检查通过后用 j - i 更新最长长度。该实现假设 s 不为 null。
     *
     * 时间复杂度：O(N^3)。
     * 额外空间复杂度：O(N)，用于检查子串时的 HashSet。
     */
    public static int lengthOfLongestSubstring1(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j <= n; j++)
                if (allUnique(s, i, j)) ans = Math.max(ans, j - i);
        return ans;
    }

    public static boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if (set.contains(ch)) return false;
            set.add(ch);
        }
        return true;
    }

    /**
     * 解法二：HashSet 滑动窗口。窗口 [i, j) 中始终保存一段不含重复字符的子串，
     * set 保存窗口内的所有字符。若 s[j] 不在集合中，就将它加入并右移 j，再更新答案；
     * 若 s[j] 已经存在，就从左边逐个移除字符并右移 i，直到可以安全加入 s[j]。
     * 每个字符最多进入和离开窗口各一次。该实现假设 s 不为 null。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(min(N, |Σ|))，|Σ| 为字符集大小。
     */
    public static int lengthOfLongestSubstring2(String s) {
        int ans = 0, i = 0, j = 0;
        int n = s.length();
        Set<Character> set = new HashSet<>();
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }
    //abcddb
    /**
     * 解法三：用 HashMap 记录每个字符最近一次出现位置的下一个下标，使左边界可以一次跳过重复字符。
     * 遍历到右边界 j 时，若当前字符以前出现过，就令 i = max(该字符上次位置的下一个下标, i)。
     * Math.max 非常关键：如果上次出现位置已在当前窗口左侧，不能让 i 向左回退。
     * 此时 [i, j] 始终无重复字符，其长度为 j - i + 1。该实现假设 s 不为 null。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(min(N, |Σ|))。
     */
    public static int lengthOfLongestSubstring3(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    /**
     * 解法四：与解法三等价，但 HashMap 保存的是字符最近一次出现的真实下标。
     * 只有当该下标大于或等于当前左边界 i 时，重复字符才位于当前窗口内，
     * 此时将 i 直接移到上次出现位置的下一位。如果上次位置在窗口外，左边界保持不变。
     * 因此窗口 [i, j] 始终无重复字符，每次用 j - i + 1 更新答案。
     * 该实现假设 s 不为 null。
     *
     * 时间复杂度：O(N)。
     * 额外空间复杂度：O(min(N, |Σ|))。
     */
    public static int lengthOfLongestSubstring4(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))&&(map.get(s.charAt(j))>=i)) {
                i = map.get(s.charAt(j))+1;
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j);
        }
        return ans;
    }


    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring4("abcddb"));
    }
}
