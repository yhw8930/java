package LeetCode;

/**
 * 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * <p>
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 * <p>
 * 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1
 * 输入:
 * gas  = [1,2,3,4,5]
 * cost = [3,4,5,1,2]
 * <p>
 * 输出: 3
 */
public class P134_加油站 {
    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cpst = {3, 4, 5, 1, 2};
        System.out.println(new P134_加油站().canCompleteCircuit(gas, cpst));
    }

    /**
     * total 累加全程净油量，cur 累加从当前候选起点 index 出发后的剩余油量。
     * 若到 i 时 cur < 0，则 index..i 中任意位置都不可能是起点，可直接改从 i+1 尝试。
     * 最后 total < 0 说明总油量不足，否则剩下的候选起点必然可行。
     * 时间复杂度：O(N)；额外空间：O(1)。
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, cur = 0, index = 0;
        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
            cur += gas[i] - cost[i];
            if (cur < 0) {
                index = i + 1;
                cur = 0;
            }
        }
        return total >= 0 ? index : -1;
    }
    /*public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i = 0; i < gas.length; i++) {
            if (gas[i]-cost[i]>=0){
                if (fun(i,gas,cost)){
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean fun(int index, int[] gas, int[] cost){
        int sum = 0;
        for (int i = index; i < gas.length+index; i++) {
            if (sum+gas[i%gas.length]-cost[i%gas.length]>=0){
                sum=sum+gas[i%gas.length]-cost[i%gas.length];
            }else {
                return false;
            }
        }
        return true;
    }*/

}
