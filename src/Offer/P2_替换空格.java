package Offer;

/**
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class P2_替换空格 {
    public static void main(String[] args) {
        System.out.println(new P2_替换空格().replaceSpace(new StringBuffer("We Are Happy.")));
    }

    public String replaceSpace(StringBuffer str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ') {
                buffer.append(c);
            } else {
                buffer.append("%20");
            }
        }
        return buffer.toString();
    }
}
