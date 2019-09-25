import java.util.*;

public class Main333 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n==0){
            System.out.println(0);
        }else {
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            Arrays.sort(nums);
            int len = nums[n-1]-nums[0];
            int[][] dp = new int[n][len + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <len+1 ; j++) {
                    dp[i][j] = 1;
                }
            }
            int d ,longest = 1;
            for (int i = 1; i <n ; i++) {
                for (int j = i-1; j >=0 ; j--) {
                    d=nums[i]-nums[j];
                    dp[i][d] =dp[j][d]+1;
                    longest = Math.max(longest,dp[i][d]);
                }
            }
            System.out.println(longest);
        }

    }
}
