package hr.java.chapter4.item22;

public class Apple {

	private final String name;

	public Apple(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "apple "+name;
	}

	private Fruit asFruit() {
		return new Fruit();
	}

	private class Fruit{

		@Override
		public String toString() {
			return "fruit "+name;
		}
	}

	public static void main(String[] args) {

		Apple a = new Apple("bla");

		System.out.println(a);
		System.out.print(a.asFruit());

	}
}
