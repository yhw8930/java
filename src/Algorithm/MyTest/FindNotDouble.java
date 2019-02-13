package Algorithm.MyTest;

/**
 * 找出数组中只出现一次的数字；数组中除一之外，都是2
 * 时间复杂度O(N)，空间复杂度为O(1)
 */
public class FindNotDouble {
    public static int findNotDouble(int[] a){
        int result=a[0];
        for (int i = 1; i < a.length; i++) {
            result^=a[i];
        }
        return result;
    }
    public static void main(String[] args) {
        int[] array={1,2,3,2,4,3,5,4,1};
        System.out.println(findNotDouble(array));
    }
}
