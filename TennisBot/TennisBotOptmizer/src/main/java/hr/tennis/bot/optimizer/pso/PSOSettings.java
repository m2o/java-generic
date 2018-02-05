package hr.tennis.bot.optimizer.pso;

import hr.tennis.bot.optimizer.pso.PSO.PSO_TYPE;
import hr.tennis.bot.optimizer.pso.interfaces.IPSOAdapter;

public class PSOSettings<T> {

	public int particleCount;
	public double[][] xIntervals;
	public double[][] vIntervals;

	public double C1 = 2.05;
	public double C2 = 2.05;

	public boolean useInertia = false;
	public boolean useConstriction = false;

	public double wMax;
	public double wMin;

	public long maxIter = Long.MAX_VALUE;
	public long maxTime = Long.MAX_VALUE;

	public IPSOAdapter<T> adapter;

	public PSO_TYPE type;
	public int localK;

	private Double constrictionFactor;

	public double getConstictionFactor() {

		if(this.constrictionFactor==null){
			double q = this.C1+this.C2;
			this.constrictionFactor = 2.0 / Math.abs(2-q-Math.sqrt(q*q-4*q));
		}

		return this.constrictionFactor;
	}
}
