package stream;

import java.util.Optional;
import java.util.stream.IntStream;

public class FindDemo {

	public static void main(String[] args) {
		Optional<Person> o1 = Person.persons().parallelStream().sequential()
				.findAny();
		if (o1.isPresent()) {
			Person p = o1.get();
			System.out.println(p);
		}

		Optional<Person> o2 = Person.persons().stream()
				.filter(Person::isFeMale).findFirst();
		if (o2.isPresent()) {
			System.out.println(o2.get());
		}
	}
}
