package hr.java.item49;

public class Test {

	private static final long MAX_VALUE = 100000000;

	public static void main(String[] args) {
		
		loopSlow();
		loopFast();
	}

	private static void loopSlow() {
		long timeStart = System.currentTimeMillis();
		
		Long sum  = 0L;
		
		for(long i=0;i<MAX_VALUE;i++){
			sum = sum + i; //sum = box(unbox(sum)+i)
		}
		
		long duration = (System.currentTimeMillis()-timeStart);
		System.out.printf("duration:%d\n",duration);
	}
	
	private static void loopFast() {
		long timeStart = System.currentTimeMillis();
		
		long sum  = 0L;
		
		for(long i=0;i<MAX_VALUE;i++){
			sum = sum + i; //no [un]boxing
		}
		
		long duration = (System.currentTimeMillis()-timeStart);
		System.out.printf("duration:%d\n",duration);
	}
	
}
