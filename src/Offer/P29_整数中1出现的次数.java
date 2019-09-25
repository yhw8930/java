package Offer;

/**
 * ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）
 */
public class P29_整数中1出现的次数 {
    public static void main(String[] args) {

    }

    public int NumberOf1Between1AndN_Solution(int n) {
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        for (int i = 0; i <= n; i++) {
            buffer.append(i);
        }
        for (int i = 0; i < buffer.length(); i++) {
            if (buffer.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }
}
