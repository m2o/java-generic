package hr.tennis.bot.optimizer.function.impl;

import hr.tennis.bot.optimizer.function.Function;

public class NegativeFunction implements Function {
	
	private Function function;

	public NegativeFunction(Function function) {
		this.function = function;
	}

	@Override
	public double value(double[] variables) {
		return -1*function.value(variables);
	}

	@Override
	public int getVariableCount() {
		return function.getVariableCount();
	}

}
