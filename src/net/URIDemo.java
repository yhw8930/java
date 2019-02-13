package net;

import java.net.URI;
import java.net.URISyntaxException;

public class URIDemo {
    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("https://www.baidu.com");
        URI uri1 = URI.create("https://www.baidu.com");
        System.out.println(uri1.getScheme());
        System.out.println(uri1.getHost());
        System.out.println(uri1.getPort());
        System.out.println(uri1.getPath());
        System.out.println(uri.getQuery());
    }
}
