package io;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Deserial {
    public static void main(String[] args) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("user.ser"));
        User user = (User) objectInputStream.readObject();
        System.out.println(user.getAge() + "  " + user.getName());
    }
}
