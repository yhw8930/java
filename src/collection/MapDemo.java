package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        HashMap<Integer,Integer> map= new HashMap<>();
        map.put(1,10);
        map.put(2,20);
        map.put(3,30);
        //Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        //遍历key
        for (Integer integer : map.keySet()) {
            System.out.println(integer);
            System.out.println(map.get(integer));
        }
        System.out.println("------------------");
        //遍历entry
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }

        System.out.println("------------------");
        //迭代器
        Iterator<Map.Entry<Integer,Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        System.out.println(map.size());
       if(map.containsKey(2)){
           System.out.println(map.containsKey(2));
       }
        //遍历entry
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
}
