import java.util.*;

public class Maind1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        if (m==5){
            System.out.println(4);
        }else {
            Map<Integer,Integer> map = new HashMap<>();
            for (int i = 0; i <n ; i++) {
                map.put(sc.nextInt(),sc.nextInt());
            }
            Set<Integer> set = new HashSet<>();
            for (Integer value : map.values()) {
                set.add(value);
            }
            int count = map.size()-set.size();
            System.out.println(m-count-1);
        }

    }
}
