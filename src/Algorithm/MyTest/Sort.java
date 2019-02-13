package Algorithm.MyTest;

public class Sort {
    //交换两个数
    public static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 冒泡排序稳定，时间复杂度O(n^2),最小时间代价O(n),空间复杂度O(1)
     *
     * @param arr 引用
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        boolean flag = true;
        for (int i = 0; i < arr.length - 1 && flag; i++) {
            flag = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;
                }
            }
        }
    }

    /**
     * 选择排序不稳定，时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr 数组引用
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[index] > arr[j]) {
                    index = j;
                }
            }
            swap(arr, index, i);
        }
    }

    /**
     * 插入排序稳定，时间复杂度为O(n^2),空间复杂度O(1)
     *
     * @param arr 数组引用
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    /**
     * 归并排序稳定，时间复杂度O(Nlog(N)),空间复杂度O(N)
     *
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
    }

    //经典快排，最坏情况时间复杂度O(N^2)
    public static void quickSort1(int[] arr, int l, int r) {
        //需要一个函数预处理特殊情况，本函数不能写
        /*if (arr==null||arr.length<2) {
            return;
        }*/
        if (l < r) {
            int i = l;
            int j = r;
            int key = arr[l]; //这里可优化
            while (i < j) {
                while (i < j && arr[j] >= key) {
                    j--;
                }
                if (i < j) {
                    arr[i++] = arr[j];
                }
                while (i < j && arr[i] < key) {
                    i++;
                }
                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = key;
            quickSort1(arr, l, i - 1);
            quickSort1(arr, i + 1, r);
        }
    }

    /**
     * 快速排序，时间复杂度O(N*logN)，额外空间复杂度O(logN)
     *
     * @param arr 数组引用
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
            int[] p = partition(arr, l, r);
            quickSort(arr, l, p[0] - 1);
            quickSort(arr, p[1] + 1, r);
        }
    }

    //荷兰国旗变种
    public static int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;
        while (l < more) {
            if (arr[l] < arr[r]) {
                swap(arr, ++less, l++);
            } else if (arr[l] > arr[r]) {
                swap(arr, --more, l);
            } else {
                l++;
            }
        }
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }

    public static void main(String[] args) {
        int[] arr = new int[]{54, 33, 65, 21, 2, 97, 43, 4, 56, 43};
        //System.out.print("bubbleSort:");
        //bubbleSort(arr);
        //System.out.print("selectSort:");
        //selectSort(arr);
        //insertSort(arr);
        //System.out.print("insertSort:");
        //System.out.print("mergeSort:");
        //mergeSort(arr);
        System.out.print("quickSort:");
        quickSort(arr);
        for (int i : arr) {
            System.out.print(" " + i);
        }

    }
}
