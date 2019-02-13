package Math;

/**
 * 把一个数右移k位，时间复杂度位O(n)
 */
public class ReverseArray {
    public static void reverse(int[] a,int begin,int end){
        for (;begin<end;begin++,end--){
            int temp=a[begin];
            a[begin]=a[end];
            a[end]=temp;
        }
    }
    public static void shift_k(int[] a,int k){
        int len=a.length;
        k=k%len;//为了防止k比n大，右移k位跟右移k%n位的结果是一样的
        reverse(a,len-k,len-1);
        reverse(a,0,len-k-1);
        reverse(a,0,len-1);
    }

    public static void main(String[] args) {
        int[] array={1,2,3,4,5,6,7,8};
        shift_k(array,3);
        for (int i : array) {
            System.out.print(i+" ");
        }
    }
}
