import java.util.Arrays;


public class Subset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Integer k = Integer.valueOf(args[0]);
		
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		
//		String v;
//		while(true){
//			System.out.printf(">%s<\n",StdIn.readString());
//		}
		
		String line = StdIn.readLine();
		
		int c=0;
		for(String s : line.split("\\s+")){
			q.enqueue(s);
			c++;
//			if(c>k){
//				q.dequeue();
//			}
		}
		
		for(int i=0;i<k;i++){
			System.out.println(q.dequeue());
		}
	}

}
