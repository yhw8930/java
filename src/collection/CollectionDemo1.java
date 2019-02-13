package collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionDemo1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("a");
        list.add("d");
        //iter
        for (String s : list) {
            System.out.println(s);
        }
        //fori
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        //itli
        for (int i = 0; i < list.size(); i++) {
            String s =  list.get(i);

        }
        //itit
        Iterator<String> iterator = list.iterator();
        //删除某个元素
        while (iterator.hasNext()) {
            String next =  iterator.next();
            if("a".equals(next)){
                iterator.remove();
            }
        }
        //删除某个元素
        for(int i=list.size()-1;i>=0;i--){
            if("a".equals(list.get(i))){
                list.remove(i);
            }
        }
        list.forEach(System.out::println);

    }
}
