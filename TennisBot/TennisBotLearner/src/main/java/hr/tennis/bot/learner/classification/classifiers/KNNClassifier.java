package hr.tennis.bot.learner.classification.classifiers;

import hr.tennis.bot.learner.classification.Category;
import hr.tennis.bot.learner.classification.Classifier;
import hr.tennis.bot.learner.result.ClassifierEvaluator;
import hr.tennis.bot.learner.result.ClassifierEvaluatorSettings;
import hr.tennis.bot.learner.result.scenario.IScenario;
import hr.tennis.bot.learner.result.scenario.ScenarioSettings;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class KNNClassifier extends Classifier {

	private static final long serialVersionUID = 1L;

	private ErrorStrategy errorStrategy = ErrorStrategy.COUNT;

	private Map<String,List<DenseMatrix64F>> neighbours;

	private int n=-1;
	private int p=-1;
	private int tot=-1;

	private double maxError=-1;

	private int k=6;

	private final boolean DEBUG = false;

	public KNNClassifier() {
		// TODO Auto-generated constructor stub
	}

	public KNNClassifier(Map<String,List<double[]>> trainMap,int k,ErrorStrategy errorStrategy){

		this.k = k;
		this.p=trainMap.size();
		this.tot=0;
		this.errorStrategy = errorStrategy;

		this.neighbours=new HashMap<String, List<DenseMatrix64F>>(trainMap.size());

		for(Map.Entry<String,List<double[]>> entry : trainMap.entrySet()){

			List<DenseMatrix64F> featureMatrixList = new LinkedList<DenseMatrix64F>();

			for(double[] features : entry.getValue()){
				if(this.n==-1){
					this.n=features.length;
				}
				DenseMatrix64F a = new DenseMatrix64F(this.n,1);
				System.arraycopy(features, 0, a.data, 0, this.n);
				featureMatrixList.add(a);
			}
			this.tot+=featureMatrixList.size();
			this.neighbours.put(entry.getKey(),featureMatrixList);
		}

		this.maxError = this.n;
	}

	@Override
	public void classify(double[] input, int dimensions, double[] output) {

		//		System.out.println("in:"+Arrays.toString(input));
		//		System.out.println(String.format("n:%d p:%d tot:%d k:%d",n,p,tot,k));

		DenseMatrix64F inputMat = new DenseMatrix64F(this.n,1);
		System.arraycopy(input, 0, inputMat.data, 0, this.n);

		if(this.DEBUG){
			System.out.println("in:");
			inputMat.print();
		}

		List<StateError> neighbourList = new ArrayList<StateError>(this.tot);

		for(Map.Entry<String,List<DenseMatrix64F>> entry : this.neighbours.entrySet()){
			for(DenseMatrix64F state : entry.getValue()){
				DenseMatrix64F dif = new DenseMatrix64F(this.n,1);
				CommonOps.sub(state, inputMat, dif);
				double error=CommonOps.elementSumAbs(dif);
				neighbourList.add(new StateError(entry.getKey(), error));
			}
		}

		Collections.sort(neighbourList);

		double[] errArray = new double[Category.CATEGORY_COUNT];
		double sum=0;

		int j=0;

		if(this.errorStrategy.equals(ErrorStrategy.COUNT)){

			while((j<this.k) || (neighbourList.get(j).error == 0)){ // k less than amount of points with error=0 (leads to erroneous classification)
				Integer _class = Integer.valueOf(neighbourList.get(j)._class);
				errArray[_class]+=1;
				j++;
			}

			ArrayUtil.normalizeArray(errArray,errArray.length);
		}else{

			while((j<this.k) || (neighbourList.get(j).error == 0)){

				Integer _class = Integer.valueOf(neighbourList.get(j)._class);
				double error = neighbourList.get(j).error;
				//				double part = 1.0/(Math.abs(error));
				//				if(part==Double.POSITIVE_INFINITY){
				//					part = Double.MAX_VALUE/2;
				//				}
				double part = this.maxError - error;
				errArray[_class]+=part;
				j++;
			}

			ArrayUtil.normalizeArray(errArray,errArray.length);
		}

		System.arraycopy(errArray,0,output,0, dimensions);
	}

	@Override
	public String toString() {
		return String.format("%s (k=%d,sum_policy=%s)",super.toString(),getK(),this.errorStrategy);
	}

	@Override
	public void loadFrom(File file) {

		ObjectInputStream stream = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(file));

			KNNClassifier knn = (KNNClassifier) stream.readObject();
			this.setNeighbours(knn.getNeighbours());
			this.setN(knn.getN());
			this.setP(knn.getP());
			this.setTot(knn.getTot());
			this.setK(knn.getK());
			this.setErrorStrategy(knn.getErrorStrategy());
			this.name = file.getName();
			this.setMaxError(knn.getMaxError());

		} catch(FileNotFoundException e){
			System.err.println("unable to load classsifier "+file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {}
			}
		}
	}

	private double getMaxError() {
		return this.maxError;
	}

	public void setMaxError(double maxError) {
		this.maxError = maxError;
	}

	public Map<String, List<DenseMatrix64F>> getNeighbours() {
		return this.neighbours;
	}

	public void setNeighbours(Map<String, List<DenseMatrix64F>> neighbours) {
		this.neighbours = neighbours;
	}

	public int getN() {
		return this.n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getP() {
		return this.p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getTot() {
		return this.tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}

	public int getK() {
		return this.k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public ErrorStrategy getErrorStrategy() {
		return this.errorStrategy;
	}

	public void setErrorStrategy(ErrorStrategy errorStrategy) {
		this.errorStrategy = errorStrategy;
	}

	private class StateError implements Comparable<StateError>{

		public String _class;
		public double error;

		public StateError(String _class, double error) {
			this._class = _class;
			this.error = error;
		}

		@Override
		public int compareTo(StateError o) {
			return Double.valueOf(this.error).compareTo(o.error);
		}

		@Override
		public String toString() {
			return "("+this._class+","+this.error+")";
		}
	}

	public static enum ErrorStrategy{
		ERROR,COUNT
	}

	public static void main(String[] args) throws IOException {

		FileSet set = FileSet.fromType(Gender.MALE, TournamentType.SINGLE);

		KNNClassifier knn = new KNNClassifier();
		knn.loadFrom(set.classifierKNN);

		knn.setErrorStrategy(ErrorStrategy.COUNT);
		knn.setK(20);

		ClassifierEvaluatorSettings evaluatorSettings = new ClassifierEvaluatorSettings();
		evaluatorSettings.secureThreshold = 0.8;

		//		ScenarioSettings scenarioSettings = new ScenarioSettings();
		//		IScenario scenario = new FixedSingleBetScenario(scenarioSettings);
		//		scenarioSettings.probabilityThreashold = 0.8;
		//
		//		ClassifierEvaluator eval = new ClassifierEvaluator(knn, evaluatorSettings);
		//		eval.evaluate(set.rawFeatureTrainIn,
		//				set.rawFeatureTrainOut,
		//				set.normalize,
		//				set.rawFeatureTrainCoefficients,
		//				scenario);
		//		eval.printStatus();
		//
		//		eval = new ClassifierEvaluator(knn, evaluatorSettings);
		//		eval.evaluate(set.rawFeatureTestAllIn,
		//				set.rawFeatureTestAllOut,
		//				set.normalize,
		//				set.rawFeatureTestAllCoefficients,
		//				scenario);
		//
		//		eval.printStatus();

		ScenarioSettings scenarioSettings = new ScenarioSettings();
		IScenario scenario = new ValueMultipliedSingleBetScenario(scenarioSettings);
		scenarioSettings.valueThreashold = 1.0;
		scenarioSettings.betOnlyFavorite = false;

		ClassifierEvaluator eval = new ClassifierEvaluator(knn, evaluatorSettings);
		eval.evaluate(set.rawFeatureTrainIn,
				set.rawFeatureTrainOut,
				set.normalize,
				set.rawFeatureTrainCoefficients,
				scenario);
		eval.printStatus();

		eval = new ClassifierEvaluator(knn, evaluatorSettings);
		eval.evaluate(set.rawFeatureTestAllIn,
				set.rawFeatureTestAllOut,
				set.normalize,
				set.rawFeatureTestAllCoefficients,
				scenario);

		eval.printStatus();

	}
}

