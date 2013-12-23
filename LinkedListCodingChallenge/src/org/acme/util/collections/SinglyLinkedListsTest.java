package org.acme.util.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class that test the functionality of
 * {@link org.acme.util.collections.SinglyLinkedLists}.
 * 
 * @author Toni Pivčević
 * 
 */
public class SinglyLinkedListsTest {

	@Test
	public void testReverseInPlace() {
		SinglyLinkedList<Integer> origList;

		origList = SinglyLinkedList.of();
		SinglyLinkedLists.reverseInPlace(origList);
		assertEquals(origList, SinglyLinkedList.of());

		origList = SinglyLinkedList.of(10);
		SinglyLinkedLists.reverseInPlace(origList);
		assertEquals(origList, SinglyLinkedList.of(10));

		origList = SinglyLinkedList.of(10, 20);
		SinglyLinkedLists.reverseInPlace(origList);
		assertEquals(origList, SinglyLinkedList.of(20, 10));

		origList = SinglyLinkedList.of(10, 20, 30, 40);
		SinglyLinkedLists.reverseInPlace(origList);
		assertEquals(origList, SinglyLinkedList.of(40, 30, 20, 10));
	}

	@Test
	public void testReverse() {

		SinglyLinkedList<Integer> origList, reversedList;

		origList = SinglyLinkedList.of();
		reversedList = SinglyLinkedLists.reverse(origList);
		assertEquals(reversedList, SinglyLinkedList.of());

		origList = SinglyLinkedList.of(10);
		reversedList = SinglyLinkedLists.reverse(origList);
		assertEquals(reversedList, SinglyLinkedList.of(10));

		origList = SinglyLinkedList.of(10, 20);
		reversedList = SinglyLinkedLists.reverse(origList);
		assertEquals(reversedList, SinglyLinkedList.of(20, 10));

		origList = SinglyLinkedList.of(10, 20, 30, 40);
		reversedList = SinglyLinkedLists.reverse(origList);
		assertEquals(reversedList, SinglyLinkedList.of(40, 30, 20, 10));

	}
}
