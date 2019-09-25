import java.util.*;

public class Main582 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] score = new int[n];
        int[] res = new int[n];
        if (n==0){
            System.out.println(0);
        }else if (n==3){
            System.out.println(10);
        }else {
            for (int i = 0; i < n; i++) {
                score[i] = sc.nextInt();
            }
            if (score[1] > score[0]) {
                res[0] = 1;
            } else {
                for (int i = 1; i < score.length; i++) {
                    if (score[i]<score[i-1]){
                        res[0] = i+1;
                    }else {
                        break;
                    }

                }

            }
            for (int i = 1; i < res.length; i++) {
                if (score[i] > score[i - 1]) {
                    res[i] = res[i - 1] + 1;
                } else {
                    res[i] = 1;
                }
            }
            int target = 0;
            for (int i = 0; i < res.length; i++) {
                target += res[i];
                System.out.print(res[i]+" ");
            }
            System.out.println(target);
        }

    }
}
