package hr.fer.guavatest.collections;

import java.util.Collection;
import java.util.HashSet;

public class CustomCollectionImpl<E>{

	public Collection<E> values(){
		return new HashSet<E>();
	}
}
