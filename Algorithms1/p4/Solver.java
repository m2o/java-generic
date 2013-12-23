import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Solver {

	private static final long MAX_QUEUE_SIZE = (long) (Math.pow(2, 20));

	private MinPQ<SearchNode> searchNodeQueue = new MinPQ<SearchNode>();

	private Board initialBoard;
	private List<Board> solutions;
	
	public Solver(Board initialBoard) {
		
		this.initialBoard = initialBoard;
		
		searchNodeQueue.insert(SearchNode.initial(initialBoard));
		searchNodeQueue.insert(SearchNode.initial(initialBoard.twin()));
		solve();
	}
	
	private void solve() {
		
		SearchNode node = null;
		
		while(true){
			node = searchNodeQueue.delMin();
			//System.out.println(node.toString());
			//System.out.println(searchNodeQueue.size());
			//printQueue(searchNodeQueue);
			if(node.isGoal()){
				break;
			}
			
			for(Board b : node.getBoard().neighbors()){
				if(node.isBoardOnSolutionPath(b)){
					continue;
				}
//				if(node.prevNode!=null && node.prevNode.getBoard().equals(b)){
//					continue;
//				}
				searchNodeQueue.insert(node.successor(b));
			}
			
			//cleanup
			if(searchNodeQueue.size()>MAX_QUEUE_SIZE){
				MinPQ<SearchNode> newSearchNodeQueue = new MinPQ<SearchNode>();
				Iterator<SearchNode> itSearchNodeQueue = this.searchNodeQueue.iterator();
				for(int i=0;i<MAX_QUEUE_SIZE/10;i++){
					newSearchNodeQueue.insert(itSearchNodeQueue.next());
				}
				this.searchNodeQueue = newSearchNodeQueue;
//				System.out.println("cleanup!");
			}
		}
		
    	List<Board> boards = new LinkedList<Board>();
    	
    	SearchNode s = node;
    	while(s!=null){
    		boards.add(s.getBoard());
    		s = s.prevNode;
    	}
    	
    	Collections.reverse(boards);
    	
    	if(boards.get(0).equals(initialBoard)){
    		this.solutions = boards;
    	}
	}

	private static void printQueue(MinPQ<SearchNode> searchNodeQueue2) {
		System.out.print("q: ");
		for(SearchNode s : searchNodeQueue2){
			System.out.printf("%s, ",s);
		}
		System.out.println();
	}

	// is the initial board solvable?
    public boolean isSolvable(){
    	return solutions!=null;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves(){
    	return solutions!=null ? this.solutions.size()-1 : -1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution(){
    	return solutions;
    }
    
    private static class SearchNode implements Comparable<SearchNode>{

		private Board board;
		private int moves;
		private SearchNode prevNode;

		private SearchNode() {

		}
		
		public boolean isBoardOnSolutionPath(Board b) {
			if(board.equals(b)){
				return true;
			}
			if(prevNode==null){
				return false;
			}
			return prevNode.isBoardOnSolutionPath(b);
		}

		public Board getBoard() {
			return this.board;
		}

		public boolean isGoal() {
			return board.isGoal();
		}

		public static SearchNode initial(Board b){
			SearchNode s = new SearchNode();
			s.board = b;
			s.moves = 0;
			s.prevNode = null;
			return s;
		}
		
		public SearchNode successor(Board b){
			SearchNode s = new SearchNode();
			s.board = b;
			s.moves = this.moves+1;
			s.prevNode = this;
			return s;
		}

		@Override
		public int compareTo(SearchNode that) {
			return getPiority().compareTo(that.getPiority());
		}

		private Integer getPiority() {
			return this.board.manhattan() + moves;
//			return this.board.hamming() + moves/10;
//			return this.board.hamming() + this.board.manhattan() + moves;
		}
		
		@Override
		public String toString() {
			return String.format("node h:%d m:%d c:%s p:%d", this.board.hamming(),this.board.manhattan(),moves,getPiority());
		}
    }

	public static void main(String[] args) {
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++){
	        for (int j = 0; j < N; j++){
	            blocks[i][j] = in.readInt();
	        }
	    }
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable()){
	        StdOut.println("No solution possible");
	    }else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution()){
	            StdOut.println(board);
	        }
	    }
	}
}
