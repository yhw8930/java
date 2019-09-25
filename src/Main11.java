import java.util.*;


public class Main11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        LinkedList<String> list = new LinkedList<>();
        String[] s = new String[]{"0","1","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        list.add("");
        for (int i = 0; i <num.length() ; i++) {
            int n = Character.getNumericValue(num.charAt(i));
            while (list.peek().length()==i){
                String ss = list.remove();
                char[] chars = s[n].toCharArray();
                for (int j = 0; j < chars.length ; j++) {
                    list.add(ss+chars[j]);
                }
            }
        }
        System.out.println(list.toString());
    }


}
