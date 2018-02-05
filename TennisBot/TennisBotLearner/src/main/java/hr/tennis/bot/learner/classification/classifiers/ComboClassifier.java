package hr.tennis.bot.learner.classification.classifiers;


import hr.tennis.bot.learner.classification.Classifier;
import hr.tennis.bot.learner.classification.IClassifier;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ComboClassifier extends Classifier{

	private static final long serialVersionUID = 1L;

	private List<IClassifier> classifiers;

	public ComboClassifier(List<IClassifier> classifiers) {
		this.classifiers = classifiers;
	}

	@Override
	public void classify(double[] input, int dimensions, double[] output) {

		Arrays.fill(output,0.0);
		double[] _output = new double[dimensions];

		for(IClassifier classifier : this.classifiers){
			Arrays.fill(_output,0.0);
			classifier.classify(input, dimensions, _output);

			ClassifyUtils.addArray1ToArray2(_output, output, dimensions);
		}

		ArrayUtil.normalizeArray(output,dimensions);
	}

	@Override
	public void loadFrom(File file) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws IOException {


		FileSet set = FileSet.fromType(Gender.MALE, TournamentType.SINGLE);

		IClassifier knn = new KNNClassifier();
		knn.loadFrom(set.classifierKNN);

		IClassifier mlp = new MLPClassifier();
		mlp.loadFrom(set.classifierMLP);

		List<IClassifier> classifierList = new LinkedList<IClassifier>();

		classifierList.add(knn);
		classifierList.add(mlp);

		IClassifier comboClassifier = new ComboClassifier(classifierList);

		//		ClassifierTest.test(comboClassifier,set.rawFeatureTestAllIn,set.rawFeatureTestAllOut,set.normalize,set.rawFeatureTestAllCoefficients,0.75,new ITestDecider() {
		//		//ClassifierTest.test(knn,set.rawFeatureTrainIn,set.rawFeatureTrainOut,set.normalize,0.8,new ITestDecider() {
		//			@Override
		//			public boolean isTestable(double[] coefs,double[] feature) {
		//				return coefs[0]>=1.2;
		//			}
		//		});
	}
}
