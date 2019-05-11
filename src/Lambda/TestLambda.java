package Lambda;

interface Divable {
	int div(int num1, int num2);
}
interface Divable1 {
	String div(String num1, String num2);
}
interface Divable2 {
	void div(String num1);
}
interface Divable3 {
	void div();
}
public class TestLambda {
	public static void main(String[] args) {
		Divable d = (int num1, int num2) -> {
			System.out.println("ss");
			return num1 + num2;
		};
		Divable1 d1 = (final String num1, String num2) -> {
			return num1 + num2;
		};
		Divable1 d11 = (final String num1, String num2) -> {return num1+num2;};
		Divable2 d2 = msg -> {
			System.out.println(msg + "abc");
		};
		Divable2 d21 = msg -> System.out.println(msg + "abc");
		Divable3 d3=()->{System.out.println("");};
	}
}
