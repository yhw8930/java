package Algorithm.MyTest;

/**
 * 求最长回文子串
 * 1、中心扩展算法：回文中心的两侧互为镜像。因此，回文可以从它的中心展开，并且只有 2n - 12n−1 个这样的中心
 * 时间复杂度：O(n^2) 空间复杂度：O(1)
 * 2、Manacher算法：1. 插入#号变为全奇 2. 令RL[i]=min(RL[2*pos-i], MaxRight-i) 3.以i为中心扩展回文串 4.更新MaxRight和pos
 * 时间复杂度：O(n) 空间复杂度：O(1)
 */
public class LongestPalindrome {
    private static String getLongestPalindrome(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < str.length(); i++) {
            int length1 = expandAroundCenter(str, i, i);
            int length2 = expandAroundCenter(str, i, i + 1);
            int length = Math.max(length1, length2);
            if (length > end - start) {
                start = i - (length - 1) / 2;
                end = i + length / 2;
            }
        }
        return str.substring(start, end + 1);

    }

    private static int expandAroundCenter(String str, int left, int right) {
        while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static int getMaxLcpsLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] charArr = manacherString(str);
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
        String str = "tattarrattat";
        System.out.println(getLongestPalindrome(str));
        System.out.println(getMaxLcpsLength(str));
    }
}
