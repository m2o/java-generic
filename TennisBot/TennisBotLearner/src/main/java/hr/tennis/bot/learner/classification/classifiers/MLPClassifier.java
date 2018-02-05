package hr.tennis.bot.learner.classification.classifiers;

import hr.tennis.bot.learner.classification.Classifier;
import hr.tennis.bot.learner.classification.learning.mlp.pso.MLPWeighBiasData;
import hr.tennis.bot.learner.result.ClassifierEvaluator;
import hr.tennis.bot.learner.result.ClassifierEvaluatorSettings;
import hr.tennis.bot.learner.result.scenario.FixedSingleBetScenario;
import hr.tennis.bot.learner.result.scenario.IScenario;
import hr.tennis.bot.learner.result.scenario.ScenarioSettings;
import hr.tennis.bot.learner.result.scenario.SimpleScenario;
import hr.tennis.bot.learner.result.scenario.ValueAddedSingleBetScenario;
import hr.tennis.bot.learner.result.scenario.ValueMultipliedSingleBetScenario;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.util.ArrayUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

public class MLPClassifier extends Classifier {

	private static final long serialVersionUID = 1L;
	protected int inputLayerDim;
	protected int hiddenLayerDim;
	protected int outputLayerDim;
	protected double[][] w1;
	protected double[] b1;
	protected double[][] w2;
	protected double[] b2;

	private double[] y1;

	public MLPClassifier() {
		// TODO Auto-generated constructor stub
	}

	public MLPClassifier(double[][] w1,double[] b1,double[][] w2,double[] b2){

		this.inputLayerDim = w1[0].length;
		this.hiddenLayerDim = b1.length;
		this.outputLayerDim = b2.length;

		this.w1 = w1;
		this.b1 = b1;
		this.w2 = w2;
		this.b2 = b2;
		this.y1 = new double[this.hiddenLayerDim];

		//System.out.println(String.format("created MultilayerPerceptron %dx%dx%d",this.inputLayerDim,this.hiddenLayerDim,this.outputLayerDim));
	}

	public MLPClassifier(MLPWeighBiasData solution) {
		this(solution.w1,solution.b1,solution.w2,solution.b2);
	}

	@Override
	public void classify(double[] input, int dimensions, double[] output) {
		getOutput(input, output);
		ArrayUtil.normalizeArray(output, dimensions);
		//System.out.println(Arrays.toString(output));
	}

	public void getOutput(double[] input, double[] output) {
		for (int i = 0; i < this.hiddenLayerDim; i++) {
			double value = this.b1[i];
			for (int j = 0; j < this.inputLayerDim; j++) {
				value += this.w1[i][j] * input[j];
			}
			this.y1[i] = 1.0 / (1.0 + Math.exp(-value));
		}
		for (int i = 0; i < this.outputLayerDim; i++) {
			double value = this.b2[i];
			for (int j = 0; j < this.hiddenLayerDim; j++) {
				value += this.w2[i][j] * this.y1[j];
			}
			output[i] = 1.0 / (1.0 + Math.exp(-value));
		}
	}

	@Override
	public void loadFrom(File file) {
		ObjectInputStream stream = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(file));
			MLPClassifier multilayerPerceptron = (MLPClassifier) stream.readObject();

			this.name = file.getName();
			this.inputLayerDim = multilayerPerceptron.inputLayerDim;
			this.hiddenLayerDim = multilayerPerceptron.hiddenLayerDim;
			this.outputLayerDim = multilayerPerceptron.outputLayerDim;

			this.w1 = new double[this.hiddenLayerDim][this.inputLayerDim];
			this.b1 = new double[this.hiddenLayerDim];
			this.w2 = new double[this.outputLayerDim][this.hiddenLayerDim];
			this.b2 = new double[this.outputLayerDim];
			this.y1 = new double[this.hiddenLayerDim];

			for (int i = 0; i < this.w1.length; i++) {
				System.arraycopy(multilayerPerceptron.w1[i], 0, this.w1[i], 0, this.w1[i].length);
			}
			System.arraycopy(multilayerPerceptron.b1, 0, this.b1, 0, this.b1.length);
			for (int i = 0; i < this.w2.length; i++) {
				System.arraycopy(multilayerPerceptron.w2[i], 0, this.w2[i], 0, this.w2[i].length);
			}
			System.arraycopy(multilayerPerceptron.b2, 0, this.b2, 0, this.b2.length);

		}catch(FileNotFoundException e){
			System.err.println("unable to load classsifier "+file.getAbsolutePath());
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {}
			}
		}

	}

	public static void main(String[] args) throws IOException {

		FileSet set = FileSet.fromType(Gender.MALE, TournamentType.SINGLE);

		MLPClassifier mlp = new MLPClassifier();
		mlp.loadFrom(set.classifierMLP);
		
		ClassifierEvaluatorSettings evaluatorSettings = new ClassifierEvaluatorSettings();
		evaluatorSettings.secureThreshold = 0;

		IScenario scenario = new SimpleScenario();

		ClassifierEvaluator eval = new ClassifierEvaluator(mlp, evaluatorSettings);
		eval.evaluate(set.rawFeatureTrainIn,
				set.rawFeatureTrainOut,
				set.normalize,
				set.rawFeatureTrainCoefficients,
				scenario);
		//eval.printStatus();
		
		System.out.println("train data percentage");
		System.out.println("---------------------");
		scenario.printStatus();
		
		scenario = new SimpleScenario();
		
		eval = new ClassifierEvaluator(mlp, evaluatorSettings);
		
		
		eval.evaluate(set.rawFeatureTestAllIn,
				set.rawFeatureTestAllOut,
				set.normalize,
				set.rawFeatureTestAllCoefficients,
				scenario);

		//eval.printStatus();
		System.out.println("test data percentage");
		System.out.println("---------------------");
		scenario.printStatus();
		
		findBestValueThreshold(mlp, evaluatorSettings, set);
	}

	private static void findBestValueThreshold(MLPClassifier mlp,
			ClassifierEvaluatorSettings evaluatorSettings, FileSet set) throws IOException {
		
		TreeSet<ClassifierEvaluator> evaluatorsSet = new TreeSet<ClassifierEvaluator>();
		ScenarioSettings scenarioSettings = new ScenarioSettings();
		ValueAddedSingleBetScenario scenario = new ValueAddedSingleBetScenario(scenarioSettings);
		scenarioSettings.valueThreashold = 0;
		double counter = 0;
		
		while(counter < 4){
			
			ClassifierEvaluator eval = new ClassifierEvaluator(mlp, evaluatorSettings);
			eval.evaluate(set.rawFeatureTestAllIn,
					set.rawFeatureTestAllOut,
					set.normalize,
					set.rawFeatureTestAllCoefficients,
					scenario);
			
			
			if(scenario.getTotalBetAmount() > 900) evaluatorsSet.add(eval);
			counter += 0.01;
			
			scenarioSettings = new ScenarioSettings();
			scenarioSettings.valueThreashold = counter;
			scenario = new ValueAddedSingleBetScenario(scenarioSettings);
			
		}
		
		evaluatorsSet.last().printStatus();
		
	}
}
