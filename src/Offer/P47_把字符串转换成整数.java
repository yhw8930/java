package Offer;

/**
 * 将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，但是string不符合数字要求时返回0)，要求不能使用字符串转换整数
 * 的库函数。 数值为0或者字符串不是一个合法的数值则返回0。
 */
public class P47_把字符串转换成整数 {
    public int StrToInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        } else if (str.length() == 1 && (str == "-" || str == "+")) {
            return 0;
        } else {
            boolean flag = true;
            int i = 0;
            if (str.charAt(0) == '-') {
                i++;
                flag = false;
            }
            if (str.charAt(0) == '+') {
                i++;
            }
            int sum = 0;
            for (int j = i; j < str.length(); j++) {
                if (str.charAt(j) < '0' || str.charAt(j) > '9') {
                    return 0;
                }
                sum = sum * 10 + str.charAt(j) - '0';
            }
            return flag == true ? sum : -sum;
        }
    }
}
