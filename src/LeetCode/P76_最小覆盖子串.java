package LeetCode;

/**
 * 给定两个字符串 s 和 t，长度分别是 m 和 n，返回 s 中的 最短窗口 子串，使得该子串包含 t 中的每一个字符（包括重复字符）。如果没有这样的子串，返回空字符串 ""。
 * 测试用例保证答案唯一。
 *
 *
 */
public class P76_最小覆盖子串 {
    /**
     * 滑动窗口：need[c] 初始是 t 对字符 c 的需求量，随字符进入/离开窗口减少/增加；
     * required 是还缺少的字符总数（包含重复字符）。need[c]<0 表示窗口中该字符超额。
     * required==0 时窗口已覆盖 t，不断右移 left 直到刚好失去覆盖，期间更新最短答案。
     * 时间 O(|s|+|t|)，额外空间 O(1)（固定 128 字符表）。代码依赖 t 非空且字符编码小于 128。
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        int[] need = new int[128];  // t 中每个字符需要多少个
        for (char c : t.toCharArray()) {
            need[c]++;
        }
        int required = t.length();  // 还需要匹配多少个字符（未去重计数）
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // 如果这个字符是t需要的（need[c]>0说明还缺），required减少
            if (need[c] > 0) {
                required--;
            }
            need[c]--;  // 无论是否需要，都先扣减（可能变成负数，代表"多余"）
            // 当窗口已经覆盖了t的所有字符，尝试收缩左边界
            while (required == 0) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                char leftChar = s.charAt(left);
                need[leftChar]++;  // 左边字符要移出窗口，先"归还"
                // 如果归还后need[leftChar]变成正数，说明这个字符又变得"不够"了
                if (need[leftChar] > 0) {
                    required++;
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
}
