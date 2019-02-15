package stream;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo2 {

	public static void main(String[] args) {
		Stream<String> s1=Stream.of("Tom","Jack","Rose","Tim");
		Stream<String> s2=Stream.<String>builder().add("Jack").add("Tom").build();
		
		IntStream i1=IntStream.range(1, 6).limit(1);
		i1.forEach(System.out::println);
		IntStream i2=IntStream.rangeClosed(1, 6);
		i2.forEach(System.out::println);
		
		Stream<String> s3=Stream.empty();
		IntStream i3=IntStream.empty();
		System.out.println("------------------");
		Stream<Long> l1=Stream.iterate(0L, n->n+2);
		l1.limit(10).forEach(System.out::println);
		
		Stream.generate(Math::random).limit(5).forEach(System.out::println);
		
		Arrays.stream(new int[]{1,2,3,4,5});
		Arrays.stream(new String[]{"",""});
		
		"aaaa bbb".chars().forEach(System.out::println);
		
		Pattern.compile(",").splitAsStream("abc,cde,fgr").forEach(System.out::println);
	}

}
