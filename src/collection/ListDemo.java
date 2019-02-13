package collection;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add(0,"Tom");
        list.add(1,"Jack");
        list.add(2,"Rose");
        list.add(3,"Tom");
       /* System.out.println(list.lastIndexOf("Tom"));subString()
        list.forEach(System.out::println);
        System.out.println("---------------------");*/
        ListIterator<String> listIterator = list.listIterator();

        //从后往前遍历
        while (listIterator.hasPrevious()) {
            String next =  listIterator.previous();
            System.out.println(next);
        }
        list.clear();
        //从前往后遍历
        while (listIterator.hasNext()) {
            String next =  listIterator.next();
            System.out.println(next);

        }
    }
}
