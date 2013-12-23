package hr.java.chapter5.item23;

import java.util.Arrays;
import java.util.List;

public class Test {

	
	static void work1(List<Object> list){
		System.out.println(list);
	}
	
	static void work2(List list){
		System.out.println(list);
	}
	
	static void work3(List<?> list){
		list.add(new Integer(1));
		Object o = list.get(0);
		System.out.println(o);
	}
	
	
	public static void main(String[] args) {
		List l = Arrays.asList(1,2,3);
		List<?> ls;
		
		if(l instanceof List){
			ls = (List<?>)l;
		}
		
		work1(l);
		work2(l);
		work3(l);
		
		work1(ls);
		work2(ls);
		work3(ls);
	}
}
