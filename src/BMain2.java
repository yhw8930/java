import java.util.*;
//829
public class BMain2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int num = 1;
        int n = 2 * N;
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                int end = (n / i + i - 1) / 2;
                int start = end - n / i + 1;
                if ((start + end) * (end - start + 1) == n) {
                    num++;
                }
            }
        }
        System.out.println(num);
    }
}
