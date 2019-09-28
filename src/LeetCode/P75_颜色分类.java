package LeetCode;

/**
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * <p>
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 */
public class P75_颜色分类 {
    public static void main(String[] args) {
        int[] ints = {2, 0, 2, 1, 1, 0};
        new P75_颜色分类().sortColors(ints);
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            System.out.print(anInt+" ");
        }
    }
    public void sortColors(int[] nums) {
        int less = -1, more = nums.length, p = 0;
        while (p < more) {
            if (nums[p] < 1) {
                swap(nums, ++less, p++);
            } else if (nums[p] > 1) {
                swap(nums, --more, p);
            } else {
                p++;
            }
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
