package hr.fer.guavatest.collections;

import java.util.ArrayList;
import java.util.List;


public class ListCustomCollectionImpl<E> extends CustomCollectionImpl<E>{

	@Override
	public List<E> values() {
		return new ArrayList<E>();
	}


	public static void main(String[] args) {

		CustomCollectionImpl<Integer> c = new ListCustomCollectionImpl<Integer>();
		System.out.println(c.values().getClass());

		CustomCollectionImpl<Integer> c2 = new CustomCollectionImpl<Integer>();
		System.out.println(c2.values().getClass());

	}
}
