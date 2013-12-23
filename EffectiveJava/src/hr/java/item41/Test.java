package hr.java.item41;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	
	public static void main(String[] args) {
		
		Integer i = 4;
		thisIsMe(i); //integer
		
		Number n = (Number)i;
		thisIsMe(n); //number
		
		Object o = (Object)i;
		thisIsMe(o); //object
		
		
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));
		System.out.println(list);
		list.remove(1); //remove(int)
		System.out.println(list);
		
		list.remove((Integer)1); //remove(E)
		System.out.println(list);
		
	}

	private static void thisIsMe(Object o) {
		System.out.println("object!");
	}

	private static void thisIsMe(Number n) {
		System.out.println("number!");
	}

	private static void thisIsMe(Integer i) {
		System.out.println("integer!");
	}
}
