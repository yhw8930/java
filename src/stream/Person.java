package stream;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Person {
	public static enum Gender {
		MALE, FEMALE
	}

	private int id;
	private String name;
	private double income;
	private LocalDate birthDate;
	private Gender gender;

	public Person(int id, String name, double income, LocalDate birthDate,
			Gender gender) {
		this.id = id;
		this.name = name;
		this.income = income;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public boolean isMale() {
		return this.gender == Gender.MALE;
	}

	public boolean isFeMale() {
		return this.gender == Gender.FEMALE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public static List<Person> persons() {
		Person p1 = new Person(1, "张三", 2000, LocalDate.of(1982, 3, 2),
				Gender.MALE);
		Person p2 = new Person(2, "李四", 5000, LocalDate.of(1967, 12, 2),
				Gender.FEMALE);
		Person p3 = new Person(3, "王五", 7000, LocalDate.of(1990, 4, 17),
				Gender.MALE);
		Person p4 = new Person(4, "赵六", 6500, LocalDate.of(1967, 3, 9),
				Gender.MALE);
		Person p5 = new Person(5, "钱七", 5200, LocalDate.of(1988, 10, 12),
				Gender.FEMALE);
		return Arrays.asList(p1, p2, p3, p4, p5);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s,%s,%s,%s)", id, name, income, birthDate,
				gender);
	}

}
