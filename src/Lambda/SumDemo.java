package Lambda;
interface Policy{
    boolean test(int num);
}
public class SumDemo {
    public static int add(int[] nums,Policy policy){
        int count = 0;
        for (int num : nums) {
            if (policy.test(num)){
                count+=num;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(add(nums, (num) -> true));
        System.out.println(add(nums, (num) -> num % 2 == 0));
        System.out.println(add(nums, (num) -> num % 3 == 0));
    }
}
