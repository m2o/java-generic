package hr.fer.guavatest.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class ImmutableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Set<String> colors = new HashSet<String>(Arrays.asList("red","blue","green"));
		colors = Collections.unmodifiableSet(colors);

		System.out.println(colors);
		try {
			colors.add("violet");
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}

		Set<String> colors2 = ImmutableSet.of("red","blue","yellow");
		System.out.println(colors2);
		try {
			colors2.add("violet");
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}

		Set<String> colors3 = ImmutableSet.copyOf(Arrays.asList("red","blue","green","red","green","black"));
		System.out.println(colors3);
	}

}
