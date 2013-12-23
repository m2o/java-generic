package hr.java;

public class LogicTest {

	private static boolean isA(){
		System.out.println("A");
		return true;
	}

	private static boolean isB(){
		System.out.println("B");
		return true;
	}

	public static void main(String[] args) {
		boolean value = isA() && isB();
		System.out.println(value);

		value = isA() || isB();
		System.out.println(value);

		value = isA() | isB();
		System.out.println(value);
	}
}
