package hr.tennis.bot.learner.result.scenario;

public interface IScenario {

	public void init();
	public void classified(int prediction, int expectedClass,double probability, double[] coeffients);
	public void printStatus();
	public double getAmountDifference();

}
