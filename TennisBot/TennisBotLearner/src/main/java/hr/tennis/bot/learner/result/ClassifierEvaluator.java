package hr.tennis.bot.learner.result;

import hr.tennis.bot.learner.classification.Category;
import hr.tennis.bot.learner.classification.IClassifier;
import hr.tennis.bot.learner.result.scenario.IScenario;
import hr.tennis.bot.learner.utils.ClassifyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ClassifierEvaluator implements Comparable<ClassifierEvaluator>{

	private IClassifier classifier;
	private ClassifierEvaluatorSettings settings;

	private Double sseTotal;
	private Double sseDecided;

	private int[][] outcomeMatrix;
	private int[] unclassified;

	private IScenario scenario;
	private File testFileIn;

	public ClassifierEvaluator(IClassifier classifier,ClassifierEvaluatorSettings settings) {
		this.classifier = classifier;
		this.settings = settings;
	}

	public void init(){
		this.sseTotal = 0d;
		this.sseDecided = 0d;

		this.outcomeMatrix = new int[Category.CATEGORY_COUNT][Category.CATEGORY_COUNT];
		this.unclassified = new int[Category.CATEGORY_COUNT];
	}

	public void evaluate(
			File testFileIn,
			File testFileOut,
			File normalizeFile,
			File coefficientFile,
			IScenario scenario) throws IOException {

		this.testFileIn = testFileIn;
		this.scenario = scenario;

		List<double[]> test_in = ClassifyUtils.loadDoubleArrayListFromFile(testFileIn);
		List<double[]> test_out = ClassifyUtils.loadDoubleArrayListFromFile(testFileOut);
		List<double[]> coefficientList = ClassifyUtils.loadDoubleArrayListFromFile(coefficientFile);

		List<double[]> maxMinList = ClassifyUtils.loadDoubleArrayListFromFile(normalizeFile);

		assert (test_in.size()!=test_out.size()):"testNames and testData must be the same size";

		int prediction;
		double probability;

		double[] output = new double[Category.CATEGORY_COUNT];

		int dim = output.length;
		int suc = 0;

		init();
		if(scenario!=null){
			scenario.init();
		}

		for (int i = 0; i < test_in.size(); i++) {

			double[] input = test_in.get(i);
			double[] coeffients = coefficientList.get(i);
			double[] expectedOutput = test_out.get(i);
			int expectedClass = ClassifyUtils.maxIndex(expectedOutput);

			ClassifyUtils.normalize(maxMinList, input);

			//totPerCategory[_class]++;

			//			System.out.println(testNames.get(i));
			//			System.out.println("knn input:"+Arrays.toString(feature));

			Arrays.fill(output, 0);
			this.classifier.classify(input, dim, output);

			this.sseTotal += ClassifyUtils.sse(output,expectedOutput);

			if(this.settings.debug){
				System.out.println(Arrays.toString(output));
			}

			prediction = ClassifyUtils.maxIndex(output);
			probability = output[prediction];

			if(scenario!=null){
				scenario.classified(prediction,expectedClass,probability,coeffients);
			}

			if(probability >= this.settings.secureThreshold){
				this.outcomeMatrix[prediction][expectedClass]++;
				this.sseDecided += ClassifyUtils.sse(output,expectedOutput);
			}else{
				this.unclassified[expectedClass]++;
			}
		}
	}

	public void printStatus(){

		StringBuffer b = new StringBuffer();

		b.append('\n');
		b.append(getClass().getSimpleName());
		b.append('\n');
		b.append(StringUtils.repeat('*',getClass().getSimpleName().length()));
		b.append('\n');

		b.append(String.format("secure threshold:%.2f\n",this.settings.secureThreshold));
		b.append(String.format("test set:%s\n",this.testFileIn.getAbsolutePath()));

		b.append(String.format("# instances:%d\n",getTotal()));
		b.append(String.format("# instances (secure):%d\n",getTotalDecided()));
		b.append(String.format("secure rate:%.2f%%\n",getSecureRate()*100));
		b.append(String.format("precision:%.2f%%\n",getPrecision()*100));
		b.append(String.format("accuracy:%.2f%%\n",getAccuracy()*100));
		b.append(String.format("sensitivity:%.2f%%\n",getSensitivity()*100));
		b.append(String.format("specificity:%.2f%%\n",getSpecificity()*100));
		b.append(String.format("mse:%.4f\n",getMSE()));
		b.append(String.format("mse (secure):%.4f\n",getMSESecure()));

		b.append("\n\n");
		b.append("\t");

		for(int i=0;i<Category.CATEGORY_COUNT;i++){
			b.append(String.format("R%d\t\t",i));
		}
		b.append("Tot");
		b.append("\n");

		for(int i=0;i<Category.CATEGORY_COUNT;i++){
			b.append(String.format("E%d\t",i));
			for(int j=0;j<Category.CATEGORY_COUNT;j++){
				b.append(String.format("%d(%.2f%%)\t",
						this.outcomeMatrix[i][j],
						this.outcomeMatrix[i][j] / (double)getTotal() * 100));
			}
			b.append(String.format("%d(%.2f%%)\t",
					this.outcomeMatrix[i][0]+this.outcomeMatrix[i][1],
					(this.outcomeMatrix[i][0]+this.outcomeMatrix[i][1]) / (double)getTotal() * 100));
			b.append("\n");
		}

		b.append(String.format("E?\t"));
		for(int i=0;i<Category.CATEGORY_COUNT;i++){
			b.append(String.format("%d(%.2f%%)\t",this.unclassified[i],this.unclassified[i]/(double)getTotal() * 100));
		}
		b.append(String.format("%d(%.2f%%)",getTotalUndecided(),(double)getTotalUndecided() / getTotal() * 100));
		b.append(String.format("\nTot\t"));
		for(int i=0;i<Category.CATEGORY_COUNT;i++){
			int totalPerClass = this.outcomeMatrix[0][i] + this.outcomeMatrix[1][i] +this.unclassified[i];
			b.append(String.format("%d(%.2f%%)\t",totalPerClass, totalPerClass / (double)getTotal() * 100));
		}
		b.append(String.format("%d",getTotal()));
		b.append("\n");

		System.out.println(b);
		this.scenario.printStatus();
	}

	private int getTotalUndecided() {
		return getTotal() - getTotalDecided();
	}

	public Double getMSE() {
		return this.sseTotal/getTotal();
	}

	public Double getMSESecure() {
		return this.sseDecided/getTotalDecided();
	}

	public double getSecureRate() {
		return (double)getTotalDecided()/getTotal();
	}

	private int getTotal() {
		return getTotalDecided()+this.unclassified[0]+this.unclassified[1];
	}

	public float getSensitivity() {
		return (float)this.outcomeMatrix[0][0] /
		(this.outcomeMatrix[0][0]+this.outcomeMatrix[1][0]);
	}

	public float getSpecificity() {
		return (float)this.outcomeMatrix[1][1] /
		(this.outcomeMatrix[1][1]+this.outcomeMatrix[0][1]);
	}

	public double getAccuracy() {
		return ((double)this.outcomeMatrix[0][0]+this.outcomeMatrix[1][1])/getTotalDecided();
	}

	public double getPrecision() {
		return (double)this.outcomeMatrix[0][0]/(this.outcomeMatrix[0][0]+this.outcomeMatrix[0][1]);
	}
	
	public double getPrecisionZero() {
		return (double)this.outcomeMatrix[0][0]/(this.outcomeMatrix[0][0]+this.outcomeMatrix[0][1]);
	}
	
	public double getPrecisionOne() {
		return (double)this.outcomeMatrix[1][1]/(this.outcomeMatrix[1][1]+this.outcomeMatrix[1][0]);
	}

	public int getTotalDecided() {
		return this.outcomeMatrix[0][0]+this.outcomeMatrix[0][1]+
		this.outcomeMatrix[1][0]+this.outcomeMatrix[1][1];
	}
	
	public double getTotalZeroDecidedRate(){
		return (this.outcomeMatrix[0][0]+this.outcomeMatrix[0][1])/(double)getTotalDecided();
	}
	
	public double getTotalOneDecidedRate(){
		return (this.outcomeMatrix[1][0]+this.outcomeMatrix[1][1])/(double)getTotalDecided();
	}

	@Override
	public int compareTo(ClassifierEvaluator o) {
		
		if(this.scenario.getAmountDifference() == o.scenario.getAmountDifference())
			return 0;
		
		else if(this.scenario.getAmountDifference() > o.scenario.getAmountDifference()) 
			return 1;
		
		else 
			return -1;
	}
	

}
