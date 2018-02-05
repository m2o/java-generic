package hr.tennis.bot.learner.classification.classifiers;

import hr.tennis.bot.learner.classification.Classifier;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.NormOps;

public class RBNClassifier extends Classifier {

	protected int inputLayerDim;
	protected int hiddenLayerDim;
	protected int outputLayerDim;

	protected DenseMatrix64F weights;
	protected DenseMatrix64F[] centers;
	protected double[] centerBiases;
	protected DenseMatrix64F weightsBias;


	public RBNClassifier(double[][] centersMatrix,double[][] weights,double[] centersBias,double[] weightsBias) {

		this.inputLayerDim = centersMatrix[0].length;
		this.hiddenLayerDim = weights[0].length;
		this.outputLayerDim = weightsBias.length;

		this.centers = new DenseMatrix64F[this.hiddenLayerDim];
		for(int i = 0;i<this.hiddenLayerDim;i++){
			this.centers[i] = new DenseMatrix64F(this.inputLayerDim,1);
			System.arraycopy(centersMatrix[i],0,this.centers[i].data,0,this.inputLayerDim);
		}

		this.weights = new DenseMatrix64F(weights);
		this.centerBiases = centersBias;

		this.weightsBias = new DenseMatrix64F(this.outputLayerDim,1);
		System.arraycopy(weightsBias,0,this.weightsBias.data,0,this.outputLayerDim);

		System.out.println(String.format("created RadialBasisNetwork %dx%dx%d",this.inputLayerDim,this.hiddenLayerDim,this.outputLayerDim));
	}

	public RBNClassifier() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void classify(double[] input, int dimensions, double[] output) {
		getOutput(input, output);

//		System.out.println("in:"+Arrays.toString(input));
//		System.out.println("out:"+Arrays.toString(output));

		double sum = 0;
		for (int i = 0; i < dimensions; i++) {
			if (output[i] < 0) {
				output[i] = 0;
			}
			sum += output[i];
		}
		for (int i = 0; i < dimensions; i++) {
			output[i] = output[i] / sum;
		}
	}

	@Override
	public void loadFrom(File file) {
		ObjectInputStream stream = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(file));

			RBNClassifier classifier = (RBNClassifier) stream.readObject();

			this.name = file.getName();
			this.inputLayerDim = classifier.inputLayerDim;
			this.hiddenLayerDim = classifier.hiddenLayerDim;
			this.outputLayerDim = classifier.outputLayerDim;

			this.centers = classifier.centers;
			this.centerBiases = classifier.centerBiases;
			this.weights = classifier.weights;
			this.weightsBias = classifier.weightsBias;

		}catch(FileNotFoundException e){
			System.err.println("unable to load classsifier "+file.getAbsolutePath());
	    }catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public void getOutput(double[] input, double[] output) {
		DenseMatrix64F inputVector = new DenseMatrix64F(input.length, 1, true,input);
		DenseMatrix64F result = null;

		result = calculateHiddenLayer(inputVector);
		result = calculateOutputLayer(result);

		System.arraycopy(result.data, 0, output, 0, this.outputLayerDim);
	}

	private DenseMatrix64F calculateHiddenLayer(DenseMatrix64F input) {

		DenseMatrix64F x = new DenseMatrix64F(input.getNumRows(), 1);
		System.arraycopy(input.data, 0, x.data, 0, input.getNumRows());

		DenseMatrix64F result = new DenseMatrix64F(this.hiddenLayerDim, 1);
		DenseMatrix64F distance = new DenseMatrix64F(input.getNumRows(), 1);

		for (int i = 0; i < this.hiddenLayerDim; i++) {
			CommonOps.sub(x, this.centers[i], distance);
			double currentR = NormOps.normF(distance) * this.centerBiases[i];
			result.set(i, Math.exp(-currentR * currentR));
		}

		return result;
	}

	private DenseMatrix64F calculateOutputLayer(DenseMatrix64F input) {

		DenseMatrix64F x = new DenseMatrix64F(input.getNumRows(), 1);
		System.arraycopy(input.data, 0, x.data, 0, input.getNumRows());

		DenseMatrix64F result = new DenseMatrix64F(this.outputLayerDim, 1);
		CommonOps.mult(this.weights, x, result);
		CommonOps.addEquals(result,this.weightsBias);

		return result;
	}

	public static void main(String[] args) throws IOException {

		FileSet set = FileSet.fromType(Gender.MALE, TournamentType.SINGLE);

		RBNClassifier rbn = new RBNClassifier();
		rbn.loadFrom(set.classifierRBN);

//		ClassifierTest.test(rbn,set.rawFeatureTestAllIn,set.rawFeatureTestAllOut,set.normalize,set.rawFeatureTestAllCoefficients,0.8,new ITestDecider() {
//			@Override
//			public boolean isTestable(double[] coefs,double[] feature) {
//				return true;
////				return feature[0]>=1.2 && feature[0]<=1.5;
//			}
//		});
	}

}
