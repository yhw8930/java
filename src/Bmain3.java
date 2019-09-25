import java.util.*;

public class Bmain3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] strs = s.split(" ");
        String s1 = strs[0];
        String s2 = strs[1];
        String s3 = strs[2];
        String[] split = s3.split(s1);
        System.out.println(split.length);
        for (String ss : split) {
            String[] strings = ss.split(s2);
            System.out.println(strings[0] + " " + strings[1]);
        }
    }
}
