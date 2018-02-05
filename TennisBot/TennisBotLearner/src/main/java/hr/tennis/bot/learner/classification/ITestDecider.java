package hr.tennis.bot.learner.classification;

public interface ITestDecider {
	public boolean isTestable(double[] coefs, double[] features);
}
