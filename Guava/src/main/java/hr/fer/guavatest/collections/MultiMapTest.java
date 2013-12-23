package hr.fer.guavatest.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

public class MultiMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random r = new Random();

		//		Multimap<String, Integer> studentGrades = ArrayListMultimap.create(); //values as arraylist
		ListMultimap<String, Integer> studentGrades = LinkedListMultimap.create(); //values as linkedlist
		//		Multimap<String, Integer> studentGrades = LinkedListMultimap.create(); //values as linkedlist
		//		Multimap<String, Integer> studentGrades = HashMultimap.create(); //values as set

		for(int i = 0;i<10;i++){
			String name = String.format("Student_%d",i);
			for(int j=0; j<10; j++){
				Integer grade = r.nextInt(7)+1;
				studentGrades.put(name, grade);
			}
		}

		System.out.println(studentGrades);
		for(Map.Entry<String, Collection<Integer>> e : studentGrades.asMap().entrySet()){
			System.out.println(String.format("%s - %s",e.getKey(),e.getValue()));
		}

	}

}
