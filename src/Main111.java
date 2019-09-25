import java.util.*;

public class Main111 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        int sum =0;
        for (int i = 0; i <n ; i++) {
            sum+=nums[i];
        }
        int m = sum/2;
        int[] dp = new int[m+1];

        for (int i = 0; i < nums.length; i++) {
            for (int j = m; j >=1 ; j--) {
                if (j>=nums[i]){
                    dp[j] = Math.max(dp[j-nums[i]]+nums[i],dp[j]);
                }
            }
        }
        int res = Math.abs(2*dp[m]-sum);
        System.out.println(res);

    }
}
/*String[] s = ip.split("\\.");
        if (s.length==4){
            int n = -1;
            boolean f = false;
            for (int i = 0; i < 4; i++) {
                n = Integer.parseInt(s[i]);
                if (n<0||n>255){
                    System.out.println("Neither");
                    f = true;
                    break;
                }
            }
            if (!f){
                System.out.println("IPv4");
            }
        }else {
            String[] s1 = ip.split(":");

            if (s1.length==8){

                long n = -1;
                boolean f = true;
                for (int i = 0; i < 8; i++) {
                    n = Long.parseLong(s1[i],16);
                    if (n<0){
                        System.out.println("Neither");
                        f = false;
                    }
                }
                if (f){
                    System.out.println("IPv6");
                }
            }else {
                System.out.println("Neither");
            }

        }*/