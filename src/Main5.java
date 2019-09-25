import java.util.Scanner;

public class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        int[][] dp = new int[n][n];
        dp[0][0] = arr[0][0];
        for (int i = 1; i < n; i++) {
            int temp = arr[i][0] + dp[i - 1][0];
            if (temp > arr[i][0])
            dp[i][0]=temp;
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = Math.max(arr[0][j],arr[0][j]+dp[0][j-1]);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j]=Math.max(Math.max(arr[i][j]+dp[i][j-1],arr[i][j]+dp[i-1][j]),arr[i][j]);
            }
        }
        System.out.println(dp[n-1][n-1]);
    }
}
