package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/didi/javasource/java/src/io/text");
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/didi/javasource/java/src/io/test/text1");
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = fileInputStream.read(data, 0, data.length)) != -1) {
                fileOutputStream.write(data, 0, length);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
