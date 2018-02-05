package hr.tennis.bot.learner.classification.learning;

import hr.tennis.bot.learner.classification.classifiers.KNNClassifier;
import hr.tennis.bot.learner.classification.classifiers.KNNClassifier.ErrorStrategy;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KNNLearner {

	private static final Integer K = 10;

	public static KNNClassifier createKNN(File trainFileIn,File trainFileOut,Integer k) throws IOException {

		List<double[]> train_out = ClassifyUtils.loadDoubleArrayListFromFile(trainFileOut);
		List<double[]> train_in = ClassifyUtils.loadDoubleArrayListFromFile(trainFileIn);

		assert train_out.size()==train_in.size():"trainNames and trainData must be the same size";

		Map<String,List<double[]>> trainMap = new HashMap<String, List<double[]>>();

		for(int i=0;i<train_in.size();i++){

			double[] features = train_in.get(i);
			double[] out = train_out.get(i);
			String _class = String.valueOf(ClassifyUtils.maxIndex(out));

			if(trainMap.containsKey(_class)){
				trainMap.get(_class).add(features);
			}else{
				List<double[]> featuresList = new LinkedList<double[]>();
				featuresList.add(features);
				trainMap.put(_class,featuresList);
			}
		}

		KNNClassifier knn = new KNNClassifier(trainMap,k,ErrorStrategy.COUNT);
		return knn;
	}

	public static void learnKNN(Gender gender,TournamentType type) throws IOException{
		FileSet set = FileSet.fromType(gender, type);
		KNNClassifier knn = createKNN(set.featureTrainIn,set.featureTrainOut,K);
		knn.saveTo(set.classifierKNN);
		System.out.println(set.classifierKNN.getAbsolutePath()+" created");
	}

	public static void main(String[] args) throws IOException {
		learnKNN(Gender.MALE, TournamentType.SINGLE);
	}
}
