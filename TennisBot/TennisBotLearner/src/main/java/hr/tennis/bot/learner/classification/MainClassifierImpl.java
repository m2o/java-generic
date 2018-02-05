//package hr.tennis.bot.learner.classification;
//
//import hr.fer.nm_projekt.classifiers.ClassifierTest;
//import hr.fer.nm_projekt.classifiers.IClassifier;
//import hr.fer.nm_projekt.classifiers.KNNClassifier;
//import hr.fer.nm_projekt.classifiers.MultilayerPerceptron;
//import hr.fer.nm_projekt.classifiers.RadialBasisNetwork;
//import hr.fer.nm_projekt.exec.Executor;
//import hr.fer.nm_projekt.exec.ProcessInfo;
//import hr.fer.nm_projekt.utilities.ClassifyUtils;
//import hr.fer.nm_projekt.utilities.PCA;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//public class MainClassifierImpl implements MainClassifier {
//
//
//	private final IClassifier classifier_knn_1day = new KNNClassifier();
//	private final IClassifier classifier_knn_1week = new KNNClassifier();
//	private final IClassifier classifier_knn_1month = new KNNClassifier();
//	private final IClassifier classifier_knn_3month = new KNNClassifier();
//	private final IClassifier classifier_knn_6month = new KNNClassifier();
//	private final IClassifier classifier_knn_9month = new KNNClassifier();
//	private final IClassifier classifier_knn_12month = new KNNClassifier();
//
//	private final IClassifier classifier_knn_pca_1day = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_1week = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_1month = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_3month = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_6month = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_9month = new KNNClassifier();
//	private final IClassifier classifier_knn_pca_12month = new KNNClassifier();
//
//	private final IClassifier classifier_mlp_1day = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_1week = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_1month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_3month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_6month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_9month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_12month = new MultilayerPerceptron();
//
//	private final IClassifier classifier_mlp_pca_1day = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_1week = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_1month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_3month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_6month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_9month = new MultilayerPerceptron();
//	private final IClassifier classifier_mlp_pca_12month = new MultilayerPerceptron();
//
//	private final IClassifier classifier_rb_1day = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_1week = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_1month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_3month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_6month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_9month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_12month = new RadialBasisNetwork();
//
//	private final IClassifier classifier_rb_pca_1day = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_1week = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_1month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_3month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_6month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_9month = new RadialBasisNetwork();
//	private final IClassifier classifier_rb_pca_12month = new RadialBasisNetwork();
//
//	private final PCA pca_1day;
//	private final PCA pca_1week;
//	private final PCA pca_1month;
//	private final PCA pca_3month;
//	private final PCA pca_6month;
//	private final PCA pca_9month;
//	private final PCA pca_12month;
//
//	private final List<double[]> normalize_1day;
//	private final List<double[]> normalize_1week;
//	private final List<double[]> normalize_1month;
//	private final List<double[]> normalize_3month;
//	private final List<double[]> normalize_6month;
//	private final List<double[]> normalize_9month;
//	private final List<double[]> normalize_12month;
//
//	private final List<double[]> normalize_pca_1day;
//	private final List<double[]> normalize_pca_1week;
//	private final List<double[]> normalize_pca_1month;
//	private final List<double[]> normalize_pca_3month;
//	private final List<double[]> normalize_pca_6month;
//	private final List<double[]> normalize_pca_9month;
//	private final List<double[]> normalize_pca_12month;
//
//	private double[] buffer;
//
//	private boolean debug;
//
//	public MainClassifierImpl() throws IOException {
//
//		System.out.println("loading classifiers...");
//
//		// load configurations from file
//		this.classifier_knn_1day.loadFrom(new File("data/classifiers/knn_next_open_1day.dat"));
//		this.classifier_knn_1week.loadFrom(new File("data/classifiers/knn_next_open_1week.dat"));
//		this.classifier_knn_1month.loadFrom(new File("data/classifiers/knn_next_open_1month.dat"));
//		this.classifier_knn_3month.loadFrom(new File("data/classifiers/knn_next_open_3month.dat"));
//		this.classifier_knn_6month.loadFrom(new File("data/classifiers/knn_next_open_6month.dat"));
//		this.classifier_knn_9month.loadFrom(new File("data/classifiers/knn_next_open_9month.dat"));
//		this.classifier_knn_12month.loadFrom(new File("data/classifiers/knn_next_open_12month.dat"));
//
//		this.classifier_knn_pca_1day.loadFrom(new File("data/classifiers/knn_next_open_1day_pca.dat"));
//		this.classifier_knn_pca_1week.loadFrom(new File("data/classifiers/knn_next_open_1week_pca.dat"));
//		this.classifier_knn_pca_1month.loadFrom(new File("data/classifiers/knn_next_open_1month_pca.dat"));
//		this.classifier_knn_pca_3month.loadFrom(new File("data/classifiers/knn_next_open_3month_pca.dat"));
//		this.classifier_knn_pca_6month.loadFrom(new File("data/classifiers/knn_next_open_6month_pca.dat"));
//		this.classifier_knn_pca_9month.loadFrom(new File("data/classifiers/knn_next_open_9month_pca.dat"));
//		this.classifier_knn_pca_12month.loadFrom(new File("data/classifiers/knn_next_open_12month_pca.dat"));
//
//		this.classifier_mlp_1day.loadFrom(new File("data/classifiers/mlp_next_open_1day.dat"));
//		this.classifier_mlp_1week.loadFrom(new File("data/classifiers/mlp_next_open_1week.dat"));
//		this.classifier_mlp_1month.loadFrom(new File("data/classifiers/mlp_next_open_1month.dat"));
//		this.classifier_mlp_3month.loadFrom(new File("data/classifiers/mlp_next_open_3month.dat"));
//		this.classifier_mlp_6month.loadFrom(new File("data/classifiers/mlp_next_open_6month.dat"));
//		this.classifier_mlp_9month.loadFrom(new File("data/classifiers/mlp_next_open_9month.dat"));
//		this.classifier_mlp_12month.loadFrom(new File("data/classifiers/mlp_next_open_12month.dat"));
//
//		this.classifier_mlp_pca_1day.loadFrom(new File("data/classifiers/mlp_next_open_1day_pca.dat"));
//		this.classifier_mlp_pca_1week.loadFrom(new File("data/classifiers/mlp_next_open_1week_pca.dat"));
//		this.classifier_mlp_pca_1month.loadFrom(new File("data/classifiers/mlp_next_open_1month_pca.dat"));
//		this.classifier_mlp_pca_3month.loadFrom(new File("data/classifiers/mlp_next_open_3month_pca.dat"));
//		this.classifier_mlp_pca_6month.loadFrom(new File("data/classifiers/mlp_next_open_6month_pca.dat"));
//		this.classifier_mlp_pca_9month.loadFrom(new File("data/classifiers/mlp_next_open_9month_pca.dat"));
//		this.classifier_mlp_pca_12month.loadFrom(new File("data/classifiers/mlp_next_open_12month_pca.dat"));
//
//		this.classifier_rb_1day.loadFrom(new File("data/classifiers/rb_next_open_1day.dat"));
//		this.classifier_rb_1week.loadFrom(new File("data/classifiers/rb_next_open_1week.dat"));
//		this.classifier_rb_1month.loadFrom(new File("data/classifiers/rb_next_open_1month.dat"));
//		this.classifier_rb_3month.loadFrom(new File("data/classifiers/rb_next_open_3month.dat"));
//		this.classifier_rb_6month.loadFrom(new File("data/classifiers/rb_next_open_6month.dat"));
//		this.classifier_rb_9month.loadFrom(new File("data/classifiers/rb_next_open_9month.dat"));
//		this.classifier_rb_12month.loadFrom(new File("data/classifiers/rb_next_open_12month.dat"));
//
//		this.classifier_rb_pca_1day.loadFrom(new File("data/classifiers/rb_next_open_1day_pca.dat"));
//		this.classifier_rb_pca_1week.loadFrom(new File("data/classifiers/rb_next_open_1week_pca.dat"));
//		this.classifier_rb_pca_1month.loadFrom(new File("data/classifiers/rb_next_open_1month_pca.dat"));
//		this.classifier_rb_pca_3month.loadFrom(new File("data/classifiers/rb_next_open_3month_pca.dat"));
//		this.classifier_rb_pca_6month.loadFrom(new File("data/classifiers/rb_next_open_6month_pca.dat"));
//		this.classifier_rb_pca_9month.loadFrom(new File("data/classifiers/rb_next_open_9month_pca.dat"));
//		this.classifier_rb_pca_12month.loadFrom(new File("data/classifiers/rb_next_open_12month_pca.dat"));
//
//		// load configurations of PCA
//		this.pca_1day = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_1day.dat"));
//		this.pca_1week = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_1week.dat"));
//		this.pca_1month = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_1month.dat"));
//		this.pca_3month = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_3month.dat"));
//		this.pca_6month = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_6month.dat"));
//		this.pca_9month = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_9month.dat"));
//		this.pca_12month = ClassifyUtils.loadPCA(new File("data/pca/pca_next_open_12month.dat"));
//
//		// load data for normalization
//		this.normalize_1day = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_1week = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_1month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_3month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_6month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_9month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//		this.normalize_12month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize"));
//
//		this.normalize_pca_1day = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_1week = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_1month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_3month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_6month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_9month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//		this.normalize_pca_12month = ClassifyUtils.loadDoubleArrayListFromFile(new File("stockdata/features/data_next_open_1day_normalize_pca"));
//	}
//
//	@Override
//	public double[] classify(String stockname, Date date, PredictionInterval interval) {
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//
//		List<double[]> normalize_list = getNormalize(interval);
//
//		PCA pca = getPCA(interval);
//		List<double[]> normalize_pca_list = getNormalizePCA(interval);
//
//		ProcessInfo pInfo = Executor.execute("python python/create.py "+stockname+" "+format.format(date));
//		if(pInfo.exitValue!=0){
//			System.err.println(pInfo.stdErr);
//			System.exit(1);
//		}
//
//		int i = interval.ordinal();
//		String[] data = pInfo.stdOut.split("\n");
//		String stockName = data[0];
//		String stockIn = data[1+i*3];
//		String prices = data[1+i*3+2];
//		String[] pricesA = prices.split(" ");
//
//		System.out.println();
//		System.out.println(stockName);
//		System.out.printf("Trenutna cijena: %s $\n",pricesA[0]);
//		System.out.printf("Predvi�ena cijena: %s $\n",pricesA[1]);
//		System.out.println();
//
//		double[] features = ClassifyUtils.doubleArrayFromStr(stockIn);
//		double[] features_pca = features.clone();
//
//		ClassifyUtils.normalize(normalize_list, features);
//
//		features_pca = pca.sampleToEigenSpace(features_pca);
//		ClassifyUtils.normalize(normalize_pca_list, features_pca);
//
//		this.buffer = new double[Category.CATEGORY_COUNT];
//		classifyFeatures(features,features_pca,this.buffer,Category.CATEGORY_COUNT,interval);
//		return this.buffer;
//	}
//
//	public void classifyFeatures(double[] features,double[] features_pca,double[] output,int dim,PredictionInterval interval){
//		IClassifier knn = getKNN(interval);
//		IClassifier knn_pca = getKNN_pca(interval);
//		IClassifier mlp = getMLP(interval);
//		IClassifier mlp_pca = getMLP_pca(interval);
//		IClassifier rb = getRB(interval);
//		IClassifier rb_pca = getRB_pca(interval);
//
//		Arrays.fill(output, 0);
//
//		double[] buffer = new double[dim];
//
//		/*knn*/
//		Arrays.fill(buffer, 0);
//		knn.classify(features,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",knn.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//
//		/*knn_pca*/
//		Arrays.fill(buffer, 0);
//		knn_pca.classify(features_pca,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",knn_pca.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//
//		/*mlp*/
//		Arrays.fill(buffer, 0);
//		mlp.classify(features,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",mlp.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//
//		/*mlp_pca*/
//		Arrays.fill(buffer, 0);
//		mlp_pca.classify(features_pca,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",mlp_pca.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//
//		/*rb*/
//		Arrays.fill(buffer, 0);
//		rb.classify(features,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",rb.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//
//		/*rb_pca*/
//		Arrays.fill(buffer, 0);
//		rb_pca.classify(features_pca,dim,buffer);
//		if(this.debug){
//			System.out.printf("%s -> %s\n",rb_pca.toString(),Arrays.toString(buffer));
//		}
//		ClassifyUtils.addArray1ToArray2(buffer, output, dim);
//		ClassifyUtils.normalizeArray(output, dim);
//	}
//
//	private IClassifier getKNN(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_knn_1day;
//		case ONE_WEEK:
//			return this.classifier_knn_1week;
//		case ONE_MONTH:
//			return this.classifier_knn_1month;
//		case THREE_MONTHS:
//			return this.classifier_knn_3month;
//		case SIX_MONTHS:
//			return this.classifier_knn_6month;
//		case NINE_MONTHS:
//			return this.classifier_knn_9month;
//		case ONE_YEAR:
//			return this.classifier_knn_12month;
//		default:
//			return null;
//		}
//	}
//
//	private IClassifier getKNN_pca(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_knn_pca_1day;
//		case ONE_WEEK:
//			return this.classifier_knn_pca_1week;
//		case ONE_MONTH:
//			return this.classifier_knn_pca_1month;
//		case THREE_MONTHS:
//			return this.classifier_knn_pca_3month;
//		case SIX_MONTHS:
//			return this.classifier_knn_pca_6month;
//		case NINE_MONTHS:
//			return this.classifier_knn_pca_9month;
//		case ONE_YEAR:
//			return this.classifier_knn_pca_12month;
//		default:
//			return null;
//		}
//	}
//
//	private IClassifier getMLP(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_mlp_1day;
//		case ONE_WEEK:
//			return this.classifier_mlp_1week;
//		case ONE_MONTH:
//			return this.classifier_mlp_1month;
//		case THREE_MONTHS:
//			return this.classifier_mlp_3month;
//		case SIX_MONTHS:
//			return this.classifier_mlp_6month;
//		case NINE_MONTHS:
//			return this.classifier_mlp_9month;
//		case ONE_YEAR:
//			return this.classifier_mlp_12month;
//		default:
//			return null;
//		}
//	}
//
//	private IClassifier getMLP_pca(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_mlp_pca_1day;
//		case ONE_WEEK:
//			return this.classifier_mlp_pca_1week;
//		case ONE_MONTH:
//			return this.classifier_mlp_pca_1month;
//		case THREE_MONTHS:
//			return this.classifier_mlp_pca_3month;
//		case SIX_MONTHS:
//			return this.classifier_mlp_pca_6month;
//		case NINE_MONTHS:
//			return this.classifier_mlp_pca_9month;
//		case ONE_YEAR:
//			return this.classifier_mlp_pca_12month;
//		default:
//			return null;
//		}
//	}
//
//	private IClassifier getRB(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_rb_1day;
//		case ONE_WEEK:
//			return this.classifier_rb_1week;
//		case ONE_MONTH:
//			return this.classifier_rb_1month;
//		case THREE_MONTHS:
//			return this.classifier_rb_3month;
//		case SIX_MONTHS:
//			return this.classifier_rb_6month;
//		case NINE_MONTHS:
//			return this.classifier_rb_9month;
//		case ONE_YEAR:
//			return this.classifier_rb_12month;
//		default:
//			return null;
//		}
//	}
//
//	private IClassifier getRB_pca(PredictionInterval interval) {
//		switch (interval) {
//		case ONE_DAY:
//			return this.classifier_rb_pca_1day;
//		case ONE_WEEK:
//			return this.classifier_rb_pca_1week;
//		case ONE_MONTH:
//			return this.classifier_rb_pca_1month;
//		case THREE_MONTHS:
//			return this.classifier_rb_pca_3month;
//		case SIX_MONTHS:
//			return this.classifier_rb_pca_6month;
//		case NINE_MONTHS:
//			return this.classifier_rb_pca_9month;
//		case ONE_YEAR:
//			return this.classifier_rb_pca_12month;
//		default:
//			return null;
//		}
//	}
//
//	private List<double[]>  getNormalizePCA(PredictionInterval interval){
//		switch (interval) {
//		case ONE_DAY:
//			return this.normalize_pca_1day;
//		case ONE_WEEK:
//			return this.normalize_pca_1week;
//		case ONE_MONTH:
//			return this.normalize_pca_1month;
//		case THREE_MONTHS:
//			return this.normalize_pca_3month;
//		case SIX_MONTHS:
//			return this.normalize_pca_6month;
//		case NINE_MONTHS:
//			return this.normalize_pca_9month;
//		case ONE_YEAR:
//			return this.normalize_pca_12month;
//		default:
//			return null;
//		}
//	}
//
//	private List<double[]>  getNormalize(PredictionInterval interval){
//		switch (interval) {
//		case ONE_DAY:
//			return this.normalize_1day;
//		case ONE_WEEK:
//			return this.normalize_1week;
//		case ONE_MONTH:
//			return this.normalize_1month;
//		case THREE_MONTHS:
//			return this.normalize_3month;
//		case SIX_MONTHS:
//			return this.normalize_6month;
//		case NINE_MONTHS:
//			return this.normalize_9month;
//		case ONE_YEAR:
//			return this.normalize_12month;
//		default:
//			return null;
//		}
//	}
//
//	private PCA  getPCA(PredictionInterval interval){
//		switch (interval) {
//		case ONE_DAY:
//			return this.pca_1day;
//		case ONE_WEEK:
//			return this.pca_1week;
//		case ONE_MONTH:
//			return this.pca_1month;
//		case THREE_MONTHS:
//			return this.pca_3month;
//		case SIX_MONTHS:
//			return this.pca_6month;
//		case NINE_MONTHS:
//			return this.pca_9month;
//		case ONE_YEAR:
//			return this.pca_12month;
//		default:
//			return null;
//		}
//	}
//
//	public void setDebug(boolean debug) {
//		this.debug = debug;
//	}
//
//	public static void main(String[] args) throws IOException {
//
//		String[] parts = {"1day","1week","1month","3month","6month","9month","12month"};
//
//		String[] enums = {"ONE_DAY","ONE_WEEK","ONE_MONTH","THREE_MONTHS","SIX_MONTHS","NINE_MONTHS","ONE_YEAR"};
//
//		String[][] fileData = new String[parts.length*2][4];
//
//		for(int i = 0;i<parts.length;i++){
//			fileData[2*i][0] = "stockdata/features/data_next_open_"+parts[i]+"_test_in";
//			fileData[2*i][1] = "stockdata/features/data_next_open_"+parts[i]+"_test_in_pca";
//			fileData[2*i][2] = "stockdata/features/data_next_open_"+parts[i]+"_test_out";
//			fileData[2*i][3] = enums[i];
//
//			fileData[2*i+1][0] = "stockdata/features/data_next_open_"+parts[i]+"_train_in";
//			fileData[2*i+1][1] = "stockdata/features/data_next_open_"+parts[i]+"_train_in_pca";
//			fileData[2*i+1][2] = "stockdata/features/data_next_open_"+parts[i]+"_train_out";
//			fileData[2*i+1][3] = enums[i];
//		}
//
//		//for (int i = 12;i<parts.length*2;i++) {
//		for (int i = 0;i<parts.length*2;i+=2) {
//			String[] data = fileData[i];
//			File in = new File(data[0]);
//			File in_pca = new File(data[1]);
//			File out = new File(data[2]);
//			PredictionInterval interval = PredictionInterval.valueOf(data[3]);
//
//			MainClassifierImpl impl = new MainClassifierImpl();
//			ClassifierTest.testMain(impl,in,in_pca,out,interval);
//		}
//
//	}
//
//}
