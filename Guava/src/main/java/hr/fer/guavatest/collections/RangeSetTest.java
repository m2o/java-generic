package hr.fer.guavatest.collections;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class RangeSetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RangeSet<Integer> rSet = TreeRangeSet.create();
		rSet.add(Range.closed(5, 10));
		rSet.add(Range.open(30, 35));

		System.out.println(rSet);
		System.out.println("complement:"+rSet.complement());
		System.out.println("has 6?:"+rSet.contains(6));
		System.out.println("span:"+rSet.span());

		Range<Integer> spanRange = rSet.span();

		for(int i = spanRange.lowerEndpoint();i<=spanRange.upperEndpoint();i++){
			if(rSet.contains(i)){
				System.out.println(i);
			}
		}

	}

}
