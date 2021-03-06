package Offer;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
 * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 */
public class P6_旋转数组的最小数字 {
    public static void main(String[] args) {
        int[] ints = {3, 4, 5, 1, 2};
        System.out.println(new P6_旋转数组的最小数字().minNumberInRotateArray(ints));
    }

    public int minNumberInRotateArray(int[] array) {
        int low = 0, mid = 0, high = array.length - 1;
        while (low < high) {
            mid = low + ((high - low) >> 1);
            if (array[mid] > array[high]) {
                low = mid + 1;
            } else if (array[mid] < array[high]) {
                high = mid;
            } else {
                high--;
            }
        }
        return array[low];
    }
}
