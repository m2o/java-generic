package hr.tennis.bot.optimizer.function.impl;


import hr.tennis.bot.optimizer.function.Function;

import java.util.Arrays;

public class YEqualsX implements Function {
	
	/**
	 * Broj ulaznih varijabli.
	 */
	private int variableCount;
	
	/**
	 * @param variableCount broj ulaznih varijabli.
	 */
	public YEqualsX(int variableCount) {
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
		return vars[0];
	}

	@Override
	public String toString() {
		return "y=x function";
	}
	
	public static void main(String[] args) {
		
		YEqualsX func = new YEqualsX(3);
		
		double[] x={0.0,0.0,0.0};
		System.out.printf("y(%s)=%f\n",Arrays.toString(x),func.value(x));
	}
}
