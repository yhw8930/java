package Math;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标
 * 时间复杂度O(n)
 */
public class TwoSum {
    public static int[] twoSum(int[] nums,int target){
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement=target-nums[i];
            if (map.containsKey(complement)){
                return new int[]{map.get(complement),i};
            }
            map.put(nums[i],i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        int array[]={1,7,17,2,6,3,14};
        //twoSum(array,20);
        for (int i : twoSum(array,20)) {
            System.out.println(i);
        }
    }
}

