package Offer;

public class P12_调整数组顺序使奇数位于偶数前面 {
    public static void main(String[] args) {

    }

    public void reOrderArray(int[] array) {
        if (array == null || array.length < 2) return;
        int[] s = new int[array.length];
        int m = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                s[m++] = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) != 1) {
                s[m++] = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = s[i];
        }
    }
}

