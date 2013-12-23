package hr.fer.guavatest;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

public class OptionalTest {

	public static Optional<String> getName(){
		return Optional.of("hello");
	}
	
	public static Optional<String> getName2(){
		return Optional.absent();
	}
	
	public static void main(String[] args) {
		
		Optional<String> name = getName();
		System.out.println(name.isPresent());
		System.out.println(name.get());
		
		name = getName2();
		System.out.println(name.isPresent());
		try {
			System.out.println(name.get());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println(name.or("default name"));
		System.out.println(name.or(new Supplier<String>() {

			public String get() {
				return "default name from supplier";
			}

		}));
	}
}
