package Algorithm.MyTest;

/**
 * 给定一个如下格式的字符串(1,(2,3),(4,(5,6),7)),实现一个算法消除一个括号
 * (1,2,3,4,5,6,7),若表达式有误，则报错
 */
public class TestString {
    public static String change_str(String s){
        String result="(";
        char[] ch=s.toCharArray();
        int bracket_num=0;
        int i=0;
        while (i<ch.length){
            if (ch[i]=='('){
                bracket_num++;
            }else if (ch[i]==')'){
                if (bracket_num>0){
                    bracket_num--;
                }else {
                    System.out.println("Expression wrong!\n");
                    return null;
                }
            }else if (ch[i]==','){
                result+=ch[i];
            }else if (ch[i]>='0'&&ch[i]<='9'){
                result+=ch[i];
            }else {
                System.out.println("Expression wrong!\n");
                return null;
            }
            i++;
        }
        if (bracket_num>0){
            System.out.println("Expression wrong!\n");
            return null;
        }
        result+=')';
        return result;
    }

    public static void main(String[] args) {
        String s="(1,(2,3),(4,(5,6),7))";
        String result=change_str(s);
        if (result!=null)   System.out.println(result);
        String s2="((1,(2,,3),(4,(5,6),7))";
        String result2=change_str(s2);
        if (result2!=null)   System.out.println(result2);

    }
}
