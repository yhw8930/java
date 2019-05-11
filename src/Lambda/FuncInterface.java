package Lambda;

@FunctionalInterface
public interface FuncInterface<T> {
	T m(T t);

	String toString();
}

@FunctionalInterface
interface FuncInterface1 {
	<T> T m(T t);

	String toString();
}

interface MarkerInterface {

}

class TestFunInterface {
	void test() {
		FuncInterface<String> f1 = s -> s + "abc";

		// FuncInterface1 f2 = s -> s + "abc";

		MarkerInterface mi = (MarkerInterface & FuncInterface) s -> s + "abc";
	}
}
