package Offer;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
 * 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class ReOrderArray {
    public static void main(String[] args) {
        int[] str = {1, 3, 4, 5, 6, 7, 8, 9, 19};
        new ReOrderArray().reOrderArray(str);
        for (int i : str) {
            System.out.print(i + " ");
        }
    }

    public void reOrderArray(int[] array) {
        if (array == null || array.length < 2) return;
        int[] s = new int[array.length];
        int m = 0, n = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                n++;
            }
        }
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                s[m++] = array[i];
            } else {
                s[n++] = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = s[i];
        }
    }
}
