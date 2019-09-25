package Offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计一个数字在排序数组中出现的次数
 */
public class P35_数字在排序数组中出现的次数 {
    public int GetNumberOfK(int [] array , int k) {
        if(array==null||array.length==0) return 0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<array.length;i++){
            if(map.containsKey(array[i])){
                map.put(array[i],map.get(array[i])+1);
            }else{
                map.put(array[i],1);
            }
        }
        if(map.get(k)==null) return 0;
        return map.get(k);

    }
}
