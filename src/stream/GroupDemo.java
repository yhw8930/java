package stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class GroupDemo {

	public static void main(String[] args) {
		Map<Person.Gender, List<Person>> r = Person.persons().stream()
				.collect(Collectors.groupingBy(Person::getGender));
		r.forEach((g, ps) -> {
			System.out.println(g);
			ps.forEach(System.out::println);
		});

		Map<Boolean, List<Person>> r1 = Person.persons().stream()
				.collect(Collectors.partitioningBy(Person::isMale));
		r1.forEach((g, ps) -> {
			System.out.println(g);
			ps.forEach(System.out::println);
		});
	}

}
