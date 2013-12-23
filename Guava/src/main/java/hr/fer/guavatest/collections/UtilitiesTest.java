package hr.fer.guavatest.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class UtilitiesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<String> names = Lists.newArrayList("a","b","c");
		names.add("d");
		System.out.println(names);

		List<String> names2 = Lists.newLinkedList(Arrays.asList("e","f","g","h"));
		names2.add("i");
		System.out.println(names2);

		Map<Integer,String> studentIds = ImmutableMap.of(1, "student1", 2, "student2");
		System.out.println(studentIds);

	}

}
