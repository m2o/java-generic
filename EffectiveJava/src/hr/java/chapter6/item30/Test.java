package hr.java.chapter6.item30;

import java.math.RoundingMode;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.printf("hello%n");
		printIt(Operation.MINUS);
		
		for(Operation op : Operation.values()){;
			System.out.printf("%f %s %f = %f%n",3.0,op,5.0,op.apply(3.0,5.0));
		}
		
		for(PayRollDay day : PayRollDay.values()){;
			System.out.printf("%s %f%n",day,day.pay());
		}
	}

	private static void printIt(Stringable s){
		System.out.println(s.toString2());
	}
}
