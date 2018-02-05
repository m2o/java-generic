package hr.tennis.bot.learner.classification;

import java.io.File;

public interface IClassifier {

	public void classify(double[] input, int dimensions, double[] output);

	public void saveTo(File file);

	public void loadFrom(File file);

}
