package hr.fer.guavatest.collections;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.BoundType;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

public class MultiSetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random r = new Random();

		//Multiset<String> studentNames = HashMultiset.create();
		SortedMultiset<String> studentNames = TreeMultiset.create();

		for(int i = 0;i<10;i++){
			for(int j=r.nextInt(10); j>0; j--){
				String name = String.format("Student_%d",i);
				studentNames.add(name);
			}
		}

		System.out.println(studentNames);
		System.out.println("size:"+studentNames.size());

		Set<String> elementSet = studentNames.elementSet();
		System.out.println("elementSet:"+elementSet);
		System.out.println("elementSet size:"+elementSet.size());

		Set<Entry<String>> entrySet = studentNames.entrySet();
		System.out.println("entrySet:"+entrySet);
		for(Entry<String> e : entrySet){
			System.out.println(String.format("%s-%d",e.getElement(),e.getCount()));
		}

		SortedMultiset<String> filteredStudentNames = studentNames.subMultiset("Student_3", BoundType.OPEN, "Student_9", BoundType.OPEN);
		System.out.println(filteredStudentNames);

		Multiset<String> studentNamesByFrequency = Multisets.copyHighestCountFirst(studentNames);
		for(Entry<String> e : studentNamesByFrequency.entrySet()){
			System.out.println(String.format("%s-%d",e.getElement(),e.getCount()));
		}

	}

}
