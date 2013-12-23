package org.acme.util.collections;

import org.acme.util.collections.SinglyLinkedList.Node;

/**
 * Static utility class with service methods applied on SingleLinkedLists.
 * 
 * @author Toni Pivčević
 * 
 */
public class SinglyLinkedLists {

	/**
	 * Reversed the given single linked list inplace (iteratively).
	 * 
	 * @param list
	 *            list to reverse
	 */
	public static <E> void reverseInPlace(SinglyLinkedList<E> list) {

		if (list.size() <= 1) {
			return;
		}

		Node<E> prev = list.head;
		Node<E> current = list.head.next;
		Node<E> next;

		prev.next = null;
		list.tail = prev;

		while (current != null) {

			next = current.next;
			current.next = prev;

			current = next;
			prev = current;
		}

		list.head = prev;
	}

	/**
	 * Returns a reverse copy of the given list (recursively).
	 * 
	 * @param list
	 *            list to reverse
	 * @return copy of the given list reversed
	 */
	public static <E> SinglyLinkedList<E> reverse(SinglyLinkedList<E> list) {

		if (list.size() <= 1) {
			return list;
		} else {
			E value = list.get(0);
			SinglyLinkedList<E> reversedList = reverse(list.tailList());
			reversedList.add(value);
			return reversedList;
		}
	}
}
