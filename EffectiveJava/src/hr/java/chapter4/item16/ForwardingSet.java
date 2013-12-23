package hr.java.chapter4.item16;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ForwardingSet<E> implements Set<E> {

	private Set<E> s;
	
	public ForwardingSet(Set<E> s) {
		this.s = s;
	}

	@Override
	public boolean add(E e) {
		return this.s.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> e) {
		return this.s.addAll(e);
	}

	@Override
	public void clear() {
		s.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.s.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.s.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return this.s.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.s.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return this.s.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> o) {
		return this.s.removeAll(o);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.s.retainAll(c);
	}

	@Override
	public int size() {
		return this.s.size();
	}

	@Override
	public Object[] toArray() {
		return this.s.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.s.toArray(a);
	}

}
