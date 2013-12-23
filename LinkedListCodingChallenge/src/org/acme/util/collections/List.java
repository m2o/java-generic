package org.acme.util.collections;

/**
 * An interface of a List, consisting of a subset of the {@link java.util.List}
 * interface.
 * 
 * @author Toni Pivčević
 * @param <E>
 */
public interface List<E> {
	boolean add(E element);

	boolean add(int i, E element);

	void clear();

	boolean contains(E elem);

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	E get(int index);

	boolean isEmpty();

	boolean remove(Object o);

	E remove(int index);

	int size();
}
