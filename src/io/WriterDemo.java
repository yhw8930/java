package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterDemo {
    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/didi/javasource/java/src/io/text"));
        bufferedWriter.append("poi");
        bufferedWriter.newLine();
        bufferedWriter.append("123");
        bufferedWriter.flush();
    }
}
