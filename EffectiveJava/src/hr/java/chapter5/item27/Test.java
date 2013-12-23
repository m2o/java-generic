package hr.java.chapter5.item27;

public class Test {

	
	private static Function<Object> unary = new Function<Object>() {
		@Override
		public Object apply(Object arg) {
			return arg;
		}
	};
	
	public static void main(String[] args) {
		
		Function<Integer> fInteger = getUnaryFunction();
		System.out.println(fInteger.apply(1));
		
		Function<String> fString = getUnaryFunction();
		System.out.println(fString.apply("hello"));
	}

	@SuppressWarnings("unchecked")
	public static <T> Function<T> getUnaryFunction() {
		return (Function<T>)unary;
	}
}
