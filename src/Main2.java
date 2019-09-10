import java.util.*;
//1(2(3,4(,5)),6(7,)) =>中序遍历 3245176
public class Main2 {
    public static void main(String[] args) {

        Long a = 100L, b = new Long(100L);
        System.out.println(a == b);

        Long a1 =  100L, b1 = 100L;
        System.out.println(a1 == b1);

        Integer a2 = 100;
        Long b2 = 100L;
        System.out.println(a2.equals(b2));
        System.out.println(solution("1(2(3,4(,5)),6(7,))"));
    }

    static String solution(String input) {
        if(input==null||input.length()<2){
            return input;
        }
        Stack<Character> number=new Stack<Character>();
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<input.length();i++){
            char c=input.charAt(i);
            if(c>='0'&&c<='9'){
                char c1=input.charAt(i+1);
                if(c1==','){
                    builder.append(c);
                    builder.append(number.pop());
                    i++;
                    continue;
                }
                number.push(c);
            }
            if(c=='('){
                char c1=input.charAt(i+1);
                if(c1==','){
                    builder.append(number.pop());
                    i++;
                }
            }
            if(c==')'){
                builder.append(number.isEmpty()?"":number.pop());
            }
        }
        return builder.toString();
    }
}
