package hr.fer.guavatest.function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;
import com.google.common.collect.Sets;

public class FunctionalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		Function<String,String> identity = Functions.identity();
		Function<Object, Integer> score = Functions.constant(-1);
		Function<Object, String> toStringFunc = Functions.toStringFunction();
		
		Set<Integer> ints = Sets.newLinkedHashSet(Arrays.asList(-10,100,-3,0,1,100));
		
		Predicate<Integer> isIntPositive = new Predicate<Integer>() {
			public boolean apply(Integer input) {
				return input > 0;
			}
		};
		
		System.out.println(ints.getClass());
		System.out.println(ints);
		System.out.println(Iterables.all(ints,isIntPositive));
		System.out.println(Iterables.any(ints,isIntPositive));
		
		Integer firstPositive = Iterables.find(ints, isIntPositive);
		System.out.println(firstPositive);
		
		try {
			Iterables.find(ints, Predicates.alwaysFalse());
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		Optional<Integer> optionalInt = Iterables.tryFind(ints, Predicates.alwaysFalse());
		System.out.println(optionalInt.isPresent());
		
		Function<Integer,Integer> timesTen = new Function<Integer,Integer>() {
			public Integer apply(Integer input) {
				return input * 10;
			}
		};
		Iterable<Integer> intsTimesTen = Iterables.transform(ints,timesTen);
		System.out.println(intsTimesTen.getClass());
		System.out.println(intsTimesTen);
		
//		Collections2.transform(fromCollection, function)
		
		Function<String,String> abFunc = Functions.forMap(ImmutableMap.of("a","A","b","B"),"?");
		List<String> names = Lists.newArrayList("a","h","c","b","d");
		List<String> names2 = Lists.transform(names, abFunc);
		System.out.println(names2);
		System.out.println(names2.getClass());
		
		names.add("e");
		System.out.println(names2); //liveview!!!
		
		names.remove(0);
		System.out.println(names2);  //liveview!!!
		
		try {
			names2.add("a");
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		
		
		//iterator.remove
		List<String> vals = Lists.newArrayList("aaa","bbb","cc","d","e");
		Iterator<String> itVals = vals.iterator();
		int i = 0;
		while(itVals.hasNext()){
			String val = itVals.next();
			System.out.println(val);
			
			if(i++ % 3 == 0){
				itVals.remove();
				System.out.println("removed!");
			}
		}
//		itVals.remove();
		System.out.println(vals);
		
		
		//Maps.transform...
		
		Map<Integer, String> days = Maps.newHashMap(ImmutableMap.<Integer,String>builder()
												.put(0, "monday")
												.put(1,"tuesday")
												.put(2,"wednesday")
												.put(3,"thursday")
												.put(4,"friday")
												.put(5,"saturday")
												.build());
		
		Function<String,String> capitalizeFunc = new Function<String, String>() {
			public String apply(String input) {
				return input.substring(0, 1).toUpperCase()+input.substring(1).toLowerCase();
			}
		};
		Map<Integer, String> capitalizedDays = Maps.transformValues(days,capitalizeFunc);
		System.out.println(days);
		System.out.println(capitalizedDays);
		
		days.put(6, "sunday");
		System.out.println(days);
		System.out.println(capitalizedDays); //liveview!!!
		
		Map<Integer,String> test = Maps.transformEntries(days, new EntryTransformer<Integer,String,String>(){
			public String transformEntry(Integer key, String value) {
				return String.format("%d-%s",key,value);
			}
		});
		System.out.println(test);

	
	}

}
