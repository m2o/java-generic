package hr.tennis.bot.learner.classification.classifiers;

import hr.tennis.bot.learner.classification.Classifier;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LowestOddClassifier extends Classifier {

	private static final long serialVersionUID = 1L;
	private List<double[]> maxMinList;

	public LowestOddClassifier(List<double[]> maxMinList) {
		this.maxMinList = maxMinList;
	}

	@Override
	public void classify(double[] input, int dimensions, double[] output) {

		ClassifyUtils.denormalize(this.maxMinList,input);

		if(input[0]<=input[1]){
			output[0]=1;
			output[1]=0;
		}else{
			output[0]=0;
			output[1]=1;
		}
	}

	@Override
	public void loadFrom(File file) {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) throws IOException {

		FileSet set = FileSet.fromType(Gender.MALE, TournamentType.SINGLE);
		List<double[]> maxMinList = ClassifyUtils.loadDoubleArrayListFromFile(set.normalize);

		LowestOddClassifier staticClassifier = new LowestOddClassifier(maxMinList);

//		ClassifierTest.test(staticClassifier,set.rawFeatureTestAllIn,set.rawFeatureTestAllOut,set.normalize,set.rawFeatureTestAllCoefficients,0.80,new ITestDecider() {
//			@Override
//			public boolean isTestable(double[] coefs,double[] feature) {
//				return coefs[0]>=1.0;
//			}
//		});
	}

}
