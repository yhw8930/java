package AlgorithmBasic.class04;

//对于一个数组中的每个数，它左边所有比它小的数的总和，就是这个数的小和。整个数组的小和就是所有数的小和之和。
//
//举个例子，对于数组 [1, 3, 4, 2, 5]：
//
//1 的左边没有数，小和是 0
//3 的左边有 1 比它小，小和是 1
//4 的左边有 1, 3 比它小，小和是 1 + 3 = 4
//2 的左边有 1 比它小，小和是 1
//5 的左边有 1, 3, 4, 2 比它小，小和是 1 + 3 + 4 + 2 = 10
//所以整个数组的小和就是 0 + 1 + 4 + 1 + 10 = 16。

public class Code02_SmallSum {
	//算法核心思想是：在对数组进行归-并排序的过程中，计算产生的小和, 将时间复杂度优化到了 O(N * logN)。
	//分解 (Divide)： process 函数递归地将数组分成两半，直到每个子数组只包含一个元素。这和归并排序的分解步骤完全一样。
	//合并与计算 (Merge & Conquer)： 这是算法的关键。当合并两个已经排好序的子数组（左组和右组）时，我们可以高效地计算出“跨左右两组”的小和。
	//在 merge 函数中，当比较左组的数 arr[p1] 和右组的数 arr[p2] 时：
	//如果 arr[p1] < arr[p2]，这意味着右组中从 arr[p2] 到结尾的所有数都比 arr[p1] 大。因此，arr[p1] 会为这些数（共 r - p2 + 1 个）都贡献一次小和。所以，在这一步产生的小和就是 arr[p1] * (r - p2 + 1)。
	//如果 arr[p1] >= arr[p2]，则 arr[p1] 不会为 arr[p2] 产生小和（因为它不比 arr[p2] 小），所以小和贡献为 0。
	//通过在归并排序的 merge 过程中增加这个计算步骤，smallSum 可以在排序的同时，高效地计算出整个数组的小和。
	public static int smallSum(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]既要排好序，也要求小和返回
	// 所有merge时，产生的小和，累加
	// 左 排序   merge
	// 右 排序  merge
	// merge
	public static int process(int[] arr, int l, int r) {
		if (l == r) {
			return 0;
		}
		// l < r
		int mid = l + ((r - l) >> 1);
		return 
				process(arr, l, mid) 
				+ 
				process(arr, mid + 1, r) 
				+ 
				merge(arr, l, mid, r);
	}

	public static int merge(int[] arr, int L, int m, int r) {
		int[] help = new int[r - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = m + 1;
		int res = 0;
		while (p1 <= m && p2 <= r) {
			res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
			help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= m) {
			help[i++] = arr[p1++];
		}
		while (p2 <= r) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return res;
	}

	// for test
	public static int comparator(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int res = 0;
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				res += arr[j] < arr[i] ? arr[j] : 0;
			}
		}
		return res;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			if (smallSum(arr1) != comparator(arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
