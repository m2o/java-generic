package hr.fer.coursera.algorithms1.unionfind;

public class QuickFindUnionFind implements UnionFind{
	
	private int n;
	private int[] data;
	
	public QuickFindUnionFind(int n) {
		this.n = n;
		this.data = new int[n];
		for(int i=0;i<n;i++){
			this.data[i] = i;
		}
	}

	@Override
	public void union(int a, int b) {

		int vA = data[a];
		int vB = data[b];
		
		for(int i=0;i<n;i++){
			if(data[i]==vB){
				data[i] = vA;
			}
		}
	}

	@Override
	public boolean connected(int a, int b) {
		return data[a]==data[b];
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int n = 10;
		UnionFind uf = new QuickFindUnionFind(n);
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
