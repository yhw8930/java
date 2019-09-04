package Offer;

/**
 * 翻转字符串
 * “student. a am I” => “I am a student."
 */
public class ReverseSentence {
    public static void main(String[] args) {
        System.out.println(new ReverseSentence().ReverseSentence("student. a am I"));
    }

    public String ReverseSentence(String str) {
        if (str == null || str.length() == 0 || str.trim().equals("")) return str;
        String[] s = str.split(" ");
        String ss = "";
        for (int i = s.length - 1; i >= 0; i--) {
            ss += s[i] + " ";
        }
        return ss.trim();
    }
}
