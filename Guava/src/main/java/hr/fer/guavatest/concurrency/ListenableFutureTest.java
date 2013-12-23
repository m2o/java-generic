package hr.fer.guavatest.concurrency;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenableFutureTest {
	
	private static class Squarer implements Callable<Double> {

		private double value;

		public Squarer(double value) {
			this.value = value;
		}

		public Double call() throws Exception {
			//Thread.sleep((long) (value*100));
			
			if(((int)value) % 10 == 1){
				throw new IllegalArgumentException("illegal value:"+this.value);
			}
			return this.value*this.value;
		}
	}

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		double result = 0;
		
		ListeningExecutorService listExec = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
		
		for(int i=0;i<1000;i++){
			ListenableFuture<Double> future = listExec.submit(new Squarer((double)i));
			Futures.addCallback(future, new FutureCallback<Double>() {

				public void onSuccess(Double result) {
					System.out.println(result);
				}

				public void onFailure(Throwable t) {
					System.err.println(t.toString());
				}
				
			});
		}
		
		listExec.shutdown();
		System.out.println();
	}

}
