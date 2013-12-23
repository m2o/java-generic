package hr.main;

public class Resource implements AutoCloseable{

	public Resource() {
		System.out.println("resource opened");
	}

	@Override
	public void close() throws Exception {
		System.out.println("resource closed");
	}

	public void work() {
		System.out.println("work");
	}
	
	public void work2() {
		throw new RuntimeException("some internal error");
	}
}
