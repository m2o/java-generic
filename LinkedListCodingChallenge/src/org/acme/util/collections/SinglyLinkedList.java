package org.acme.util.collections;

/**
 * An implementation of a singly linked list.
 * 
 * @param <E>
 *            the type of elements in this collection
 * @author Toni Pivčević
 */
public class SinglyLinkedList<E> implements List<E> {

	/**
	 * Head node of list.
	 */
	Node<E> head = null;

	/**
	 * Tail node of list.
	 */
	Node<E> tail = null;

	/**
	 * Current number of elements in list.
	 */
	int size = 0;

	/**
	 * Default constructor, creates an empty list.
	 */
	public SinglyLinkedList() {

	}

	/**
	 * Static factory method that creates a list with the given variable number
	 * of elements.
	 * 
	 * @param elements
	 *            elements to add to list
	 * @return newly created list
	 */
	public static <E> SinglyLinkedList<E> of(E... elements) {
		SinglyLinkedList<E> newList = new SinglyLinkedList<E>();
		for (E element : elements) {
			newList.add(element);
		}
		return newList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(E element) {
		Node<E> node = Node.instance(element);

		if (isEmpty()) {
			this.head = this.tail = node;
		} else {
			this.tail.next = node;
			this.tail = node;
		}

		this.size++;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#add(int, java.lang.Object)
	 */
	@Override
	public boolean add(int index, E element) {

		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}

		boolean insertAtFront = index == 0;
		Node<E> newNode = Node.instance(element);

		if (insertAtFront) {
			newNode.next = this.head;
			this.head = newNode;
		} else {
			Node<E> prevNode = getNodeByIndex(index - 1);
			newNode.next = prevNode.next;
			prevNode.next = newNode;
		}

		if (newNode.next == null) {
			this.tail = newNode; // set tail reference if required
		}

		this.size++;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#clear()
	 */
	@Override
	public void clear() {
		this.head = this.tail = null;
		this.size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(E element) {
		Node<E> current = this.head;
		while (current != null) {
			if (objectEquals(current.value, element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#get(int)
	 */
	@Override
	public E get(int index) {
		return getNodeByIndex(index).value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object element) {

		if (isEmpty()) {
			return false;
		}

		Node<E> prev = null;
		Node<E> current = this.head;
		while (current != null && !objectEquals(current.value, element)) {
			prev = current;
			current = current.next;
		}

		if (current == null) {
			return false; // no such element to remove
		}

		boolean removingHead = prev == null;

		if (removingHead) {
			this.head = this.head.next;
		} else {
			prev.next = current.next;
		}

		if (current.next == null) {
			this.tail = prev; // set tail reference if required
		}

		this.size--;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#remove(int)
	 */
	@Override
	public E remove(int index) {

		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}

		boolean removingHead = index == 0;

		Node<E> prev = null;
		Node<E> current = this.head;
		for (int i = index; i > 0; i--) {
			prev = current;
			current = current.next;
		}

		if (removingHead) {
			this.head = this.head.next;
		} else {
			prev.next = current.next;
		}

		if (current.next == null) {
			this.tail = prev; // set tail reference if required
		}

		this.size--;
		return current.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acme.util.collections.List#size()
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns the node at the given index.
	 * 
	 * @param index
	 *            index to fetch
	 * @return node at given index
	 */
	private Node<E> getNodeByIndex(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> current = this.head;
		for (int i = index; i > 0; i--) {
			current = current.next;
		}

		return current;
	}

	/**
	 * Checks if the two given objects are equal. Supports null objects.
	 * 
	 * @param a
	 *            first object
	 * @param b
	 *            second object
	 * @return whether the 2 given objects are equal
	 */
	private static boolean objectEquals(Object a, Object b) {
		if (a != null) {
			return a.equals(b);
		} else {
			return b == null;
		}
	}

	/**
	 * Returns the tail list of the current list, that is a new list with all
	 * the elements without the head. A package-private method used when
	 * reversing the string recursively.
	 * 
	 * @return new tail list of the current list
	 */
	SinglyLinkedList<E> tailList() {
		if (this.size() <= 1) {
			throw new IllegalStateException(
					"can't call tail list on a empty or 1-element list");
		}

		SinglyLinkedList<E> newTailList = new SinglyLinkedList<E>();
		newTailList.head = this.head.next;
		newTailList.tail = this.tail;
		newTailList.size = this.size - 1;
		return newTailList;
	}

	// hashcode not overriden on purpose
	// list implementation is not to be used in hash-based collections

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		@SuppressWarnings("unchecked")
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) obj;
		if (this.size != other.size) {
			return false;
		}

		Node<E> currentThis = this.head;
		Node<E> currentThat = other.head;

		while (currentThis != null) {
			if (!objectEquals(currentThis.value, currentThat.value)) {
				return false;
			}
			currentThis = currentThis.next;
			currentThat = currentThat.next;
		}

		return true;
	}

	/**
	 * A package-private Node class required for the singly-linked list
	 * implementations.
	 * 
	 * @author Toni Pivčević
	 * 
	 * @param <E>
	 *            the type of elements in this collection
	 */
	static class Node<E> {
		/**
		 * The next node in the list.
		 */
		Node<E> next;
		/**
		 * The value of the element at the current node. Null values are valid.
		 */
		private final E value;

		/**
		 * Default constructor, creates a node with the given element.
		 * 
		 * @param value
		 */
		private Node(E value) {
			this.value = value;
		}

		/**
		 * Static factory creates a node with the given element.
		 * 
		 * @param value
		 *            element at this node
		 * @return new node with given element
		 */
		public static <E> Node<E> instance(E value) {
			return new Node<E>(value);
		}
	}

}
