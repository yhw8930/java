package stream;

public class MatchDemo {

	public static void main(String[] args) {
		boolean b1 = Person.persons().stream().allMatch(Person::isMale);
		System.out.println(b1);

		boolean b2 = Person.persons().stream()
				.anyMatch(p -> p.getBirthDate().getYear() == 1990);
		System.out.println(b2);

		boolean b3 = Person.persons().stream()
				.noneMatch(p -> p.getIncome() > 6000);
		System.out.println(b3);

	}
}
