package hr.java.chapter5.item25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Test {

	public static void printArray(Object[] array){
		System.out.println(Arrays.toString(array));
	}
	
	public static void printArrayVar(Object... vars){
		System.out.println(Arrays.toString(vars));
	}
	
	public static <E> void printArrayE(E... vars){
		System.out.println(Arrays.toString(vars));
	}
	
	public static void main(String[] args) {
		
		Integer[] ints = new Integer[]{1,2,3};
		Number[] nums = ints;
		
		printArray(ints);
		printArray(nums);
		
		ints[0] = new Integer(10);
		nums[1] = new Integer(20); 
	
		printArray(ints);
		printArray(nums);
		
//		ints[2] = new Double(1.1d); //doesn't compile
//		nums[2] = new Double(1.1d); //throws ArrayStoreException
		
		
//		List<Object> l = new ArrayList<Integer>(); //doesn't compile
//		l.add(new Double(1.1));
		
		List<?> l2 = new ArrayList<Integer>(); //compiles
//		l2.add(new Double(1.1));//doesn't compile
		
		List[] lists = new List[10];
		List<?>[] lists2 = new List<?>[10];
//		List<Object>[] lists3 = new List<Object>[10]; //doesn't compile
		
		
		printArrayVar(1,2,3,4,5);
		Test.<String>printArrayE("a","b","c","d","e");
		printArrayVar(new HashSet<Integer>());
	}
}
