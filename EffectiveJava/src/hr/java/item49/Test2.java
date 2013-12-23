package hr.java.item49;

public class Test2 {

	
	public static void main(String[] args) {
		
		Integer smallInt = 2;
		Integer smallInt2 = 2;
		
		System.out.println(smallInt.equals(smallInt2)); //obj equality
		System.out.println(smallInt == smallInt2);      //obj identity
		
		Integer bigInt = 2000;
		Integer bigInt2 = 2000;
		
		System.out.println(bigInt.equals(bigInt2));  //obj equality
		System.out.println(bigInt == bigInt2);       //obj identity
		
		Integer a = 2010,c = null;
		int b = 2010;
		
		System.out.println(a < b);  //unbox(a), primitive comparison
		System.out.println(a == b);  //unbox(a), primitive equality
		
		try {
			System.out.println(c == b); //unbox(c), NullPointerException
		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
		}
	}
}
