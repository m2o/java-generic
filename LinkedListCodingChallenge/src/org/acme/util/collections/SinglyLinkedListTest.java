/**
 * 
 */
package org.acme.util.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class that test the functionality of
 * {@link org.acme.util.collections.SinglyLinkedList}.
 * 
 * @author Toni Pivčević
 * 
 */
public class SinglyLinkedListTest {

	private List<Integer> empty;
	private List<Integer> prepop;
	private List<Integer> sparse;

	@Before
	public void setUp() throws Exception {
		this.empty = new SinglyLinkedList<Integer>();
		this.prepop = SinglyLinkedList.of(10, 20, 30);
		this.sparse = SinglyLinkedList.of(1, null, 2, null, 3);
	}

	@After
	public void tearDown() throws Exception {
		this.empty = null;
		this.prepop = null;
	}

	@Test
	public void testSetUp() {
		assertNotNull(this.empty);
		assertEquals(0, this.empty.size());

		assertNotNull(this.prepop);
		assertEquals(3, this.prepop.size());

		assertNotNull(this.sparse);
		assertEquals(5, this.sparse.size());
	}

	@Test
	public void testAdd() {

		Integer elem = 1;
		assertEquals(0, this.empty.size());
		assertTrue(this.empty.add(elem));
		assertEquals(1, this.empty.size());
		assertEquals(elem, this.empty.get(0));

		elem = 40;
		assertEquals(3, this.prepop.size());
		assertTrue(this.prepop.add(elem));
		assertEquals(4, this.prepop.size());
		assertEquals(elem, this.prepop.get(3));
	}

	@Test
	public void testAddDuplicate() {

		Integer elem = 40;

		assertTrue(this.prepop.add(elem));
		assertEquals(4, this.prepop.size());
		assertEquals(elem, this.prepop.get(3));

		assertTrue(this.prepop.add(elem));
		assertEquals(5, this.prepop.size());
		assertEquals(elem, this.prepop.get(4));
	}

	@Test
	public void testAddNull() {

		Integer elem = null;

		assertTrue(this.prepop.add(elem));
		assertEquals(4, this.prepop.size());
		assertEquals(elem, this.prepop.get(3));
	}

	@Test
	public void testAddIndex() {

		Integer elem = 5;
		assertTrue(this.prepop.add(0, elem));
		assertEquals(4, this.prepop.size());
		assertEquals(elem, this.prepop.get(0));

		elem = 15;
		assertTrue(this.prepop.add(2, elem));
		assertEquals(5, this.prepop.size(), 5);
		assertEquals(elem, this.prepop.get(2), elem);

		elem = 35;
		assertTrue(this.prepop.add(5, elem));
		assertEquals(6, this.prepop.size());
		assertEquals(elem, this.prepop.get(5));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddIndexIndexOutOfBoundsExceptionLow() {
		this.prepop.add(-1, 10);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddIndexIndexOutOfBoundsExceptionHigh() {
		this.prepop.add(4, 10);
	}

	@Test
	public void testClear() {
		this.prepop.clear();
		assertEquals(0, this.prepop.size());
		this.prepop.add(1);
		assertEquals(1, this.prepop.size());
	}

	@Test
	public void testContains() {
		assertTrue(this.prepop.contains(10));
		assertFalse(this.prepop.contains(15));
		assertTrue(this.prepop.contains(20));
		assertFalse(this.prepop.contains(null));

		assertFalse(this.empty.contains(10));
		assertFalse(this.empty.contains(15));
		assertFalse(this.empty.contains(20));
		assertFalse(this.empty.contains(null));

		assertFalse(this.sparse.contains(0));
		assertTrue(this.sparse.contains(1));
		assertFalse(this.sparse.contains(4));
		assertTrue(this.sparse.contains(null));
	}

	@Test
	public void testGet() {
		assertEquals((Integer) 10, this.prepop.get(0));
		assertEquals((Integer) 30, this.prepop.get(2));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetIndexOutOfBoundsExceptionLow() {
		this.empty.get(0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetIndexOutOfBoundsExceptionHigh() {
		this.prepop.get(4);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(this.prepop.isEmpty());
		this.prepop.remove(2); // index
		this.prepop.remove(1); // index
		this.prepop.remove(0); // index
		assertTrue(this.prepop.isEmpty());
		this.prepop.add(40);
		assertFalse(this.prepop.isEmpty());

		assertTrue(this.empty.isEmpty());
		this.empty.add(1);
		assertFalse(this.empty.isEmpty());
	}

	@Test
	public void testEquals() {
		assertTrue(this.empty.equals(this.empty));
		assertFalse(SinglyLinkedList.of(1, 2).equals(this.empty));
		assertFalse(this.empty.equals(SinglyLinkedList.of(2, 3)));
		assertFalse(SinglyLinkedList.of(1).equals(SinglyLinkedList.of(2)));

		List<Integer> l1 = SinglyLinkedList.of(1, 2, 3, 4, 5);
		List<Integer> l2 = SinglyLinkedList.of(1, 2, 3, 4, 5);
		assertTrue(l1.equals(l2));

		l1 = SinglyLinkedList.of(1, 2, 3);
		l2 = SinglyLinkedList.of(1, 3, 2);
		assertFalse(l1.equals(l2));
	}

	@Test
	public void testSize() {
		assertEquals(3, this.prepop.size());
		this.prepop.remove(2); // index
		this.prepop.remove(1); // index
		assertEquals(1, this.prepop.size());
		this.prepop.remove(0); // index
		assertEquals(0, this.prepop.size());
		this.prepop.add(40);
		assertEquals(1, this.prepop.size());

		assertEquals(0, this.empty.size());
		this.empty.add(1);
		assertEquals(1, this.empty.size());
	}

	@Test
	public void testRemove() {

		assertEquals((Integer) 10, this.prepop.get(0));
		assertTrue(this.prepop.remove((Integer) 10)); // removing element 10
														// (Integer), not at
														// index 10 (int)
		assertEquals((Integer) 20, this.prepop.get(0));
		assertEquals(this.prepop.size(), 2);

		assertEquals((Integer) 30, this.prepop.get(1));
		assertTrue(this.prepop.remove((Integer) 30)); // removing element 10
														// (Integer), not at
														// index 10 (int)

		assertEquals((Integer) 20, this.prepop.get(0));
		assertEquals(1, this.prepop.size());

		assertFalse(this.empty.remove((Integer) 100)); // removing element 100
														// (Integer), not at
														// index 100 (int)
	}

	@Test
	public void testRemoveIndex() {

		assertEquals((Integer) 10, this.prepop.get(0));
		this.prepop.remove(0); // removing element at index 0, not element 0
								// (Integer))
		assertEquals((Integer) 20, this.prepop.get(0));
		assertEquals(this.prepop.size(), 2);

		assertEquals((Integer) 30, this.prepop.get(1));
		this.prepop.remove(1); // removing element at index 1, not element 1
								// (Integer))
		assertEquals((Integer) 20, this.prepop.get(0));
		assertEquals(1, this.prepop.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIndexOutOfBoundsExceptionLow() {
		this.prepop.remove(-1); // removing element at index -1
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIndexOutOfBoundsExceptionHigh() {
		this.prepop.get(4); // removing element at index 4
	}
}
