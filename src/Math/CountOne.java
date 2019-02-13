package Math;

import java.util.Scanner;

/**
 * 求二进制中1的个数，时间复杂度为O(n)
 */
public class CountOne {
    public static int countOne(int num){
        int count=0;
        while (num!=0){
            num=num&(num-1);
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        /*Scanner input=new Scanner(System.in);
        int num = input.nextInt();
        System.out.println(countOne(num));*/
        System.out.println(Integer.toBinaryString(7));
        System.out.println(countOne(-1));
    }
}
