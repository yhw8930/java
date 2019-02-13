package Math;

import java.util.Scanner;

/**
 * 判断一个数是否是2的n次方，时间复杂度O(1)
 * 条件n&(n-1)
 */
public class IsLog2 {
    private static boolean isLog2(int n){
        if(n<1){
            return false;
        }
        return (n&(n-1))==0;
    }
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int num = input.nextInt();

        System.out.println(isLog2(num));

    }
}
