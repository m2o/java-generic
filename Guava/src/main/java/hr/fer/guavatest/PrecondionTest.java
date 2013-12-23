package hr.fer.guavatest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;

public class PrecondionTest {

	private static double sqrt(int i) {
		Preconditions.checkArgument(i>=0,"sqrt arg was %s, expected non-negative int",i);
		return Math.sqrt(new Integer(i).doubleValue());
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		sqrt(10);
		try {
			sqrt(-1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		Integer arg = null;
		try {
			Preconditions.checkNotNull(arg);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		boolean status = false;
		try {
			Preconditions.checkState(status, "status not true");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		String[] bureks = new String[10];
		int i = 10;
		
		try {
			System.out.println(bureks[10]);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		try {
			Preconditions.checkElementIndex(i, bureks.length,"bureks");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		try {
			Preconditions.checkPositionIndex(i, bureks.length);
			Preconditions.checkPositionIndex(i+1, bureks.length);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		try {
			Preconditions.checkPositionIndexes(5,10, bureks.length);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		try {
			Preconditions.checkPositionIndexes(5,11, bureks.length);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		

	}
}
