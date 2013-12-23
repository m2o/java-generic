package hr.fer.coursera.algorithms1.particles;

import java.util.PriorityQueue;

public class PriorityQueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		
		for(int i=10;i>0;i--){
			queue.add(i);
		}
		
		while(!queue.isEmpty()){
			System.out.println(queue.poll());
		}

	}

}
