package net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDemo {
    public static void main(String[] args) throws Exception{
        //URL url = new URL("http://www.baidu.com");
        String p= "this is 2.5%";
        String encode = URLEncoder.encode(p, "utf-8");
        System.out.println(encode);
        System.out.println(URLDecoder.decode(encode,"utf-8"));
    }
}
