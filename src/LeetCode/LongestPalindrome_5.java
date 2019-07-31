package LeetCode;

/**
 * Leetcode Hot5
 * 最长回文子串 :给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000
 * 1、暴力解法：3重循环暴力解决   时间复杂度O(n^3),空间复杂度O(1)
 * 2、动态规划：
 * 3、中心扩展算法：回文中心的两侧互为镜像。因此，回文可以从它的中心展开，并且只有 2n−1个这样的中心
 * 时间复杂度：O(n^2) 空间复杂度：O(1)
 * 4、Manacher算法：1. 插入#号变为全奇 2. 令RL[i]=min(RL[2*pos-i], MaxRight-i) 3.以i为中心扩展回文串 4.更新MaxRight和pos
 * 时间复杂度：O(n) 空间复杂度：O(1)
 */
public class LongestPalindrome_5 {
    public String longestPalindrome1(String s) {
        if (s == null || s.length() == 0) return null;
        if (s.length() == 1) {
            return s;
        }
        String target = null;
        int max = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i; j < s.length(); j++) {
                String temp = s.substring(i, j + 1);
                if (fun(temp) && temp.length() > max) {
                    max = temp.length();
                    target = temp;
                }

            }
        }
        return target;
    }

    public boolean fun(String s) {
        for (int i = 0, j = s.length() - 1; i < s.length() / 2; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public String longestPalindrome3(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public char[] manacherString4(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public int getMaxLcpsLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] charArr = manacherString4(str);
        int[] pArr = new int[charArr.length];
        int index = -1;
        int pR = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i != charArr.length; i++) {
            pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > pR) {
                pR = i + pArr[i];
                index = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }


    public static void main(String[] args) {
        LongestPalindrome_5 palindrome = new LongestPalindrome_5();
        String s1 = palindrome.longestPalindrome1("afdfesasdsa");
        System.out.println("暴力解法：" + s1);
        //System.out.println("暴力解法：" + s1);
        String s3 = palindrome.longestPalindrome3("asddsa");//i =2 len =6-1 =5
        System.out.println("中心扩展：" + s3);               //i=2 len = 7 -1=6
        int s4 = palindrome.getMaxLcpsLength("afdfesasdsa");
        System.out.println("Manacher：" + s4);
    }
}
