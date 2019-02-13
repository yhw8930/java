package Math;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 找出数组中重复次数最多的元素
 */
public class FindMostFrequentInArray {
    public static int findMostFrequentInArray(int[] a){
        int num=Integer.MIN_VALUE;
        int max=0;
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])){
                map.put(a[i],map.get(a[i])+1);
            }else {
                map.put(a[i],1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next =  iterator.next();
            if (next.getValue()>max){
                max=next.getValue();
                num=next.getKey();
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int[] a={1,1,2,2,3,3,3,3,4,4,6,6,6};
        System.out.println(findMostFrequentInArray(a));
    }
}
