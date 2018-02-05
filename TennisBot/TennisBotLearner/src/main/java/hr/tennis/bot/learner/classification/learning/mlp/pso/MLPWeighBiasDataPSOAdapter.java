package hr.tennis.bot.learner.classification.learning.mlp.pso;

import hr.tennis.bot.learner.classification.classifiers.MLPClassifier;
import hr.tennis.bot.learner.result.ClassifierEvaluator;
import hr.tennis.bot.learner.result.ClassifierEvaluatorSettings;
import hr.tennis.bot.learner.result.scenario.IScenario;
import hr.tennis.bot.learner.result.scenario.ScenarioSettings;
import hr.tennis.bot.learner.result.scenario.ValueMultipliedSingleBetScenario;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.optimizer.pso.interfaces.IPSOAdapter;
import hr.tennis.bot.optimizer.util.RandomUtil;
import hr.tennis.bot.util.ArrayUtil;

import java.io.IOException;

public class MLPWeighBiasDataPSOAdapter implements IPSOAdapter<MLPWeighBiasData> {

	private int inputLayerDim;
	private int hiddenLayerDim;
	private int outputLayerDim;

	private int totalDim;

	private double xMin;
	private double xMax;

	private FileSet set;

	public MLPWeighBiasDataPSOAdapter(int inputLayerDim,
			int hiddenLayerDim,
			int outputLayerDim,
			double xMin,
			double xMax,
			FileSet set) {

		this.inputLayerDim = inputLayerDim;
		this.hiddenLayerDim = hiddenLayerDim;
		this.outputLayerDim = outputLayerDim;
		this.xMin = xMin;
		this.xMax = xMax;

		this.set = set;

		this.totalDim = inputLayerDim*hiddenLayerDim
		+ hiddenLayerDim
		+ outputLayerDim*hiddenLayerDim
		+ outputLayerDim;
	}


	@Override
	public MLPWeighBiasData create() {

		MLPWeighBiasData solution = new MLPWeighBiasData();

		solution.w1 = RandomUtil.nextMatrix(this.hiddenLayerDim,this.inputLayerDim,this.xMin,this.xMax);
		solution.b1 = RandomUtil.nextArray(this.hiddenLayerDim,this.xMin,this.xMax);
		solution.w2 = RandomUtil.nextMatrix(this.outputLayerDim,this.hiddenLayerDim,this.xMin,this.xMax);
		solution.b2 = RandomUtil.nextArray(this.outputLayerDim,this.xMin,this.xMax);

		return solution;
	}

	@Override
	public double evaluate(MLPWeighBiasData solution) {

		MLPClassifier classifier = new MLPClassifier(solution);

		ClassifierEvaluatorSettings settings = new ClassifierEvaluatorSettings();
		settings.secureThreshold = 0.5;

		ScenarioSettings scenarioSettings = new ScenarioSettings();
		IScenario scenario = new ValueMultipliedSingleBetScenario(scenarioSettings);
		scenarioSettings.valueThreashold = 1.0;
		scenarioSettings.betOnlyFavorite = false;
		
		ClassifierEvaluator eval = new ClassifierEvaluator(classifier, settings);

		try {
			eval.evaluate(this.set.rawFeatureTrainIn,
					this.set.rawFeatureTrainOut,
					this.set.normalize,
					this.set.rawFeatureTrainCoefficients,
					scenario);
		} catch (IOException e) {
			//ignorable
			e.printStackTrace();
		}

		double mse = eval.getMSE();
		//mseSecure = Double.isNaN(mseSecure)? 2.0 : mseSecure; //2 is max possible mse

		double secureRateDiff = Math.abs(eval.getSecureRate()- 0.50);

		double accuracy = eval.getAccuracy();
		double precision = eval.getPrecision();
		double precisionZero = eval.getPrecisionZero();
		double precisionOne = eval.getPrecisionOne();
		double sensitivity = eval.getSensitivity();
		double specificity = eval.getSpecificity();

		double totalZeroRate = eval.getTotalZeroDecidedRate();
		double totalOneRate = eval.getTotalOneDecidedRate();
		
		totalZeroRate = Double.isNaN(totalZeroRate) ? totalZeroRate : 0.0;
		totalOneRate = Double.isNaN(totalOneRate) ? totalOneRate : 0.0;

		double form = 0.0;
		form += scenario.getAmountDifference();
		//form -= mse;
		//form += eval.getAccuracy();
		//form += (!Double.isNaN(accuracy) ? accuracy : 0.0);
		//form += 0.5 * (!Double.isNaN(sensitivity) ? sensitivity : 0.0);
		//form += 1.0 * (!Double.isNaN(specificity) ? specificity : 0.0);
		//form += !Double.isNaN(precisionZero) ? precisionZero : 0.0;
		//form += !Double.isNaN(precisionOne) ? precisionOne : 0.0;
		//form -= 0.9 * Math.abs(totalZeroRate - 0.70);
		//form -= 0.9 * Math.abs(totalOneRate - 0.30);
		//return /*-100*mseSecure*/ -100*secureRateDiff;
		return form;
	}

	@Override
	public double[] serialize(MLPWeighBiasData solution) {

		double[] data = new double[this.totalDim];
		int destPos = 0;


		ArrayUtil.copyMatrixToArray(solution.w1,solution.w1.length,solution.w1[0].length,data,destPos);
		destPos += solution.w1.length * solution.w1[0].length;

		System.arraycopy(solution.b1,0,data,destPos,solution.b1.length);
		destPos += solution.b1.length;

		ArrayUtil.copyMatrixToArray(solution.w2,solution.w2.length,solution.w2[0].length,data,destPos);
		destPos += solution.w2.length * solution.w2[0].length;

		System.arraycopy(solution.b2,0,data,destPos,solution.b2.length);
		destPos += solution.b2.length;

		return data;
	}

	@Override
	public MLPWeighBiasData deserialize(double[] particle) {

		MLPWeighBiasData solution = new MLPWeighBiasData();

		solution.w1 = new double[this.hiddenLayerDim][this.inputLayerDim];
		solution.b1 = new double[this.hiddenLayerDim];
		solution.w2 = new double[this.outputLayerDim][this.hiddenLayerDim];
		solution.b2 = new double[this.outputLayerDim];

		int srcPos = 0;

		ArrayUtil.copyArrayToMatrix(particle,srcPos,solution.w1,solution.w1.length,solution.w1[0].length);
		srcPos += solution.w1.length * solution.w1[0].length;

		System.arraycopy(particle,srcPos,solution.b1,0,solution.b1.length);
		srcPos += solution.b1.length;

		ArrayUtil.copyArrayToMatrix(particle,srcPos,solution.w2,solution.w2.length,solution.w2[0].length);
		srcPos += solution.w2.length * solution.w2[0].length;

		System.arraycopy(particle,srcPos,solution.b2,0,solution.b2.length);
		srcPos += solution.b2.length;

		return solution;
	}
}