import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item>{
	
	private Node<Item> first = null;
	private Node<Item> last = null;
	private int size = 0;
	
	public Deque() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item){
		if(item==null){
			throw new NullPointerException();
		}
		
		Node<Item> newNode = new Node<Item> (item);
		
		if(isEmpty()){
			first = last = newNode;
		}else{
			first.prev = newNode;
			newNode.next = first;
			first = newNode;
			
		}
		
		size++;
	}
	  
	public void addLast(Item item){
		if(item==null){
			throw new NullPointerException();
		}
		
		Node<Item> newNode = new Node<Item> (item);
		
		if(isEmpty()){
			first = last = newNode;
		}else{
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
		}
		
		size++;
	}
	
	public Item removeFirst(){
		if(first==null){
			throw new NoSuchElementException();
		}
		
		Node<Item> node = first;
		
		if(size>1){
			first.next.prev = null;
			first = first.next;
		}else{
			last=first=null;
		}
		
		size--;
		return node.item;
	}
	
	public Item removeLast(){
		if(last==null){
			throw new NoSuchElementException();
		}
		
		Node<Item> node = last;
		
		if(size>1){
			last.prev.next = null;
			last = last.prev;
		}else{
			last=first=null;
		}
		
		size--;
		return node.item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item>{
		
		private Node<Item> current;
		
		public DequeIterator() {
			current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current!=null;
		}

		@Override
		public Item next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			Item i = current.item;
			current = current.next;
			return i;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private static class Node<Item> {
		Item item;
		Node<Item> prev;
		Node<Item> next;
		
		public Node(Item item) {
			this.item = item;
		}
	}

	
	public static void main(String[] args) {
		
		Deque<Integer> d = new Deque<Integer>();
		d.addLast(1);
		d.addLast(2);
		d.addLast(3);
		d.addLast(4);
		d.addFirst(5);
		d.addFirst(6);
		d.addFirst(7);
		
		for(Integer i : d){
			System.out.println(i);
		}
		
	}
}
