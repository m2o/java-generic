package hr.tennis.bot.learner.feature;

import hr.tennis.bot.learner.utils.ClassifyUtils;
import hr.tennis.bot.learner.utils.FileSet;
import hr.tennis.bot.model.Constants;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.model.TournamentValue;
import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.learning.MatchLearningInstance;
import hr.tennis.bot.model.learning.MatchLearningInstanceFactory;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.ArrayUtil;
import hr.tennis.bot.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class FeatureExtractor {



	private static void extractRawFeatures(List<SinglesMatch> matches,
			File fileIn,
			File fileOut,
			File fileNames,
			File fileCoefficients) throws IOException {

		List<double[]> dataIn = new ArrayList<double[]>();
		List<double[]> dataOut = new ArrayList<double[]>();
		List<String> names = new ArrayList<String>();
		List<double[]> coefficients = new ArrayList<double[]>();

		Date minDate = null;
		Date maxDate = null;

		if(matches.size()>0){
			minDate = matches.get(0).getDate();
			maxDate = matches.get(0).getDate();
		}

		for(SinglesMatch match : matches){

			MatchLearningInstance learningInstance = MatchLearningInstanceFactory.createMatchLearningInstance(match);
			//System.out.printf("created instance - %s\n",learningInstance);
			
			//if one of the features is NaN continue!!
			if(!learningInstance.validate()) continue;

			dataIn.add(learningInstance.getDataIn());
			dataOut.add(learningInstance.getDataOut());
			names.add(learningInstance.getName());

			double expectedOutcomeCoeffiecient,notExpectedOutcomeCoeffiecient;

			if(match.isExpectedOutcome()){
				expectedOutcomeCoeffiecient = match.getAverageOdd().getWinnerWinningCoefficient();
				notExpectedOutcomeCoeffiecient = match.getAverageOdd().getLoserWinningCoefficient();
			}else{
				expectedOutcomeCoeffiecient = match.getAverageOdd().getLoserWinningCoefficient();
				notExpectedOutcomeCoeffiecient = match.getAverageOdd().getWinnerWinningCoefficient();
			}

			coefficients.add(new double[]{expectedOutcomeCoeffiecient,notExpectedOutcomeCoeffiecient});

			Date date = match.getDate();
			if(date.after(maxDate)){
				maxDate = date;
			}
			if(date.before(minDate)){
				minDate = date;
			}
		}

		ClassifyUtils.saveDoubleArrayListToFile(dataIn, fileIn);
		ClassifyUtils.saveDoubleArrayListToFile(dataOut, fileOut);
		ClassifyUtils.saveDoubleArrayListToFile(coefficients, fileCoefficients);

		names.add("");
		names.add(String.format("#n:%d",dataIn.size()));
		names.add(String.format("#r:%d",dataIn.size()>0 ? dataIn.get(0).length : -1));
		names.add(String.format("#min date:%s",minDate!=null ? DateUtil.formatDateSimple(minDate) : "?"));
		names.add(String.format("#max date:%s",minDate!=null ? DateUtil.formatDateSimple(maxDate) : "?"));
		ClassifyUtils.saveStringListToFile(names, fileNames);

		System.out.printf("extracted raw features - %s\n",fileIn.getAbsolutePath());
	}

	private static void extractRawFeatures(List<SinglesMatch> trainMatches,
			List<SinglesMatch> validateMatches,
			List<SinglesMatch> testMatchesAll,
			Gender gender,
			TournamentType type) throws IOException {

		FileSet set = FileSet.fromType(gender, type);

		
		extractRawFeatures(trainMatches,set.rawFeatureTrainIn,set.rawFeatureTrainOut,set.rawFeatureTrainNames,set.rawFeatureTrainCoefficients);
		extractRawFeatures(validateMatches,set.rawFeatureValidateIn,set.rawFeatureValidateOut,set.rawFeatureValidateNames,set.rawFeatureValidateCoefficients);
		extractRawFeatures(testMatchesAll,set.rawFeatureTestAllIn,set.rawFeatureTestAllOut,set.rawFeatureTestAllNames,set.rawFeatureTestAllCoefficients);

		//TODO - find some better way to sync remaining values
		MatchLearningInstanceFactory.cache.sync();
	}

	public static void extractFeatures(Gender gender,TournamentType type) throws IOException{

		//		String[] suffixs = {"","_pca"};

		//		int PCA_N = 25;

		//		for(String suffix : suffixs){

		//			String rawFileTemplate = String.format("data/raw-features/%s/%s/raw_data_%%s_%%s",
		//				        						type.toString().toLowerCase(),
		//				        						gender.toString().toLowerCase());
		//
		//			String fileTemplate = String.format("data/features/%s/%s/data_%%s_%%s",
		//												type.toString().toLowerCase(),
		//												gender.toString().toLowerCase());
		//
		//			FileSet rawFeatureFileSet = FileSet.fromTemplate(rawFileTemplate);
		//			FileSet featureFileSet = FileSet.fromTemplate(fileTemplate);

		FileSet set = FileSet.fromType(gender, type);

		//File normalizeFile = new File("stockdata/features/data_next_open_"+part+"_normalize"+suffix);
		//File pcaFile = new File("data/pca/pca_next_open_"+part+".dat");

		//			if(suffix.equals("")){
		normalizeRawFeatures(set);
		//			}else{
		//				extractFeaturesWithPCA(rawTrainFile_in, rawTestFile_in, trainFile_in, testFile_in,
		//						rawTrainFile_out, rawTestFile_out, trainFile_out, testFile_out,
		//						normalizeFile,pcaFile,PCA_N);
		//			}
		//		}

	}

	protected static void normalizeRawFeatures(FileSet set) throws IOException {

		// load features for train data
		List<double[]> trainFeatures = ClassifyUtils.loadDoubleArrayListFromFile(set.rawFeatureTrainIn);
		List<double[]> validateFeatures = ClassifyUtils.loadDoubleArrayListFromFile(set.rawFeatureValidateIn);
		List<double[]> testFeaturesAll = ClassifyUtils.loadDoubleArrayListFromFile(set.rawFeatureTestAllIn);

		// get min and max for normalize
		List<double[]> maxMinList = ClassifyUtils.extractMaxMinListFromFeatures(trainFeatures);
		List<double[]> overrideMaxMinList = ClassifyUtils.getDataInMaxMinListOverride();

		for(int i=0;i<maxMinList.size();i++){
			if((overrideMaxMinList==null) || (overrideMaxMinList.size()<=i)){
				break;
			}
			double[] maxMin = overrideMaxMinList.get(i);
			if(maxMin!=null){
				maxMinList.set(i,maxMin);
			}
		}

		// normalize
		ClassifyUtils.normalize(maxMinList, trainFeatures);
		ClassifyUtils.normalize(maxMinList, validateFeatures);
		ClassifyUtils.normalize(maxMinList, testFeaturesAll);

		// save data to files
		ClassifyUtils.saveDoubleArrayListToFile(maxMinList, set.normalize);
		ClassifyUtils.saveDoubleArrayListToFile(trainFeatures, set.featureTrainIn);
		ClassifyUtils.saveDoubleArrayListToFile(validateFeatures, set.featureValidateIn);
		ClassifyUtils.saveDoubleArrayListToFile(testFeaturesAll, set.featureTestAllIn);

		//copy raw-features to features
		FileUtils.copyFile(set.rawFeatureTrainOut,set.featureTrainOut);
		FileUtils.copyFile(set.rawFeatureValidateOut,set.featureValidateOut);
		FileUtils.copyFile(set.rawFeatureTestAllOut, set.featureTestAllOut);

		//System.out.println("normalized features");
	}

	private static List<SinglesMatch> findInstances(Gender gender,Date dateFrm,Date dateTo, TournamentValue[] tournamentValues){

		long startTs = System.currentTimeMillis();

		List<Integer> tournamentValueOrdinals = ArrayUtil.toOrdinalArray(tournamentValues);

		String queryValue = "select distinct sm from SinglesMatch sm " +
		"join sm.odds o " +
		//"join sm.round r "+
		//"join r.tournamentInstance ti "+
		//"join ti.tournament t "+
		"join fetch sm.odds _odd "+
		"join fetch _odd.bettingHouse "+
		"join fetch sm.winnerPlayer "+
		"join fetch sm.loserPlayer "+
		"join fetch sm.winnerPlayerRanking "+
		"join fetch sm.loserPlayerRanking "+
		"join fetch sm.round _round "+
		"join fetch _round.tournamentInstance _ti "+
		"join fetch _ti.tournament "+
		"where " +
		"sm.date >= :dateFrm and " +
		"sm.date <= :dateTo and " +
		"sm.winnerPlayer.gender = :gender and " +
		"sm.winnerPlayerRanking is not NULL and " +
		"sm.loserPlayerRanking is not NULL and " +
		"sm.result.forfeited = false and " +
		(tournamentValues!=null ? String.format("sm.round.tournamentInstance.tournament.value in (%s) and ",StringUtils.join(tournamentValueOrdinals,',')) : "") +
		"o.bettingHouse.name = :bhname and " +
		"o.winnerWinningCoefficient >= 1.0 and " +
		"o.winnerWinningCoefficient <= 10.0 and " +
		"o.loserWinningCoefficient >= 1.0 and " +
		"o.loserWinningCoefficient <= 10.0 and " +
		"(o.loserWinningCoefficient<=1.9 or o.winnerWinningCoefficient<=1.9) " +
		"order by sm.date";

		Query query = PersistenceUtil.getEntityManager()
		.createQuery(queryValue)
		.setParameter("dateFrm", dateFrm)
		.setParameter("dateTo", dateTo)
		.setParameter("gender", gender)
		.setParameter("bhname", Constants.BETTING_HOUSE_AVERAGE);

		List<SinglesMatch> matches = query.getResultList();

		long endTs = System.currentTimeMillis();
		double duration = (endTs-startTs)/1000.0;

		System.out.printf("%s %s-%s %s - #%d %.2fs\n",gender,dateFrm,dateTo,Arrays.toString(tournamentValues),matches.size(),duration);
		return matches;
	}

	private static void extractMaleSingleFeatures() throws IOException {

		List<SinglesMatch> trainMatches = null;
		List<SinglesMatch> validateMatches = null;
		List<SinglesMatch> testMatchesAll =  null;


		if("toni".equalsIgnoreCase(System.getenv("USERNAME"))){

			trainMatches = findInstances(Gender.MALE,
					DateUtil.createDate(2008, 1, 1),
					DateUtil.createDate(2009, 12, 31),
					new TournamentValue[]{TournamentValue.GRANDSLAM,
				TournamentValue.MASTERS,
				TournamentValue.ATP_500,
				TournamentValue.ATP_250});

			validateMatches = new ArrayList<SinglesMatch>(0);

			testMatchesAll = findInstances(Gender.MALE,
					DateUtil.createDate(2010, 1, 1),
					DateUtil.createDate(2011, 12, 31),
					new TournamentValue[]{TournamentValue.GRANDSLAM,
				TournamentValue.MASTERS,
				TournamentValue.ATP_500,
				TournamentValue.ATP_250});

		}else{

			trainMatches = findInstances(Gender.MALE,
					DateUtil.createDate(2008, 1, 1),
					DateUtil.createDate(2010, 1, 31),
					new TournamentValue[]{TournamentValue.GRANDSLAM,
				TournamentValue.MASTERS,
				TournamentValue.ATP_500,
				TournamentValue.ATP_250});

			validateMatches = new ArrayList<SinglesMatch>(0);

			testMatchesAll = findInstances(Gender.MALE,
					DateUtil.createDate(2011, 1, 1),
					DateUtil.createDate(2011, 12, 31),
					new TournamentValue[]{TournamentValue.GRANDSLAM,
				TournamentValue.MASTERS,
				TournamentValue.ATP_500,
				TournamentValue.ATP_250});
		}

		extractRawFeatures(trainMatches,validateMatches,testMatchesAll,Gender.MALE,TournamentType.SINGLE);
		extractFeatures(Gender.MALE,TournamentType.SINGLE);
	}

	private static void extractFemaleSingleFeatures() throws IOException {
		List<SinglesMatch> trainMatches = findInstances(Gender.FEMALE,
				DateUtil.createDate(2007, 1, 1),
				DateUtil.createDate(2007, 1, 31),
				null);

		List<SinglesMatch> validateMatches = findInstances(Gender.FEMALE,
				DateUtil.createDate(2010, 1, 1),
				DateUtil.createDate(2010, 1,31),
				null);

		List<SinglesMatch> testMatches = findInstances(Gender.FEMALE,
				DateUtil.createDate(2011, 1, 1),
				DateUtil.createDate(2011, 1,31),
				null);

		extractRawFeatures(trainMatches,validateMatches,testMatches,Gender.FEMALE,TournamentType.SINGLE);
		extractFeatures(Gender.FEMALE,TournamentType.SINGLE);
	}

	/**
	 * run with jvm options:
	 * -Xms512m -Xmx1024m (increases start/max heap size)
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		long startTs = System.currentTimeMillis();
		extractMaleSingleFeatures();
		//		extractFemaleSingleFeatures();
		long endTs = System.currentTimeMillis();
		System.out.printf("total duration: %.2fs\n",(endTs-startTs)/1000.0);

		PersistenceUtil.getEntityManager().close();
		PersistenceUtil.close();
	}

}
