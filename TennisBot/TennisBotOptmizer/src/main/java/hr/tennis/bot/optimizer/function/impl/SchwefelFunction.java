package hr.tennis.bot.optimizer.function.impl;


import hr.tennis.bot.optimizer.function.Function;

import java.util.Arrays;

/**
 * Schwefelova funkcija varijabilnog broja ulaznih varijabli.
 * @author toni
 */
public class SchwefelFunction implements Function {
	
	/**
	 * Broj ulaznih varijabli.
	 */
	private int variableCount;
	
	/**
	 * @param variableCount broj ulaznih varijabli.
	 */
	public SchwefelFunction(int variableCount) {
		this.variableCount=variableCount;
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
		
		double tot=0;
		
		for(int i=0;i<variableCount;i++){
			tot+=-vars[i]*Math.sin(Math.sqrt(Math.abs(vars[i])));
		}
		
		return tot/variableCount;
	}

	@Override
	public String toString() {
		return "Schwefel function";
	}
	
	public static void main(String[] args) {
		
		SchwefelFunction func = new SchwefelFunction(3);
		
		double[] x={430.0,410.0,430.0};
		System.out.printf("y(%s)=%f\n",Arrays.toString(x),func.value(x));
	}
}
