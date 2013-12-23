package hr.fer.guavatest.string;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;

public class CharMatcherTest {
	
	public static void main(String[] args) {
		
		CharMatcher cm = CharMatcher.inRange('a', 'e');
		Predicate<Character> pCm = cm;
		System.out.println(pCm.apply('A'));
		
		CharMatcher monkeyCm = CharMatcher.forPredicate(new Predicate<Character>() {
			public boolean apply(Character input) {
				return new Character('@').equals(input);
			}
		});
		
		System.out.println(monkeyCm.matchesAnyOf("t.piv@gmail.com"));
		
		String val = CharMatcher.WHITESPACE.trimAndCollapseFrom("  a  b      c\t d  ",' ');
		System.out.println(val);
		
		val = CharMatcher.DIGIT.retainFrom("hello123 this is 456");
		System.out.println(val);
		
		val = CharMatcher.DIGIT.replaceFrom("hello123 this is 456","?");
		System.out.println(val);
		
		int i = CharMatcher.DIGIT.indexIn("hello123");
		System.out.println(i);
	}

}
