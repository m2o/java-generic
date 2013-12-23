package hr.java.chapter6.item35;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tester {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		
		Class test = Class.forName(args[0]);
		System.out.println(test);
		
		for(Method m : test.getDeclaredMethods()){

			
			if(m.isAnnotationPresent(Test.class)){
				
				try{
					m.invoke(null);
					System.out.println(m.toString()+" passed");
				}catch(InvocationTargetException wrappedExc){
					Throwable t = wrappedExc.getCause();
					System.out.println(m.toString()+" failed cause:"+t.toString());
				}catch (Exception e) {
					System.out.println(m.toString()+" invalid");
					e.printStackTrace();
				}
			}else if(m.isAnnotationPresent(ExceptionTest.class)){
				
				Class<? extends Exception> expectedExceptions[] = m.getAnnotation(ExceptionTest.class).value();
				
				try{
					m.invoke(null);
					System.out.println(m.toString()+" failed (no exception raised)");
				}catch(InvocationTargetException wrappedExc){
					Throwable t = wrappedExc.getCause();
					
					boolean ok = false;
					for(Class<? extends Exception> expectedException : expectedExceptions){
						if(expectedException.isInstance(t)){
							System.out.println(m.toString()+" passed");
							ok = true;
							break;
						}
					}
					
					if(!ok){
						System.out.println(m.toString()+" failed wrong exception:"+t.toString());
					}
				}catch (Exception e) {
					System.out.println(m.toString()+" invalid");
					e.printStackTrace();
				}
				
			}
		}
	}

}
