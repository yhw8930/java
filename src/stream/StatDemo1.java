package stream;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

public class StatDemo1 {

	public static void main(String[] args) {
		double totalIncome = Person.persons().stream()
				.collect(Collectors.summingDouble(Person::getIncome));
		System.out.println(totalIncome);

		DoubleSummaryStatistics incomeStats = Person.persons().stream()
				.collect(Collectors.summarizingDouble(Person::getIncome));
		System.out.println(incomeStats.getMax());
	}

}
