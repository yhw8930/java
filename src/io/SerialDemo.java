package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerialDemo {
    public static void main(String[] args) throws IOException {
        User user = new User();
        user.setAge("23");
        user.setName("zhnagsan");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("user.ser"));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
    }
}
