package hr.tennis.bot.learner.classification.learning;

import hr.tennis.bot.learner.classification.classifiers.RBNClassifier;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.IOException;

public class RBNLearner {

	public static RBNClassifier createRB(File centersFile,File weightsFile,File centerBiasesFile,File weightsBiasesFile) throws IOException {

		double[][] centers = ClassifyUtils.doubleArrayListTo2DimDoubleArray(ClassifyUtils.loadDoubleArrayListFromFile(centersFile));
		double[][] weights = ClassifyUtils.doubleArrayListTo2DimDoubleArray(ClassifyUtils.loadDoubleArrayListFromFile(weightsFile));
		double[] centersBias = ClassifyUtils.loadDoubleArrayFromFile(centerBiasesFile);
		double[] weightsBias = ClassifyUtils.loadDoubleArrayFromFile(weightsBiasesFile);

		RBNClassifier classifier = new RBNClassifier(centers, weights, centersBias, weightsBias);
		return classifier;
	}

	public static void learnRBN(Gender gender,TournamentType type) throws IOException{
		FileSet set = FileSet.fromType(gender, type);
		RBNClassifier mlp = createRB(set.matlabRBc,set.matlabRBw,set.matlabRBcb,set.matlabRBwb);
		mlp.saveTo(set.classifierRBN);
		System.out.println(set.classifierRBN.getAbsolutePath()+" created");
	}

	public static void main(String[] args) throws IOException {
		learnRBN(Gender.MALE, TournamentType.SINGLE);
	}
}
