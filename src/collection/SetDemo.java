package collection;

import java.util.*;

public class SetDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Tom");
        set.add("Jack");
        set.add("Rose");
        set.add("Tom");
        set.forEach(System.out::println);
        System.out.println("--------------");
        LinkedHashSet<String> name = new LinkedHashSet<>();
        name.add("Tom");
        name.add("Jack");
        name.add("Rose");
        name.add("Tom");
        name.forEach(System.out::println);
        System.out.println("-----------------====================");
        TreeSet<String> name2 = new TreeSet<>();
        name2.add("Tom");
        name2.add("Jack");
        name2.add("Rose");
        name2.add("Tom");
        name2.forEach(System.out::println);
    }


}
