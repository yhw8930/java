package Offer;

/**
 * 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？为此他特别数了一下1~13中包含1的数字有1、10、11、12、13
 * 因此共出现6次,但是对于后面问题他就没辙了。ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数
 * （从1 到 n 中1出现的次数）
 */
public class NumberOf1Between1AndN {

    public static void main(String[] args) {
        System.out.println(new NumberOf1Between1AndN().NumberOf1Between1AndN_Solution(198));
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
