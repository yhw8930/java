package Offer;

/**
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 */
public class RectCover {
    public static void main(String[] args) {
        System.out.println(new RectCover().RectCover(4));
    }

    public int RectCover(int target) {
        if (target <= 2) return target;
        int cur = 0, f1 = 1, f2 = 2;
        for (int i = 3; i <= target; i++) {
            cur = f1 + f2;
            f1 = f2;
            f2 = cur;
        }
        return cur;
    }
}
