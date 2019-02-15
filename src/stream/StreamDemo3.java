package stream;

import java.util.Optional;
import java.util.stream.Stream;

public class StreamDemo3 {

	public static void main(String[] args) {
		// int num = Stream.of(1, 2, 3, 4, 5).peek(n ->
		// System.out.println("one:"+n))
		// .filter(n -> n % 2 == 1).peek(n -> System.out.println("two:"+n))
		// .reduce(0, Integer::sum);
		// System.out.println(num);

		// Person.persons().stream().forEach(p->System.out.println(p.getName()));
		// Person.persons().stream().forEach(System.out::println);
		// Person.persons().stream().map(Person::getName).forEach(System.out::println);
		// IntStream.range(1, 5).map(n -> n * n).forEach(System.out::println);

		// Person.persons().stream().filter(Person::isMale).map(Person::getName)
		// .forEach(System.out::println);
		// Person.persons().stream().filter(p->p.getIncome()>5000).map(Person::getName)
		// .forEach(System.out::println);

		Stream.of(4, 2, 3, 2, 3, 5, 1).distinct().sorted()
				.forEach(System.out::println);

		double sum = Person.persons().stream().map(Person::getIncome)
				.reduce(0D, Double::sum);

		Optional<Double> sum2 = Person.persons().stream()
				.map(Person::getIncome).reduce(Double::sum);
		if(sum2.isPresent()){
			System.out.println(sum2.get());
		}
		System.out.println(sum);
	}

}
