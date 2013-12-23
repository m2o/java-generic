package hr.java.item11;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Apple a = new Apple("blue", true);
		System.out.println("a: "+a);
		
		Apple b = a.clone();
		
		b.setNewtons(false);
		b.setColor("yellow");
		b.getData()[0] = 100;
		
		System.out.println("a: "+a);
		System.out.println("b: "+b);

	}

}
