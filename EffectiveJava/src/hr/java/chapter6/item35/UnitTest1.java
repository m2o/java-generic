package hr.java.chapter6.item35;

public class UnitTest1 {

	@Test
	public static void test1(){

	}
	
	@Test
	public void test2(){

	}
	
	@Test
	public static void test3(){
		throw new IllegalArgumentException();
	}
	
	@ExceptionTest(IllegalArgumentException.class)
	public static void test4(){
		throw new IllegalArgumentException();
	}
	
	@ExceptionTest(NullPointerException.class)
	public static void test5(){
		throw new IllegalArgumentException();
	}
	
	@ExceptionTest({NullPointerException.class,IllegalArgumentException.class})
	public static void test6(){
		throw new IllegalArgumentException();
	}
}
