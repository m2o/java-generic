package hr.fer.coursera.algorithms1.unionfind;

public class QuickUnionUnionFind implements UnionFind{
	
	private int n;
	private int[] data;
	
	public QuickUnionUnionFind(int n) {
		this.n = n;
		this.data = new int[n];
		for(int i=0;i<n;i++){
			this.data[i] = i;
		}
	}

	@Override
	public void union(int a, int b) {
		int rootA = findRoot(a);
		int rootB = findRoot(b);
		if(rootA!=rootB){
			this.data[rootA] = rootB;
		}
	}

	@Override
	public boolean connected(int a, int b) {
		return findRoot(a)==findRoot(b);
	}
	
	private int findRoot(int elem) {
		int currentElem = elem;
		while(currentElem!=data[currentElem]){
			currentElem = data[currentElem];
		}
		return currentElem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int n = 10;
		UnionFind uf = new QuickUnionUnionFind(n);
		uf.union(0, 1);
		uf.union(4, 5);
		uf.union(6, 9);
		uf.union(2, 9);
		uf.union(1, 2);
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				boolean con = uf.connected(i, j);
				if(con){
					System.out.println(String.format("%d-%d",i,j));
				}
			}
		}
		//System.out.println(uf.connected(0, 6));
		//System.out.println(uf.connected(3, 9));
		
	}

}
