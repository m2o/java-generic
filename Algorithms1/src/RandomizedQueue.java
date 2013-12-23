import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size = 0;
	private int reservedSize = 10;
	private Item[] data = (Item[]) new Object[reservedSize];
	
	public RandomizedQueue() {
		
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	private boolean isFull(){
		return size == reservedSize;
	}
	
	private boolean shouldEnlarge(){
		return isFull();
	}
	
	private boolean shouldShrink(){
		return size < reservedSize/4.0;
	}
	
	public int size(){
		return size;
	}
	
	private void resize(int newReservedSize) {
		 data = Arrays.copyOf(data, newReservedSize);
		 reservedSize = newReservedSize;
	}
	
	public void enqueue(Item item){
		
		if(item == null){
			throw new NullPointerException();
		}
		
		if(isEmpty()){
			data[0]=item;
		}else{
			int insertIx = size;
			int swapIx = StdRandom.uniform(size+1);
			
			data[insertIx] = data[swapIx];
			data[swapIx] = item;
		}
		
		size++;

		if(shouldEnlarge()){
			resize(reservedSize*2);
		}
	}

	public Item dequeue(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		int itemIx = size-1;
		Item item = data[itemIx];
		data[itemIx] = null;
		size--;
		if(shouldShrink()){
			resize(reservedSize/2);
		}
		return item;
	}
	
	public Item sample(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		int itemIx = StdRandom.uniform(size);
		return data[itemIx];
	}

	@Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
	
	private class RandomizedQueueIterator implements Iterator<Item>{
		
		private int nextIx = 0;
		private int[] iterOrder;
		
		public RandomizedQueueIterator() {
			iterOrder = new int[size];
			
			for(int i=0;i<size;i++){
				iterOrder[i]=i;
			}
			
			StdRandom.shuffle(iterOrder);
		}
		
		@Override
		public boolean hasNext() {
			return nextIx<size;
		}

		@Override
		public Item next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return data[iterOrder[nextIx++]];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		
		RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
		for(int i=0;i<10;i++){
			d.enqueue(i);
		}
		
		for(int i : d){
			System.out.println(i);
		}
		for(int i : d){
			System.out.println(i);
		}
		
//		for(int i=0;i<100;i++){
//			System.out.println(d.dequeue());
//		}
		
	}
}
