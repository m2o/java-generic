package hr.tennis.bot.learner.utils;

import hr.tennis.bot.model.learning.MatchLearningInstance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.ejml.data.DenseMatrix64F;

public class ClassifyUtils {

	protected static void extractFeatures(File trainFile_in, File testFile_in,File trainFeaturesFile_in, File testFeaturesFile_in,
			File trainFile_out, File testFile_out,File trainFeaturesFile_out, File testFeaturesFile_out,
			File normalizeFile) throws IOException {

		// load features for train data
		List<double[]> trainFeatures = loadDoubleArrayListFromFile(trainFile_in);

		// load features for test data
		List<double[]> testFeatures = loadDoubleArrayListFromFile(testFile_in);

		// get min and max for normalize
		List<double[]> maxMinList = extractMaxMinListFromFeatures(trainFeatures);

		// normalize
		normalize(maxMinList, trainFeatures);
		normalize(maxMinList, testFeatures);

		// save data to files
		saveDoubleArrayListToFile(maxMinList, normalizeFile);
		saveDoubleArrayListToFile(trainFeatures, trainFeaturesFile_in);
		saveDoubleArrayListToFile(testFeatures, testFeaturesFile_in);

		//copy raw-features to features
		FileUtils.copyFile(trainFile_out, trainFeaturesFile_out);
		FileUtils.copyFile(testFile_out, testFeaturesFile_out);
	}

	protected static void extractFeaturesWithPCA(File trainFile_in, File testFile_in,File trainFeaturesFile_in, File testFeaturesFile_in,
			File trainFile_out, File testFile_out,File trainFeaturesFile_out, File testFeaturesFile_out,
			File normalizeFile,File pcaFile,int pcaDimension) throws IOException {

		// load features for train data
		List<double[]> trainFeatures = loadDoubleArrayListFromFile(trainFile_in);

		// load features for test data
		List<double[]> testFeatures = loadDoubleArrayListFromFile(testFile_in);

		// create PCA
		PCA pca = new PCA();
		pca.setup(trainFeatures.size(), trainFeatures.get(0).length);
		for (double[] features : trainFeatures) {
			pca.addSample(features);
		}
		pca.computeBasis(pcaDimension);

		// transform train data with PCA
		for (int i = 0; i < trainFeatures.size(); i++) {
			double[] features = trainFeatures.get(i);
			double[] newFeatures = pca.sampleToEigenSpace(features);
			trainFeatures.set(i, newFeatures);
		}

		// transform test data with PCA
		for (int i = 0; i < testFeatures.size(); i++) {
			double[] features = testFeatures.get(i);
			double[] newFeatures = pca.sampleToEigenSpace(features);
			testFeatures.set(i, newFeatures);
		}

		// get min and max for normalize
		List<double[]> maxMinList = extractMaxMinListFromFeatures(trainFeatures);

		// normalize
		normalize(maxMinList, trainFeatures);
		normalize(maxMinList, testFeatures);

		// save data to files
		saveObject(pca, pcaFile);
		saveDoubleArrayListToFile(maxMinList, normalizeFile);
		saveDoubleArrayListToFile(trainFeatures, trainFeaturesFile_in);
		saveDoubleArrayListToFile(testFeatures, testFeaturesFile_in);

		//copy raw-features to features
		FileUtils.copyFile(trainFile_out, trainFeaturesFile_out);
		FileUtils.copyFile(testFile_out, testFeaturesFile_out);
	}

	//	public static void updateSamples() throws IOException {
	//
	//		File featuresDir = new File("data/features");
	//		FileUtils.deleteDirectory(featuresDir);
	//		featuresDir.mkdir();
	//
	//		String[] parts = {"1day","1week","1month","3month","6month","9month","12month"};
	//		String[] suffixs = {"","_pca"};
	//
	//		int PCA_N = 25;
	//
	////		for(String part : parts){
	//			for(String suffix : suffixs){
	//				System.out.println("Extracting next_open_"+part+suffix+" features...");
	//
	//				File rawTrainFile_in = new File("stockdata/raw-features/data_next_open_"+part+"_train_in");
	//				File rawTestFile_in = new File("stockdata/raw-features/data_next_open_"+part+"_test_in");
	//				File trainFile_in = new File("stockdata/features/data_next_open_"+part+"_train_in"+suffix);
	//				File testFile_in = new File("stockdata/features/data_next_open_"+part+"_test_in"+suffix);
	//
	//				File rawTrainFile_out = new File("stockdata/raw-features/data_next_open_"+part+"_train_out");
	//				File rawTestFile_out = new File("stockdata/raw-features/data_next_open_"+part+"_test_out");
	//				File trainFile_out = new File("stockdata/features/data_next_open_"+part+"_train_out"+suffix);
	//				File testFile_out = new File("stockdata/features/data_next_open_"+part+"_test_out"+suffix);
	//
	//				File normalizeFile = new File("stockdata/features/data_next_open_"+part+"_normalize"+suffix);
	//				File pcaFile = new File("data/pca/pca_next_open_"+part+".dat");
	//
	//				if(suffix.equals("")){
	//					extractFeatures(rawTrainFile_in, rawTestFile_in, trainFile_in, testFile_in,
	//							rawTrainFile_out, rawTestFile_out, trainFile_out, testFile_out,
	//							normalizeFile);
	//				}else{
	//					extractFeaturesWithPCA(rawTrainFile_in, rawTestFile_in, trainFile_in, testFile_in,
	//							rawTrainFile_out, rawTestFile_out, trainFile_out, testFile_out,
	//							normalizeFile,pcaFile,PCA_N);
	//				}
	//			}
	////		}
	//
	//		System.out.println("Done");
	//	}

	//	protected static void extractFeatures(File trainFile_in, File testFile_in,File trainFeaturesFile_in, File testFeaturesFile_in,
	//			File trainFile_out, File testFile_out,File trainFeaturesFile_out, File testFeaturesFile_out,
	//			File normalizeFile) throws IOException {
	//
	//		// load features for train data
	//		List<double[]> trainFeatures = loadDoubleArrayListFromFile(trainFile_in);
	//
	//		// load features for test data
	//		List<double[]> testFeatures = loadDoubleArrayListFromFile(testFile_in);
	//
	//		// get min and max for normalize
	//		List<double[]> maxMinList = extractMaxMinListFromFeatures(trainFeatures);
	//
	//		// normalize
	//		normalize(maxMinList, trainFeatures);
	//		normalize(maxMinList, testFeatures);
	//
	//		// save data to files
	//		saveDoubleArrayListToFile(maxMinList, normalizeFile);
	//		saveDoubleArrayListToFile(trainFeatures, trainFeaturesFile_in);
	//		saveDoubleArrayListToFile(testFeatures, testFeaturesFile_in);
	//
	//		//copy raw-features to features
	//		FileUtils.copyFile(trainFile_out, trainFeaturesFile_out);
	//		FileUtils.copyFile(testFile_out, testFeaturesFile_out);
	//	}

	//	protected static void extractFeaturesWithPCA(File trainFile_in, File testFile_in,File trainFeaturesFile_in, File testFeaturesFile_in,
	//			File trainFile_out, File testFile_out,File trainFeaturesFile_out, File testFeaturesFile_out,
	//			File normalizeFile,File pcaFile,int pcaDimension) throws IOException {
	//
	//		// load features for train data
	//		List<double[]> trainFeatures = loadDoubleArrayListFromFile(trainFile_in);
	//
	//		// load features for test data
	//		List<double[]> testFeatures = loadDoubleArrayListFromFile(testFile_in);
	//
	//		// create PCA
	//		PCA pca = new PCA();
	//		pca.setup(trainFeatures.size(), trainFeatures.get(0).length);
	//		for (double[] features : trainFeatures) {
	//			pca.addSample(features);
	//		}
	//		pca.computeBasis(pcaDimension);
	//
	//		// transform train data with PCA
	//		for (int i = 0; i < trainFeatures.size(); i++) {
	//			double[] features = trainFeatures.get(i);
	//			double[] newFeatures = pca.sampleToEigenSpace(features);
	//			trainFeatures.set(i, newFeatures);
	//		}
	//
	//		// transform test data with PCA
	//		for (int i = 0; i < testFeatures.size(); i++) {
	//			double[] features = testFeatures.get(i);
	//			double[] newFeatures = pca.sampleToEigenSpace(features);
	//			testFeatures.set(i, newFeatures);
	//		}
	//
	//		// get min and max for normalize
	//		List<double[]> maxMinList = extractMaxMinListFromFeatures(trainFeatures);
	//
	//		// normalize
	//		normalize(maxMinList, trainFeatures);
	//		normalize(maxMinList, testFeatures);
	//
	//		// save data to files
	//		saveObject(pca, pcaFile);
	//		saveDoubleArrayListToFile(maxMinList, normalizeFile);
	//		saveDoubleArrayListToFile(trainFeatures, trainFeaturesFile_in);
	//		saveDoubleArrayListToFile(testFeatures, testFeaturesFile_in);
	//
	//		//copy raw-features to features
	//		FileUtils.copyFile(trainFile_out, trainFeaturesFile_out);
	//		FileUtils.copyFile(testFile_out, testFeaturesFile_out);
	//	}

	public static List<double[]> extractMaxMinListFromFeatures(List<double[]> featuresList){

		List<double[]> maxMinList = new ArrayList<double[]>();
		double[] firstFeature = featuresList.get(0);
		int featureCount = firstFeature.length;
		for (int i = 0; i < featureCount; i++) {
			double[] maxMin = new double[] { firstFeature[i], firstFeature[i] };
			maxMinList.add(maxMin);
		}
		for (int i = 1; i < featuresList.size(); i++) {
			double[] features = featuresList.get(i);
			for (int j = 0; j < featureCount; j++) {
				double[] maxMin = maxMinList.get(j);
				
				double feature = features[j];
				if(!Double.isNaN(feature)){
					if (feature > maxMin[0] || Double.isNaN(maxMin[0])) {
						maxMin[0] = feature;
					}
					if (feature < maxMin[1] || Double.isNaN(maxMin[1])) {
						maxMin[1] = feature;
					}
				}
			}
		}

		for(double[] maxMin : maxMinList){
			if((maxMin[0]<=1.0) && (maxMin[1]>=0.0)){
				maxMin[0]=1.0;
				maxMin[1]=0.0;
			}
		}

		return maxMinList;
	}

	public static void normalize(List<double[]> maxMinList, List<double[]> featureList) {
		for (double[] feature : featureList) {
			normalize(maxMinList, feature);
		}
	}

	public static void normalize(List<double[]> maxMinList, double[] data) {
		for (int i = 0; i < data.length; i++) {
			double oldValue = data[i];

			double[] maxMin = maxMinList.get(i);
			double max = maxMin[0];
			double min = maxMin[1];
			double value;

			if(Double.isNaN(oldValue))
				oldValue = (max+min)/2.0; //TODO - think about this
			
			value = (oldValue - min) / (max - min);
			value = value > 1.0 ? 1.0 : value;
			value = value < 0.0? 0.0 : value;
			

			data[i] = value;
		}
	}

	public static void denormalize(List<double[]> maxMinList,double[] data) {
		for (int i = 0; i < data.length; i++) {
			double oldValue = data[i];
			double[] maxMin = maxMinList.get(i);
			double max = maxMin[0];
			double min = maxMin[1];

			double value = oldValue * (max - min) + min;
			data[i] = value;
		}
	}

	private static List<double[]> integerListToDoubleArrayList(List<Integer> integerList, int maxIndex) {
		List<double[]> arrayList = new ArrayList<double[]>();
		for (int value : integerList) {
			double[] array = new double[maxIndex];
			array[value] = 1;
			arrayList.add(array);
		}
		return arrayList;
	}

	public static double[][] doubleArrayListTo2DimDoubleArray(List<double[]> doubleArrayList){
		int x = doubleArrayList.size();
		int y = doubleArrayList.get(0).length;
		double[][] twoDimArray = new double[x][y];
		for(int i=0;i<x;i++){
			System.arraycopy(doubleArrayList.get(i),0,twoDimArray[i],0,y);
		}
		return twoDimArray;
	}

	private static void saveIntegerListToFile(List<Integer> integerList, File file) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			for (Integer integer : integerList) {
				out.append(integer.toString());
				out.newLine();
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static List<Integer> loadIntegerListFromFile(File file) throws IOException {
		BufferedReader in = null;
		List<Integer> integerList = new ArrayList<Integer>();
		try {
			in = new BufferedReader(new FileReader(file));
			while(true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				integerList.add(Integer.parseInt(line));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return integerList;
	}

	public static double[] loadDoubleArrayFromFile(File file) throws IOException {
		BufferedReader in = null;
		List<Double> integerList = new ArrayList<Double>();
		try {
			in = new BufferedReader(new FileReader(file));
			while(true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				integerList.add(Double.parseDouble(line));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}

		double[] doubleArray = new double[integerList.size()];
		for(int i = 0;i<integerList.size();i++){
			doubleArray[i]=integerList.get(i).doubleValue();
		}

		return doubleArray;
	}

	public static synchronized void saveStringListToFile(List<String> stringList, File file) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			for (String string : stringList) {
				out.append(string);
				out.newLine();
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static List<String> loadStringListFromFile(File file) throws IOException {
		BufferedReader in = null;
		List<String> stringList = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(file));
			while(true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				stringList.add(line);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return stringList;
	}

	public static synchronized void saveDoubleArrayListToFile(List<double[]> data, File file) throws IOException {

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			for (double[] array : data) {
				for (int i = 0; i < array.length; i++) {
					out.append(String.valueOf(array[i]));
					if (i != array.length - 1) {
						out.append(" ");
					}
				}
				out.newLine();
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static List<double[]> loadDoubleArrayListFromFile(File file) throws IOException {
		BufferedReader in = null;
		List<double[]> doubleArrayList = new ArrayList<double[]>();
		try {
			in = new BufferedReader(new FileReader(file));
			while(true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}

				doubleArrayList.add(doubleArrayFromStr(line));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return doubleArrayList;
	}

	public static double[] doubleArrayFromStr(String line){
		String[] stringArray = line.trim().split("\\s+");
		double[] doubleArray = new double[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			doubleArray[i] = Double.parseDouble(stringArray[i]);
		}
		return doubleArray;
	}

	public static DenseMatrix64F loadMarixFromFile(File matrixFile, File sizeFile) throws IOException{

		BufferedReader reader = new BufferedReader(new FileReader(sizeFile));

		String[] sizeStr = reader.readLine().trim().split(" ");
		int rows = Integer.parseInt(sizeStr[0]);
		int cols = Integer.parseInt(sizeStr[1]);
		DenseMatrix64F res = new DenseMatrix64F(rows, cols);

		reader = new BufferedReader(new FileReader(matrixFile));
		for(int row = 0; row < rows; row++){
			String[] line = reader.readLine().trim().split(" ");
			for(int col = 0; col < cols; col++) {
				res.set(row, col, Double.parseDouble(line[col]));
			}
		}

		return res;
	}

	private static void saveObject(Object obj, File file) throws IOException {
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
		try {
			stream.writeObject(obj);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static PCA loadPCA(File file) throws IOException {
		PCA pca = null;
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
		try {
			pca = (PCA) stream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return pca;
	}

	public static int getMaxIndex(double[] array) {
		int maxIndex = 0;
		for (int i = 1; i < array.length; i++) {
			if( array[i] > array[maxIndex] ) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	public static double getTwoBestRatio( double[] array ) {
		int m1 = getMaxIndex( array );
		int m2 = (m1+1)%array.length;
		for( int i = 0; i < array.length; ++i ) {
			if( (i != m1) && (array[i] > array[m2]) ) {
				m2 = i;
			}
		}

		if( array[m2] < 1e-3 ) {
			return 1000;
		}

		return array[m1]/array[m2];
	}

	public static void mulArrayElems(double[] array,double factor,int length){
		for (int i = 0; i < length; i++) {
			array[i]*=factor;
		}
	}

	public static void addArray1ToArray2(double[] array1, double[] array2, int length) {
		for (int i = 0; i < length; i++) {
			array2[i] += array1[i];
		}
	}

	public static int maxIndex(double[] array){
		int maxIndex=-1;
		double max=-1*Double.MAX_VALUE;
		for(int i=0;i<array.length;i++){
			if(array[i]>max){
				max=array[i];
				maxIndex=i;
			}
		}
		return maxIndex;
	}

	public static String toString(List<double[]> featureList){

		StringBuilder b = new StringBuilder();
		for(double[] feature : featureList){
			b.append(Arrays.toString(feature));
			b.append(',');
		}
		b.insert(0,'[');
		b.setCharAt(b.length()-1,']');
		return b.toString();
	}

	public static void main(String[] args) throws IOException {
		//		LearningUtils.createTrainigAndTestImageList(new File("data/selected images.txt"), 0.2);
		//		ClassifyUtils.updateSamples();
	}

	public static double sse(double[] output, double[] expectedOutput) {

		double tot=0;
		for(int i=0;i<output.length;i++){
			tot+=(output[i]-expectedOutput[i])*(output[i]-expectedOutput[i]);
		}
		return tot;
	}

	public static List<double[]> getDataInMaxMinListOverride() {

		List<double[]> maxMinList = new ArrayList<double[]>();
		Properties normalizeProps = new Properties();
		try {
			normalizeProps.load(new FileInputStream("src/main/resources/NormalizeMaxMixOverrides.properties"));
		} catch (FileNotFoundException e) {
			//ignorable
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		for(String featureName : MatchLearningInstance.getFeatureNames()){
			String maxMin = normalizeProps.getProperty(featureName);
			if(maxMin!=null){
				String[] _split = maxMin.split(",");
				maxMinList.add(new double[]{Double.valueOf(_split[1]),Double.valueOf(_split[0])});
			}else{
				maxMinList.add(null);
			}
		}
		return maxMinList;
	}
}