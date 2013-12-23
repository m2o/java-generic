package hr.fer.guavatest.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class BiMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BiMap<Integer, String> studentIdsMap = HashBiMap.create();

		for(int i = 0;i<10;i++){
			Integer id = i;
			String name = String.format("Student_%d",i);
			studentIdsMap.put(id, name);
		}

		System.out.println(studentIdsMap);

		try {
			studentIdsMap.put(100, "Student_3");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		BiMap<String,Integer> studentNameMap = studentIdsMap.inverse();
		System.out.println(studentNameMap);


	}

}
