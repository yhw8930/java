package io;

import java.io.File;
import java.io.IOException;

public class FileDemo {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        File file =new File("/src");
       /* File file2 =new File("/src/io");
        System.out.println(file2.exists());
        System.out.println(file.exists());
        //System.out.println(file.createNewFile());
        System.out.println(file2.getName());
        String[] list = file2.list();
        for (String s : list) {
            System.out.println(s);*/
        for (File listRoot : File.listRoots()) {
            System.out.println(listRoot.getPath());
        }
        File file1 = new File("/Users/didi/javasource/java/src");
        for (String s : file1.list()) {
            System.out.println(s);
        }
    }
}
