package Lambda;

import java.io.IOException;
@FunctionalInterface
interface Interface1 {
	void m(int num1, int num2);
}
@FunctionalInterface
interface Interface2 {
	void m(String num1, String num2);
	//String m(String num1, String num2)throws IOException;
}
public class TargetType {
	static void test1(Interface1 i){
	}
	static void test1(Interface2 i){
		
	}
	static Interface1 test2(){
		return (num1, num2) -> System.out.print(num1 + num2);
	}
	public static void main(String[] args) {
		Interface1 i1 = (num1, num2) -> System.out.print(num1 + num2);
//		Interface2 i2 = (num1, num2) -> {
//			System.out.println("");
//			System.in.read();
//			return num1 + num2;
//		};
		Interface2 i2= (num1, num2) -> System.out.print(num1 + num2);
		
		test1(i1);
		test1(i2);
		test1((Interface1) (num1, num2) -> System.out.print(num1 + num2));
		//test2((num1, num2) -> System.out.print(num1 + num2));
	}

}
