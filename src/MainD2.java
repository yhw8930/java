import java.util.*;

public class MainD2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[] nums = new int[m];

        for (int i = 0; i <nums.length ; i++) {
            nums[i] = sc.nextInt();
        }
        int sum  =0;
        int min = Integer.MAX_VALUE;
        int i=0,j=0,start=0,end=0;
        for (;j<nums.length;j++) {
            if (sum>0){
                i=j;
                sum=nums[j];
            }else {
                sum+=nums[j];
            }
            if (sum<min){
                start=i;
                end=j;
                if (end-start>=n){
                    min=sum;
                }

            }
        }
        System.out.println(min);
    }

}
