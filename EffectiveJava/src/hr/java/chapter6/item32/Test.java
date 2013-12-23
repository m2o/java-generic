package hr.java.chapter6.item32;

import java.util.EnumSet;
import java.util.Set;

public class Test {
	
	public static void print(String value,Set<Style> styles){
		System.out.println(value+" "+styles.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		print("bla", EnumSet.allOf(Style.class));
		print("bla", EnumSet.of(Style.STRIKETHROUGH,Style.BOLD));
		print("bla", EnumSet.complementOf(EnumSet.of(Style.BOLD)));

	}

}
