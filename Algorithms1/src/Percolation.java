

public class Percolation {

	private int n;
	
	private int startIx;
	private int endIx;

	private boolean[] openSites;

	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufFull;

	// create N-by-N grid, with all sites blocked
	public Percolation(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException("expected positive non-zero integer for n");
		}
		this.n = n;
		this.startIx = 0;
		this.endIx = n*n+2-1;
		this.openSites = new boolean[endIx+1];
		this.uf = new WeightedQuickUnionUF(endIx+1);
		this.ufFull = new WeightedQuickUnionUF(endIx);
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		
		int thisIx = toIx(i, j);
		
		if (openSites[thisIx]) {
			return;
		}
		
		//left neighbour
		if(j>1 && isOpen(i,j-1)){
			int leftIx = toIx(i,j-1);
			uf.union(thisIx,leftIx);
			ufFull.union(thisIx,leftIx);
		}
		
		//right neighbour
		if(j<n && isOpen(i,j+1)){
			int rightIx = toIx(i,j+1);
			uf.union(thisIx,rightIx);
			ufFull.union(thisIx,rightIx);
		}
		
		//top neighbour
		if(i>1 && isOpen(i-1,j)){
			int topIx = toIx(i-1,j);
			uf.union(thisIx,topIx);
			ufFull.union(thisIx,topIx);
		}
		
		//bottom neighbour
		if(i<n && isOpen(i+1,j)){
			int bottomIx = toIx(i+1,j);
			uf.union(thisIx,bottomIx);
			ufFull.union(thisIx,bottomIx);
		}
		
		if(i==1){
			uf.union(thisIx,startIx); //start
			ufFull.union(thisIx,startIx);
		}
		if(i==n){
			uf.union(thisIx,endIx); //end
		}
		
		openSites[thisIx] = true;
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return openSites[toIx(i,j)];
	}

	private int toIx(int i, int j) {
		
		if(i<1 || i>n){
			throw new IndexOutOfBoundsException(String.format("expected index [%d,%d]",1,n));
		}
		if(j<1 || j>n){
			throw new IndexOutOfBoundsException(String.format("expected index [%d,%d]",1,n));
		}
		
		return (i-1)*n+(j-1)+1;
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return this.ufFull.connected(toIx(i, j), startIx);
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(startIx, endIx);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Percolation p = new Percolation(4);
		p.open(1, 1);
		p.open(2, 1);
		p.open(2, 1);
		p.open(3, 1);
		p.open(3, 4);
		p.open(4, 1);
		p.open(4, 2);
		p.open(4, 3);
		p.open(4, 4);
		
//		PercolationVisualizer.draw(p, 4);
		
		System.out.println("percolates: "+p.percolates());
		System.out.println(p.isOpen(1, 1));
		System.out.println(p.isFull(1, 1));
//		System.out.println(p.isOpen(2, 1));
//		System.out.println(p.isFull(4, 1));
//		System.out.println(p.isFull(4, 4));
//		
//		WeightedQuickUnionUF w = new WeightedQuickUnionUF(10);
//		w.union(2,3);
//		w.union(1,9);
//		w.union(1,6);
//		w.union(6,5);
//		w.union(7,4);
//		w.union(7,2);
//		w.union(6,2);
//		w.union(8,6);
//		w.union(0,7);
//		
//		System.out.println(w);
		
	}

}
