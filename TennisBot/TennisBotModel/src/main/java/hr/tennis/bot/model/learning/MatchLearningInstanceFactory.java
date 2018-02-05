package hr.tennis.bot.model.learning;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;
import hr.tennis.bot.util.FileBasedCache;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MatchLearningInstanceFactory {

	public static FileBasedCache cache;

	static{
		try {
			cache = new FileBasedCache(".featurecache");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static synchronized MatchLearningInstance createMatchLearningInstance(SinglesMatch match) {
		try {
			return _createMatchLearningInstance(match);
		} catch (SecurityException e) {
			//ignorable
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//ignorable
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//ignorable
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			//ignorable
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			//ignorable
			e.printStackTrace();
		}

		System.exit(1);
		return null;
	}

	private static MatchLearningInstance _createMatchLearningInstance(SinglesMatch match) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		MatchLearningInstance learningInstance = new MatchLearningInstance();
		MatchLearningInstanceMaker maker = new MatchLearningInstanceMaker(match);
		List<String> featureList = MatchLearningInstance.getFeatureNames();
		List<String> builtinFeatureList = MatchLearningInstance.getBuiltinFeatureNames();

		for(Field field : MatchLearningInstance.class.getDeclaredFields()){

			String fieldName = field.getName();

			if(featureList.contains(fieldName) || builtinFeatureList.contains(fieldName) ){

				String key = String.format("%d.%s",match.getId(),fieldName);
				Object value = cache.get(key);

				String calculateFuncName = String.format("calculate%s",StringUtils.capitalize(fieldName));
				Method calculateFunc = MatchLearningInstanceMaker.class.getDeclaredMethod(calculateFuncName);

				Class returnType = calculateFunc.getReturnType();

				if(value==null){
					value = calculateFunc.invoke(maker);
					cache.set(key,value);
				}

				String setterFuncName = String.format("set%s",StringUtils.capitalize(fieldName));
				Method setterFunc = MatchLearningInstance.class.getDeclaredMethod(setterFuncName,returnType);
				setterFunc.invoke(learningInstance,value);
			}
		}
		return learningInstance;
	}

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		List<SinglesMatch> matches = (List<SinglesMatch>)DAO.getResultList("SinglesMatch.findMatchesByDateAndGender",
				new ParamMap("date",DateUtil.createDate(2011, 11, 13))
		.addParameter("gender", Gender.MALE));

		//		List<SinglesMatch> matches = (List<SinglesMatch>)DAO.getResultList("SinglesMatch.findMatchesByGender",
		//									  new ParamMap("gender",Gender.MALE));

		List<MatchLearningInstance> learningInstances = new LinkedList<MatchLearningInstance>();

		for(SinglesMatch match : matches){

			if(match.getAverageOdd()==null){
				continue;
			}

			System.out.println(match);
			MatchLearningInstance learningInstance = MatchLearningInstanceFactory.createMatchLearningInstance(match);
			learningInstances.add(learningInstance);
			System.out.println(learningInstance.getDayOfYear());
			System.out.println(learningInstance.getHomeWin());
			System.out.println(learningInstance.getRound());

			System.out.println(learningInstance.getHomePlayerAverageOdd());
			System.out.println(learningInstance.getAwayPlayerAverageOdd());
		}

		PersistenceUtil.getEntityManager().close();
		PersistenceUtil.close();


	}
}
