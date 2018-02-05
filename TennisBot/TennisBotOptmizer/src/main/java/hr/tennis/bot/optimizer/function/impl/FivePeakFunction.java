package hr.tennis.bot.optimizer.function.impl;


import hr.tennis.bot.optimizer.function.Function;

import java.util.Arrays;

/**
 * @author toni
 */
public class FivePeakFunction implements Function {
	
	/**
	 * Broj ulaznih varijabli.
	 */
	private int variableCount;
	
	/**
	 * @param variableCount broj ulaznih varijabli.
	 */
	public FivePeakFunction() {
		this.variableCount=1;
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
		
		double x = vars[0];
		double tot=1;
		
		double exp=-2*(x-0.1)/0.8*(x-0.1)/0.8;
		
		tot*=Math.pow(2,exp);
		tot*=Math.pow(Math.sin(5*Math.PI*x),6);
		
		return tot;
	}

	@Override
	public String toString() {
		return "Five peak function";
	}
	
	public static void main(String[] args) {
		
		FivePeakFunction func = new FivePeakFunction();
		
		double[] x={0.0};
		System.out.printf("y(%s)=%f\n",Arrays.toString(x),func.value(x));
	}
}
