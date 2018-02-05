package hr.tennis.bot.learner.classification.learning.mlp.pso;

import hr.tennis.bot.learner.classification.classifiers.MLPClassifier;
import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.optimizer.pso.PSO;
import hr.tennis.bot.optimizer.pso.PSO.PSO_TYPE;
import hr.tennis.bot.optimizer.pso.PSOSettings;
import hr.tennis.bot.optimizer.pso.factory.PSOFactory;
import hr.tennis.bot.util.ArrayUtil;

import java.io.IOException;
import java.util.List;

public class PSOMLPLearner {

	public static MLPClassifier createMLP(FileSet set, int h) throws IOException {

		List<double[]> train_in = ClassifyUtils.loadDoubleArrayListFromFile(set.rawFeatureTrainIn);
		List<double[]> train_out = ClassifyUtils.loadDoubleArrayListFromFile(set.rawFeatureTrainOut);

		assert train_out.size()==train_in.size():"trainNames and trainData must be the same size";

		int inputLayerDim = train_in.get(0).length;
		int hiddenLayerDim = h;
		int outputLayerDim = train_out.get(0).length;

		int dim = inputLayerDim*hiddenLayerDim +
		hiddenLayerDim +
		outputLayerDim*hiddenLayerDim +
		outputLayerDim;

		double velIntervalPercent=0.20;

		double xMin=-10.0;
		double xMax=10.0;
		double vMin=-1.0*(xMax-xMin)*velIntervalPercent/2;
		double vMax=(xMax-xMin)*velIntervalPercent/2;

		final double[][] xIntervals = ArrayUtil.expand(xMin,xMax,dim);
		double[][] vIntervals = ArrayUtil.expand(vMin,vMax,dim);

		PSOSettings<MLPWeighBiasData> settings = new PSOSettings<MLPWeighBiasData>();
		settings.particleCount = 130;
		settings.xIntervals = xIntervals;
		settings.vIntervals = vIntervals;
		settings.type = PSO_TYPE.LOCAL;
		settings.localK = 10;
		settings.C1 = 2.05;
		settings.C2 = 2.05;
		settings.useInertia = false;
		settings.useConstriction = true;
		settings.wMax = 1.0;
		settings.wMin = 0.2;

		settings.maxIter = 1000;
		settings.maxTime = 15*60;

		MLPWeighBiasDataPSOAdapter mlpPSOAdapter = new MLPWeighBiasDataPSOAdapter(inputLayerDim,
				hiddenLayerDim,
				outputLayerDim,
				xMin,
				xMax,
				set);

		settings.adapter = mlpPSOAdapter;

		PSO<MLPWeighBiasData> pso = PSOFactory.createPSO(settings);
		MLPWeighBiasData s = pso.optimize();
		return new MLPClassifier(s);
	}

	public static void learnMLP(Gender gender,TournamentType type) throws IOException{

		FileSet set = FileSet.fromType(gender, type);
		MLPClassifier mlp = createMLP(set,10);
		mlp.saveTo(set.classifierMLP);
		System.out.println(set.classifierMLP.getAbsolutePath()+" created");
	}

	public static void main(String[] args) throws IOException {
		learnMLP(Gender.MALE, TournamentType.SINGLE);
		//learnMLP(Gender.FEMALE, TournamentType.SINGLE);
	}

}
