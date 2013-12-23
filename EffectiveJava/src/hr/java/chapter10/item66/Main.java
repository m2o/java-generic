package hr.java.chapter10.item66;

import java.util.concurrent.TimeUnit;

public class Main {

	private static boolean counter = false;
	
	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	public void doSomeWork() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 10;
				while(i-- > 0){
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("t counter:"+counter);
				}
			}
		});
		
		t.start();
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		counter = true;
		System.out.println("main counter:"+counter);
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			//ignorable
		}
	}
	
	
	public static void main(String[] args) {
		new Main().doSomeWork();
	}
}
