package Lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
interface ToInt<T> {
	int convert(T t);
}

@FunctionalInterface
interface Interface3{
	<T> T m(T t);
}

class MyClass3{
	static <T> T n(T t){
		return t;
	}
}

public class TestMethodRef {

	public static void main(String[] args) {
		ToInt<String> t1 = Integer::parseInt;
		//System.out.println(t1.convert("123"));

		Supplier<Integer> s1 = "Jack"::length;
		System.out.println(s1.get());

		Consumer<String> s2 = System.out::print;

		Function<String, Integer> f1 = String::length;
		System.out.println(f1.apply("Rose and Jack"));

		Son son = new Son();
		son.m();

		Supplier<Son> son1 = Son::new;
		Son s = son1.get();

		Function<String, Son> fun1 = Son::new;
		Son son2 = fun1.apply("张武");

		Function<Integer, int[]> fun2 = int[]::new;
		int[] nums = fun2.apply(5);
		
		Function<Integer, int[]> fun3 =size->new  int[size];
		
		Interface3 i3=MyClass3::n;
	System.out.println(i3.m("泛型方法"));
	}

}

class Parent {
	private String name = "张三";

	String getName() {
		return name;
	}
}

class Son extends Parent {
	private String name = "张四";

	public Son() {

	}

	public Son(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	void m() {
		Supplier<String> s1 = this::getName;
		Supplier<String> s2 = Son.super::getName;
		System.out.println(s1.get());
		System.out.println(s2.get());
	}
}
