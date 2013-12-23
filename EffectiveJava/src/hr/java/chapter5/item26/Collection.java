package hr.java.chapter5.item26;

public class Collection<E> {
	
	private E[] elementsA;
	private Object[] elementsB;
	
	private int sizeA = 0;
	private int sizeB = 0;
	
	//only the push(E) method will be used to insert values into the 
	//array so type safety is ensured. The runtime type of the array
	//will always be Object[], not E[].
	@SuppressWarnings("unchecked")
	public Collection() {
		//this.elements = new E[10];  //doesn't compile
		this.elementsA = (E[]) new Object[10];  //compiles, doesn't fail at runtime because of erasure (no cast at runtime)
		this.elementsB = new Object[10]; 
	}
	
	public void pushB(E i) {
		elementsB[sizeA++] = i;
	}

	public void pushA(E i) {
		elementsA[sizeB++] = i;
	}
	
	//only the push(E) method will be used to insert values into the 
	//array so type safety is ensured.
	@SuppressWarnings("unchecked")
	public E popB() {
		return (E) elementsB[--sizeB];
	}

	public E popA() {
		return elementsA[--sizeA];
	}
	
	public static void main(String[] args) {
		Collection<Integer> c = new Collection<Integer>();
		
		c.pushA(10);
		c.pushB(12);
		
		System.out.println(c.popA());
		System.out.println(c.popB());
		
		//Number[] n = (Number[]) new Object[10]; //compile, fails at runtime
	}
}
