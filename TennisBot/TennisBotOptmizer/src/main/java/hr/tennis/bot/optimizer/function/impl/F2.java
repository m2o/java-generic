package hr.tennis.bot.optimizer.function.impl;


import hr.tennis.bot.optimizer.function.Function;

import java.util.Arrays;

/**
 * Rastringova funkcija varijabilnog broja ulaznih varijabli.
 * @author toni
 */
public class F2 implements Function {
	
	private double q;
	private double alpha;
	
	/**
	 * Broj ulaznih varijabli.
	 */
	private int variableCount;
	
	public F2(double q,double alpha) {
		this.variableCount=2;
		this.q=q;
		this.alpha=alpha;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.ropaerj.genetic.function.Function#getVariableCount()
	 */
	public int getVariableCount() {
		return variableCount;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.ropaerj.genetic.function.Function#value(double[])
	 */
	@Override
	public double value(double[] vars) {
		
		double x=vars[0];
		double y=vars[1];
		
		double tot=1;
		tot-=Math.pow(x/(1+10*y),alpha);
		tot-=x/(1+10*y)*Math.sin(2*Math.PI*q*x);
		tot*=(1+10*y);

		return tot;
	}

	@Override
	public String toString() {
		return "F2 function";
	}
	
	public static void main(String[] args) {
		
		F2 func = new F2(4,2);
		
		double[] x={0.9,0.6};
		System.out.printf("y(%s)=%f\n",Arrays.toString(x),func.value(x));
	}
}
