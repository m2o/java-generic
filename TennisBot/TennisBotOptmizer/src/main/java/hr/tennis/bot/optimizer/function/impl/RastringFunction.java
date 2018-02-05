package hr.tennis.bot.optimizer.function.impl;


import hr.tennis.bot.optimizer.function.Function;

import java.util.Arrays;

/**
 * Rastringova funkcija varijabilnog broja ulaznih varijabli.
 * @author toni
 */
public class RastringFunction implements Function {
	
	/**
	 * Broj ulaznih varijabli.
	 */
	private int variableCount;
	
	/**
	 * @param variableCount broj ulaznih varijabli.
	 */
	public RastringFunction(int variableCount) {
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
		
		double tot=10*variableCount;
		
		for(int i=0;i<variableCount;i++){
			tot+=vars[i]*vars[i]-10*Math.cos(2*Math.PI*vars[i]);
		}
		
		return tot;
	}

	@Override
	public String toString() {
		return "Rastring function";
	}
	
	public static void main(String[] args) {
		
		RastringFunction func = new RastringFunction(3);
		
		double[] x={0.0,0.0,0.0};
		System.out.printf("y(%s)=%f\n",Arrays.toString(x),func.value(x));
	}

	public Function negate() {
		return new NegativeFunction(this);
	}
}
