package collection;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Person implements Comparable<Person>{
    String name;
    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.name);
    }
}
public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");

        //queue.forEach(System.out::println);
        while (queue.peek()!=null){
            System.out.println(queue.peek());
            queue.poll();
        }
        Queue<Person>  queue1 = new PriorityQueue<>();
        Person p1 =new Person();
        p1.name="Tom";
        Person p2 =new Person();
        p2.name="Jack";
        Person p3 =new Person();
        p3.name="Mary";
        Person p4 =new Person();
        p4.name="Pom";

        queue1.offer(p1);
        queue1.offer(p2);
        queue1.offer(p3);
        queue1.offer(p4);

        while (queue1.peek()!=null){
            Person person = queue1.peek();
            System.out.println(person.name);
            System.out.println(queue1.peek());
            queue1.remove();
        }
    }
}
