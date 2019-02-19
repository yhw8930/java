package Algorithm.MyTest;

/**
 * 字符串匹配算法
 * 1、BF 简单模式匹配算法 O(N*M)
 * 2、KMP 时间复杂度O(N + M)
 * 3、BM 时间复杂度O(N + M)
 */
public class MatchString {
    public static int getIndexOfByBF(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }
        char[] s1 = s.toCharArray();
        char[] s2 = m.toCharArray();
        int i = 0, j = 0;
        while (i < s.length() && j < m.length()) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return j == m.length() ? i - j : -1;
    }

    public static int getIndexOfByKmp(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }
        char[] s1 = s.toCharArray();
        char[] s2 = m.toCharArray();
        int i = 0, j = 0;
        int[] next = getNext(m);
        while (i < s.length() && j < m.length()) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else if (next[j] == -1) {
                i++;
            } else {
                j = next[j];
            }
        }
        return j == m.length() ? i - j : -1;
    }

    public static int[] getNext(String m) {
        if (m.length() == 1) {
            return new int[]{-1};
        }
        char[] s = m.toCharArray();
        int[] next = new int[m.length()];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < m.length()) {
            if (next[pos - 1] == next[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String match = "ababa";
        System.out.println(getIndexOfByBF(str, match));
        System.out.println(getIndexOfByKmp(str, match));
    }
}
