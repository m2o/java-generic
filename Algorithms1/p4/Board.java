import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Board {

	private int[][] blocks;
	private int n;

	public Board() {

	}
	
	public Board(int[][] blocks) {
		this.blocks = copyArray(blocks,blocks.length);;
		this.n = this.blocks.length;
	}

	// board dimension N
    public int dimension(){
    	return n;
    }
    
    // number of blocks out of place
    public int hamming(){
    	
    	int expected=1;
    	int incorrect=0;
    	
	    for (int i = 0; i < n; i++){
	        for (int j = 0; j < n; j++){
	        	int current = blocks[i][j];
	        	if(current!=0 && current!=expected){
	        		incorrect++;
	        	}
	        	expected++;
	        }
	    }
	    return incorrect;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan(){
    	
    	int distance = 0;

	    for (int i = 0; i < n; i++){
	        for (int j = 0; j < n; j++){
	        	int current = blocks[i][j];
	        	if(current!=0){
	        		int expectedJ = (current-1)%n;
	        		int expectedI = (current-1)/n;
	        		
	        		distance += Math.abs(expectedJ-j) + Math.abs(expectedI-i);
	        	}
	        }
	    }
	    
	    return distance;
    	
    }
    
    // is this board the goal board?
    public boolean isGoal(){
    	return hamming()==0;
    }
    
	// a board obtained by exchanging two adjacent blocks in the same row
    public Board twin(){
    	Board b = this.copy();
    	
    	boolean done = false;
    	int emptyI = -1,emptyJ1 = -1,emptyJ2 = -1;

	    for (int i = 0; i < n; i++){
	    	emptyI = -1;
	    	emptyJ1 = -1;
	    	emptyJ2 = -1;
	        for (int j = 0; j < n; j++){
	        	if(blocks[i][j]!=0){
	        		if(emptyJ1<0){
	        			emptyJ1 = j; 
	        		}else if(emptyJ2<0){
	        			emptyJ2 = j;
	        			emptyI = i;
	        			done = true;
	        			break;
	        		}
	        	}else{
	    	    	emptyI = -1;
	    	    	emptyJ1 = -1;
	    	    	emptyJ2 = -1;
	        	}
	        }
	        if(done){
	        	break;
	        }
	    }
	    
	    swap(b.blocks, emptyI, emptyJ1, emptyI, emptyJ2);
	    return b;
    }
    
    private Board copy(){
    	
	    int[][] blocks = copyArray(this.blocks,n);
    	
    	Board b = new Board();
    	b.n = this.n;
    	b.blocks = blocks;
    	return b;
    }

	private static int[][] copyArray(int[][] oblocks,int n) {
		int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++){
	        for (int j = 0; j < n; j++){
	            blocks[i][j] = oblocks[i][j];
	        }
	    }
		return blocks;
	}
    
    // all neighboring boards
    public Iterable<Board> neighbors(){
    	
    	boolean done = false;
    	int emptyI = -1,emptyJ = -1;
    	
	    for (int i = 0; i < n; i++){
	        for (int j = 0; j < n; j++){
	        	if(blocks[i][j]==0){
	        		emptyI = i;
	        		emptyJ = j;
	        		done=true;
	        		break;
	        	}
	        }
	        if(done){
	        	break;
	        }
	    }
    	
    	List<Board> boards = new LinkedList<Board>();
    	
    	if(emptyI>0){
    		Board b = this.copy();
    		swap(b.blocks,emptyI,emptyJ,emptyI-1,emptyJ);
    		boards.add(b);
    	}
    	if(emptyI<n-1){
    		Board b = this.copy();
    		swap(b.blocks,emptyI,emptyJ,emptyI+1,emptyJ);
    		boards.add(b);
    	}
    	
    	if(emptyJ>0){
    		Board b = this.copy();
    		swap(b.blocks,emptyI,emptyJ,emptyI,emptyJ-1);
    		boards.add(b);
    	}
    	if(emptyJ<n-1){
    		Board b = this.copy();
    		swap(b.blocks,emptyI,emptyJ,emptyI,emptyJ+1);
    		boards.add(b);
    	}
    	
    	return boards;
    }
    
    private static void swap(int[][] blocks, int i1, int j1, int i2,int j2) {
    	int temp = blocks[i1][j1];
		blocks[i1][j1] = blocks[i2][j2] ;
		blocks[i2][j2] = temp;
	}
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		
		if(this.n!=other.n){
			return false;
		}
		
		for(int i=0;i<n;i++){
			if(!Arrays.equals(blocks[i],other.blocks[i])){
				return false;
			}
		}
		return true;
	}

	// string representation of the board (in the output format specified below)
    public String toString(){
    	
    	StringBuilder b = new StringBuilder();
    	b.append(n);
    	b.append('\n');
    	
	    for (int i = 0; i < n; i++){
	        for (int j = 0; j < n; j++){
	        	b.append(blocks[i][j]);
	        	b.append(' ');
	        }
	        b.append('\n');
	    }
	    return b.toString();
    }
}
