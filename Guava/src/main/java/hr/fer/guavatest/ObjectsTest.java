package hr.fer.guavatest;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

public class ObjectsTest {
	
	public static class Something implements Comparable<Something>{
		
		private String value;
		private int version;
		
		public Something(String value, int version) {
			this.value = value;
			this.version = version;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Something)){
				return false;
			}
			
			Something other = (Something)obj;
			
			return Objects.equal(this.value,  other.getValue()) && 
					Objects.equal(this.version, other.getVersion());
			
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(getValue(),getVersion());
		}
		
		public String getValue() {
			return value;
		}
		
		public int getVersion() {
			return version;
		}
		
		
		
		@Override
		public String toString() {
			return Objects.toStringHelper(this)
					.add("value", getValue())
					.add("version", getVersion())
					.toString();
		}

		public int compareTo(Something o) {
			return ComparisonChain.start().compare(this.value, o.value)
					                      .compare(this.version, o.version)
					                      .result();
		}
	}

	public static void main(String[] args) {
		
		String name = "hello";
		name = Objects.firstNonNull(name, "default name");
		System.out.println(name);
		
		name = null;
		name = Objects.firstNonNull(name, "default name");
		System.out.println(name);
		
		Something s1 = new Something("aa", 1);
		Something s2 = new Something("bb", 1);
		Something s3 = new Something("aa", 0);
		Something s4 = new Something("bb", 0);
		System.out.println(s1);
		System.out.println(s2);
		
		List<Something> somethings = Lists.newArrayList(s1,s2,s3,s4);
		Collections.sort(somethings);
		System.out.println(somethings);
		
		System.out.println(s1.equals(s2));
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		
		s2.value = "aa";
		s2.version = 1;
		System.out.println(s1.equals(s2));
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
	}
}
