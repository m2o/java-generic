package hr.fer.guavatest.concurrency;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;

public class FutureTest {
	
	private static class Squarer implements Callable<Double> {

		private double value;

		public Squarer(double value) {
			this.value = value;
		}

		public Double call() throws Exception {
			return this.value*this.value;
		}
	}

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService exec = Executors.newFixedThreadPool(10);
		
		List<Future<Double>> futures = Lists.newLinkedList();
		
		for(int i=0;i<1000;i++){
			futures.add(exec.submit(new Squarer((double)i)));
		}
		
		for(Future<Double> future : futures){
			Double result = future.get();
			System.out.println(result);
		}
		
		exec.shutdown();
	}

}
