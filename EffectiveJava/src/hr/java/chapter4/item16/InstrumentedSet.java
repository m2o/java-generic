package hr.java.chapter4.item16;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InstrumentedSet<E> extends ForwardingSet<E> {

	private int addCount = 0;
	
	public InstrumentedSet(Set<E> s) {
		super(s);
	}

	public boolean add(E e) {
		addCount++;
		return super.add(e);
	};
	
	@Override
	public boolean addAll(Collection<? extends E> e) {
		addCount += e.size();
		return super.addAll(e);
	}
	
	public int getAddCount() {
		return addCount;
	}
	
	public static void main(String[] args) {
		InstrumentedSet<Integer> s = new InstrumentedSet<Integer>(new HashSet<Integer>());
		s.add(1);
		s.add(4);
		s.addAll(Arrays.asList(1,2,3,4));
		s.remove(4);
		System.out.println(s.getAddCount());
	}
}
