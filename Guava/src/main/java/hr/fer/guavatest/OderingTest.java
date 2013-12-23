package hr.fer.guavatest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.management.MXBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class OderingTest {

	private static class MyStr implements Comparable<MyStr>{
		
		private String value;
		private int version;

		public MyStr(String value,int version) {
			this.value = value;
			this.version = version;
		}
		
		public String getValue() {
			return value;
		}
		
		public int getVersion() {
			return version;
		}

		/* (non-Javadoc)
		 * natural order
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(MyStr o) {
			return this.getValue().compareTo(o.getValue());
		}
		
		@Override
		public String toString() {
			return String.format("%s(%s,%d)", getClass().getSimpleName(),getValue(),getVersion());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<MyStr> myStrsOrdered = Lists.newArrayList(new MyStr("auto",0),
												       new MyStr("auto",1),
													   new MyStr("burek",0),
				                                       new MyStr("burek",1),
				                                       null,
				                                       new MyStr("casa",0),
				                                       new MyStr("casa",1));
		
		List<MyStr> myStrsUnordered = Lists.<MyStr>newArrayList(myStrsOrdered);
		Collections.shuffle(myStrsUnordered);
		
		System.out.println(myStrsOrdered);
		System.out.println(myStrsUnordered);

		Ordering<MyStr> myStrsNaturalOrder = Ordering.natural().nullsLast();
		System.out.println(myStrsNaturalOrder.isOrdered(myStrsOrdered));
		System.out.println(myStrsNaturalOrder.isOrdered(myStrsUnordered));
		System.out.printf("natural:%s\n",myStrsNaturalOrder.sortedCopy(myStrsUnordered));
		
		Ordering<Object> myStrsToStringOrdering = Ordering.usingToString().nullsLast();
		System.out.printf("toString:%s\n",myStrsToStringOrdering.sortedCopy(myStrsUnordered));
		
		Ordering<MyStr> myStrsByValueOrdering = (new Ordering<OderingTest.MyStr>() {
			@Override
			public int compare(MyStr left, MyStr right) {
				return left.getValue().compareTo(right.getValue());
			}
		}).nullsLast();
		System.out.printf("byValue:%s\n",myStrsByValueOrdering.sortedCopy(myStrsUnordered));
		
		Ordering<MyStr> myStrsByValueOrderingReversed = myStrsByValueOrdering.reverse();
		System.out.printf("byValue(reversed):%s\n",myStrsByValueOrderingReversed.sortedCopy(myStrsUnordered));
		
		
		Ordering<MyStr> myStrsByVersionOrdering = (new Ordering<OderingTest.MyStr>() {
			@Override
			public int compare(MyStr left, MyStr right) {
				return Integer.compare(left.getVersion(), right.getVersion());
			}
		}).nullsLast();
		System.out.printf("byVersion:%s\n",myStrsByVersionOrdering.sortedCopy(myStrsUnordered));

		Ordering<MyStr> myStrsByValueByVersionOrdering = myStrsByValueOrdering.compound(myStrsByVersionOrdering);
		System.out.printf("byValueVersion:%s\n",myStrsByValueByVersionOrdering.sortedCopy(myStrsUnordered));
		
		
		System.out.println(myStrsByValueByVersionOrdering.leastOf(myStrsUnordered, 3));
	}

}
