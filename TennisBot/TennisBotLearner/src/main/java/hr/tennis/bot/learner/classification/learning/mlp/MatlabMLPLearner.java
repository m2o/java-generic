package hr.tennis.bot.learner.classification.learning.mlp;


import hr.tennis.bot.learner.classification.classifiers.MLPClassifier;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.IOException;

public class MatlabMLPLearner {

	public static MLPClassifier createMLP(File w1File,File b1File,File w2File,File b2File) throws IOException {

		double[][] w1 = ClassifyUtils.doubleArrayListTo2DimDoubleArray(ClassifyUtils.loadDoubleArrayListFromFile(w1File));
		double[] b1 = ClassifyUtils.loadDoubleArrayFromFile(b1File);
		double[][] w2 = ClassifyUtils.doubleArrayListTo2DimDoubleArray(ClassifyUtils.loadDoubleArrayListFromFile(w2File));
		double[] b2 = ClassifyUtils.loadDoubleArrayFromFile(b2File);

		MLPClassifier mlp = new MLPClassifier(w1, b1, w2, b2);
		return mlp;
	}

	public static void learnMLP(Gender gender,TournamentType type) throws IOException{
		FileSet set = FileSet.fromType(gender, type);
		MLPClassifier mlp = createMLP(set.matlabMLPw1,set.matlabMLPb1,set.matlabMLPw2,set.matlabMLPb2);
		mlp.saveTo(set.classifierMLP);
		System.out.println(set.classifierMLP.getAbsolutePath()+" created");
	}

	public static void main(String[] args) throws IOException {
		learnMLP(Gender.MALE, TournamentType.SINGLE);
		//learnMLP(Gender.FEMALE, TournamentType.SINGLE);
	}
}
