package Algorithm.MyTest;

/**
 * 找出数组中第K个最小的数
 * 采用快速排序，分而治之的思想，根据主元，每次Partiton以主元为轴，比它小的数在左边，比它大的数在右边，
 * 判断tmp的位置，如果它的位置为k-1，那么它就是第k小的数，如果它的位置小于k-1，那么第k小一定在数组的右半部分，
 * 采用递归方法在数组右半部分继续查找；否则第k小在数组左半部分，采用递归方法在左半部分数组中继续查找
 */
public class Partition {
    public static int partition(int[] a,int low,int high,int k){
        int i,j,temp;
        if (low>high){
            return Integer.MIN_VALUE;
        }
        i=low+1;
        j=high;
        temp=a[i];
        while (i<j) {
            while (i < j && a[j] >= temp) {
                j--;
            }
            if (i < j) {
                a[i++] = a[j];
            }
            while (i < j && a[j] < temp) {
                i++;
            }
            if (i < j) {
                a[j--] = a[i];
            }
        }
            a[i] = temp;
            if (i + 1 == k)
                return temp;
            else if (i + 1 > k)
                return partition(a, low, i - 1, k);
            else
                return partition(a, i + 1, high, k);

    }

    public static int getKMin(int array[],int k){
        if(array==null)
            return Integer.MIN_VALUE;
        if(array.length<k)
            return Integer.MIN_VALUE;
        return partition(array,0,array.length-1,k);
    }
    public static void main(String[] args) {
        int a[]={1,5,2,6,8,0,6};
        int kMin=getKMin(a,6);
        System.out.println(kMin);
    }
}
