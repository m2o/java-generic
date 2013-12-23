package hr.java.item3;

public class Test {

	public static void main(String[] args) {
		SomeSingleComponent singleton = SomeSingleComponent.INSTANCE;
		System.out.println(singleton);
	}
}
