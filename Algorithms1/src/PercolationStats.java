public class PercolationStats {

	private int T;
	private int N;
	private double mean;
	private double stddev;
	private double confLo;
	private double confHi;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		
		if(N<=0){
			throw new IllegalArgumentException("expected positive integer for N");
		}
		if(T<=0){
			throw new IllegalArgumentException("expected positive integer for T");
		}
		
		this.N = N;
		this.T = T;
		
		run();
	}

	private void run() {
		
		double[] results = new double[T];
		
		for(int i=0;i<T;i++){
			results[i] = (double)runsingle();
		}
		
		this.mean = StdStats.mean(results);
		this.stddev = StdStats.stddev(results);
		this.confLo = this.mean - 1.96*this.stddev / Math.sqrt(this.T);
		this.confHi = this.mean - 1.96*this.stddev / Math.sqrt(this.T);
	}

	private double runsingle() {

		Percolation p = new Percolation(this.N);
		
		while(!p.percolates()){
			p.open(StdRandom.uniform(1, N+1),StdRandom.uniform(1, N+1));
		}
		
		int openCnt = 0;
		
		for(int i=1;i<=this.N;i++){
			for(int j=1;j<=this.N;j++){
				if(p.isOpen(i, j)){
					openCnt++;
				}
			}
		}
		
		return ((double) openCnt) / (N*N) ;
	}

	// sample mean of percolation threshold
	public double mean() {
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return stddev;
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return confLo;
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi() {
		return confHi;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		
		PercolationStats stats = new PercolationStats(N, T);
		stats.run();
		
		System.out.printf("mean\t\t\t= %f\n", stats.mean());
		System.out.printf("stddev\t\t\t= %f\n", stats.stddev());
		System.out.printf("95%% confidence interval\t= %f, %f\n", stats.confidenceLo(),stats.confidenceHi());
	}
	
	

}
