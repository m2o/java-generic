package hr.tennis.bot.model.learning;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MatchLearningInstance {

	// name
	private String name;

	// input

	/* generic */

	private Integer dayOfYear;
	// private Surface surface;
	// private Integer tournamentCategory;
	private Double round;

	/* player */

	// private boolean homePlayerPlaysAtHome;
	// private Integer homePlayerAge;
	private Integer homePlayerHeight; 
	private Integer homePlayerWeight; 
	private Integer homePlayerYearsPro;
	private Integer homePlayerPrefferedHand;
	
	//
	// private boolean awayPlayerPlaysAtHome;
	// private Integer awayPlayerAge;
	private Integer awayPlayerHeight; 
	private Integer awayPlayerWeight; 
	private Integer awayPlayerYearsPro; 
	private Integer awayPlayerPrefferedHand;

	/* ranking */

	private Integer homePlayerRankingPoints;
	private Integer awayPlayerRankingPoints;

	private Integer homePlayerRankingPosition;
	private Integer awayPlayerRankingPosition;

	// private Integer homePlayerRankingPointsChange1Month;
	// private Integer homePlayerRankingPointsChange3Month;

	// private Integer awayPlayerRankingPointsChange1Month;
	// private Integer awayPlayerRankingPointsChange3Month;

	/* results */

	// private Integer homePlayerTournamentsWonByCategory;
	// private Integer homePlayerTournamentsWonByCategory12Months;
	// private Double homePlayerTournamentsCompletionByCategory;
	// private Double homePlayerTournamentsCompletionByCategory12Months;
	//
	// private Integer awayPlayerTournamentsWonByCategory;
	// private Integer awayPlayerTournamentsWonByCategory12Months;
	// private Double awayPlayerTournamentsCompletionByCategory;
	// private Double awayPlayerTournamentsCompletionByCategory12Months;

	private Double homePlayerWinningPercentage;
	private Double homePlayerWinningPercentage1Month;
	private Double homePlayerWinningPercentage6Month;
	private Double homePlayerWinningPercentage12Month;
	private Double homePlayerWinningPercentageBySurface;
	private Double homePlayerWinningPercentageBySurface1Month;
	private Double homePlayerWinningPercentageBySurface12Month;

	private Double awayPlayerWinningPercentage;
	private Double awayPlayerWinningPercentage1Month;
	private Double awayPlayerWinningPercentage6Month;
	private Double awayPlayerWinningPercentage12Month;
	private Double awayPlayerWinningPercentageBySurface;
	private Double awayPlayerWinningPercentageBySurface1Month;
	private Double awayPlayerWinningPercentageBySurface12Month;

	private Double h2hWinningPercentage;
	private Double h2hWinningPercentage12Month;
	private Double h2hWinningPercentageBySurface;
	private Double h2hWinningPercentageBySurface12Month;

	/* match statistics */

	private Double homePlayerSetsWonPercentage;
	private Double homePlayerSetsWonPercentage1Month;
	private Double homePlayerSetsWonPercentage6Month;
	private Double homePlayerSetsWonPercentage12Month;
	private Double homePlayerSetsWonPercentageBySurface;
	// private Double homePlayerSetsWonPercentageBySurface1Month;
	private Double homePlayerSetsWonPercentageBySurface12Month;
	//
	private Double awayPlayerSetsWonPercentage;
	private Double awayPlayerSetsWonPercentage1Month;
	private Double awayPlayerSetsWonPercentage6Month;
	private Double awayPlayerSetsWonPercentage12Month;
	private Double awayPlayerSetsWonPercentageBySurface;
	// private Double awayPlayerSetsWonPercentageBySurface1Month;
	private Double awayPlayerSetsWonPercentageBySurface12Month;
	//
	// private Double homePlayerGamesWonPercentage;
	// private Double homePlayerGamesWonPercentage1Month;
	// private Double homePlayerGamesWonPercentage12Month;
	// private Double homePlayerGamesWonPercentageBySurface;
	// private Double homePlayerGamesWonPercentageBySurface1Month;
	// private Double homePlayerGamesWonPercentageBySurface12Month;
	//
	// private Double awayPlayerGamesWonPercentage;
	// private Double awayPlayerGamesWonPercentage1Month;
	// private Double awayPlayerGamesWonPercentage12Month;
	// private Double awayPlayerGamesWonPercentageBySurface;
	// private Double awayPlayerGamesWonPercentageBySurface1Month;
	// private Double awayPlayerGamesWonPercentageBySurface12Month;
	//
	// private Double homePlayerTiebreaksWonPercentage;
	// private Double homePlayerTiebreaksWonPercentage1Month;
	// private Double homePlayerTiebreaksWonPercentage12Month;
	// private Double homePlayerTiebreaksWonPercentageBySurface;
	// private Double homePlayerTiebreaksWonPercentageBySurface1Month;
	// private Double homePlayerTiebreaksWonPercentageBySurface12Month;
	//
	// private Double awayPlayerTiebreaksWonPercentage;
	// private Double awayPlayerTiebreaksWonPercentage1Month;
	// private Double awayPlayerTiebreaksWonPercentage12Month;
	// private Double awayPlayerTiebreaksWonPercentageBySurface;
	// private Double awayPlayerTiebreaksWonPercentageBySurface1Month;
	// private Double awayPlayerTiebreaksWonPercentageBySurface12Month;

	/* odd */

	private Double homePlayerAverageOdd;
	private Double awayPlayerAverageOdd;
	// private Double homePlayerAverageOddSuccess;
	// private Double awayPlayerAverageOddSuccess;

	// output
	private Boolean homeWin;

	public MatchLearningInstance() {

	}

	public static List<String> getFeatureNames() {

		List<String> featureNames = new ArrayList<String>();

		if ("toni".equalsIgnoreCase(System.getenv("USERNAME"))) {
			//featureNames.add("homePlayerRankingPosition");
			//featureNames.add("awayPlayerRankingPosition");
			//			featureNames.add("homePlayerRankingPoints");
			//			featureNames.add("awayPlayerRankingPoints");
			//			featureNames.add("homePlayerAverageOdd");
			//			featureNames.add("awayPlayerAverageOdd");

			//			featureNames.add("homePlayerWinningPercentage");
			//			featureNames.add("awayPlayerWinningPercentage");
			//			featureNames.add("homePlayerWinningPercentage1Month");
			//			featureNames.add("awayPlayerWinningPercentage1Month");
			//			featureNames.add("homePlayerWinningPercentage12Month");
			//			featureNames.add("awayPlayerWinningPercentage12Month");
			//
			//			featureNames.add("homePlayerWinningPercentageBySurface");
			//			featureNames.add("awayPlayerWinningPercentageBySurface");
			//			featureNames.add("homePlayerWinningPercentageBySurface1Month");
			//			featureNames.add("awayPlayerWinningPercentageBySurface1Month");
			//			featureNames.add("homePlayerWinningPercentageBySurface12Month");
			//			featureNames.add("awayPlayerWinningPercentageBySurface12Month");

			//featureNames.add("h2hWinningPercentage");
			//			featureNames.add("h2hWinningPercentage12Month");
			//featureNames.add("h2hWinningPercentageBySurface");
			//			featureNames.add("h2hWinningPercentageBySurface12Month");

			//all features
			//			featureNames.add("dayOfYear");
			//			featureNames.add("round");
			//
			//			featureNames.add("homePlayerPrefferedHand");
			//			featureNames.add("awayPlayerPrefferedHand");
			//
			//			featureNames.add("homePlayerRankingPoints");
			//			featureNames.add("awayPlayerRankingPoints");
			//
			//			featureNames.add("homePlayerRankingPosition");
			//			featureNames.add("awayPlayerRankingPosition");
			//
			//			featureNames.add("homePlayerWinningPercentage");
			//			featureNames.add("homePlayerWinningPercentage1Month");
			//			featureNames.add("homePlayerWinningPercentage12Month");
			//			featureNames.add("homePlayerWinningPercentageBySurface");
			//			featureNames.add("homePlayerWinningPercentageBySurface1Month");
			//			featureNames.add("homePlayerWinningPercentageBySurface12Month");
			//
			//			featureNames.add("awayPlayerWinningPercentage");
			//			featureNames.add("awayPlayerWinningPercentage1Month");
			//			featureNames.add("awayPlayerWinningPercentage12Month");
			//			featureNames.add("awayPlayerWinningPercentageBySurface");
			//			featureNames.add("awayPlayerWinningPercentageBySurface1Month");
			//			featureNames.add("awayPlayerWinningPercentageBySurface12Month");
			//
			//			featureNames.add("h2hWinningPercentage");
			//			featureNames.add("h2hWinningPercentage12Month");
			//			featureNames.add("h2hWinningPercentageBySurface");
			//			featureNames.add("h2hWinningPercentageBySurface12Month");

			//test
			featureNames.add("homePlayerRankingPosition");
			featureNames.add("awayPlayerRankingPosition");
			featureNames.add("h2hWinningPercentage");
			featureNames.add("h2hWinningPercentageBySurface");
			featureNames.add("homePlayerWinningPercentageBySurface");
			featureNames.add("awayPlayerWinningPercentageBySurface");

		} else {
			
			//featureNames.add("homePlayerRankingPosition");
			//featureNames.add("awayPlayerRankingPosition");


	
			//featureNames.add("homePlayerWinningPercentage1Month");
			//featureNames.add("awayPlayerWinningPercentage1Month");



			//featureNames.add("homePlayerWinningPercentageBySurface1Month");
			//featureNames.add("awayPlayerWinningPercentageBySurface1Month");
			
			
			
			
//			//featureNames.add("homePlayerRankingPoints");
//			//featureNames.add("awayPlayerRankingPoints");
//			featureNames.add("homePlayerWinningPercentageBySurface12Month");
//			featureNames.add("awayPlayerWinningPercentageBySurface12Month");
			featureNames.add("homePlayerWinningPercentage12Month");
			featureNames.add("homePlayerWinningPercentage6Month");
			featureNames.add("awayPlayerWinningPercentage12Month");
			featureNames.add("awayPlayerWinningPercentage6Month");
			featureNames.add("homePlayerWinningPercentage");
			featureNames.add("awayPlayerWinningPercentage");
//			//featureNames.add("h2hWinningPercentage12Month");
			//featureNames.add("homePlayerAverageOdd");
			//featureNames.add("awayPlayerAverageOdd");
			featureNames.add("homePlayerRankingPosition");
			featureNames.add("awayPlayerRankingPosition");
			featureNames.add("h2hWinningPercentage");
//			//featureNames.add("h2hWinningPercentageBySurface");
			featureNames.add("homePlayerWinningPercentageBySurface");
			featureNames.add("awayPlayerWinningPercentageBySurface");
////			
			featureNames.add("homePlayerHeight");
			featureNames.add("homePlayerWeight");
			featureNames.add("homePlayerYearsPro");
//			
			featureNames.add("awayPlayerHeight");
			featureNames.add("awayPlayerWeight");
			featureNames.add("awayPlayerYearsPro");
//			
			featureNames.add("homePlayerSetsWonPercentage");
			featureNames.add("homePlayerSetsWonPercentageBySurface");
//			//featureNames.add("homePlayerSetsWonPercentageBySurface12Month");
//			//featureNames.add("homePlayerSetsWonPercentage1Month");
			featureNames.add("homePlayerSetsWonPercentage6Month");
			featureNames.add("homePlayerSetsWonPercentage12Month");
//
			featureNames.add("awayPlayerSetsWonPercentage");
			featureNames.add("awayPlayerSetsWonPercentageBySurface");
//			//featureNames.add("awayPlayerSetsWonPercentageBySurface12Month");
//			//featureNames.add("awayPlayerSetsWonPercentage1Month");
			featureNames.add("awayPlayerSetsWonPercentage6Month");
			featureNames.add("awayPlayerSetsWonPercentage12Month");
		}

		return featureNames;
	}

	public double[] getDataOut() {
		if (this.homeWin) {
			return new double[] { 1, 0 };
		} else {
			return new double[] { 0, 1 };
		}
	}

	// getters

	public Integer getDayOfYear() {
		return this.dayOfYear;
	}

	public void setDayOfYear(Integer dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public Boolean getHomeWin() {
		return this.homeWin;
	}

	public void setHomeWin(Boolean homeWin) {
		this.homeWin = homeWin;
	}

	public Double getHomePlayerAverageOdd() {
		return this.homePlayerAverageOdd;
	}

	public void setHomePlayerAverageOdd(Double homePlayerAverageOdd) {
		this.homePlayerAverageOdd = homePlayerAverageOdd;
	}

	public Double getAwayPlayerAverageOdd() {
		return this.awayPlayerAverageOdd;
	}

	public void setAwayPlayerAverageOdd(Double awayPlayerAverageOdd) {
		this.awayPlayerAverageOdd = awayPlayerAverageOdd;
	}

	public Double getRound() {
		return this.round;
	}

	public void setRound(Double round) {
		this.round = round;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHomePlayerRankingPoints() {
		return this.homePlayerRankingPoints;
	}

	public void setHomePlayerRankingPoints(Integer homePlayerRankingPoints) {
		this.homePlayerRankingPoints = homePlayerRankingPoints;
	}

	public Integer getAwayPlayerRankingPoints() {
		return this.awayPlayerRankingPoints;
	}

	public void setAwayPlayerRankingPoints(Integer awayPlayerRankingPoints) {
		this.awayPlayerRankingPoints = awayPlayerRankingPoints;
	}

	public Integer getHomePlayerRankingPosition() {
		return this.homePlayerRankingPosition;
	}

	public void setHomePlayerRankingPosition(Integer homePlayerRankingPosition) {
		this.homePlayerRankingPosition = homePlayerRankingPosition;
	}

	public Integer getAwayPlayerRankingPosition() {
		return this.awayPlayerRankingPosition;
	}

	public void setAwayPlayerRankingPosition(Integer awayPlayerRankingPosition) {
		this.awayPlayerRankingPosition = awayPlayerRankingPosition;
	}

	public static List<String> getBuiltinFeatureNames() {

		List<String> featureNames = new ArrayList<String>();
		featureNames.add("homeWin");
		featureNames.add("name");

		return featureNames;
	}

	public double[] getDataIn() {

		List<String> featureNames = getFeatureNames();
		double[] dataIn = new double[featureNames.size()];

		for (int i = 0; i < featureNames.size(); i++) {
			dataIn[i] = getFeature(featureNames.get(i));
		}
		return dataIn;
	}
	
	/**
	 * @return returns false if one of the features is nan, otherwise returns true
	 */
	public Boolean validate(){
		
		List<String> featureNames = getFeatureNames();
		
		for(int i = 0; i < featureNames.size(); i++){
			
			Double feature = getFeature(featureNames.get(i));
			if(Double.isNaN(feature)){
				return false;
			}
			
		}
		return true;
	}

	private Double getFeature(String name) {

		String setterFuncName = String.format("get%s",
				StringUtils.capitalize(name));
		Method calculateFunc = null;
		Double feature = null;
		try {
			calculateFunc = this.getClass().getDeclaredMethod(setterFuncName);
			Object value = calculateFunc.invoke(this);
			feature = value!=null ? Double.valueOf(value.toString()) : Double.NaN;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return feature;
	}

	public Integer getHomePlayerPrefferedHand() {

		return this.homePlayerPrefferedHand;
	}

	public void setHomePlayerPrefferedHand(Integer homePlayerPrefferedHand) {

		this.homePlayerPrefferedHand = homePlayerPrefferedHand;

	}

	public Integer getAwayPlayerPrefferedHand() {

		return this.awayPlayerPrefferedHand;
	}

	public void setAwayPlayerPrefferedHand(Integer awayPlayerPrefferedHand) {

		this.awayPlayerPrefferedHand = awayPlayerPrefferedHand;
	}

	public Double getHomePlayerWinningPercentageBySurface() {
		return this.homePlayerWinningPercentageBySurface;
	}

	public void setHomePlayerWinningPercentageBySurface(
			Double homePlayerWinningPercentageBySurface) {
		this.homePlayerWinningPercentageBySurface = homePlayerWinningPercentageBySurface;
	}

	public Double getAwayPlayerWinningPercentageBySurface() {
		return this.awayPlayerWinningPercentageBySurface;
	}

	public void setAwayPlayerWinningPercentageBySurface(
			Double awayPlayerWinningPercentageBySurface) {
		this.awayPlayerWinningPercentageBySurface = awayPlayerWinningPercentageBySurface;
	}

	public Double getHomePlayerWinningPercentageBySurface12Month() {
		return this.homePlayerWinningPercentageBySurface12Month;
	}

	public void setHomePlayerWinningPercentageBySurface12Month(
			Double homePlayerWinningPercentageBySurface12Month) {
		this.homePlayerWinningPercentageBySurface12Month = homePlayerWinningPercentageBySurface12Month;
	}

	public Double getAwayPlayerWinningPercentageBySurface12Month() {
		return this.awayPlayerWinningPercentageBySurface12Month;
	}

	public void setAwayPlayerWinningPercentageBySurface12Month(
			Double awayPlayerWinningPercentageBySurface12Month) {
		this.awayPlayerWinningPercentageBySurface12Month = awayPlayerWinningPercentageBySurface12Month;
	}

	public Double getHomePlayerWinningPercentage1Month() {
		return this.homePlayerWinningPercentage1Month;
	}

	public void setHomePlayerWinningPercentage1Month(
			Double homePlayerWinningPercentage1Month) {
		this.homePlayerWinningPercentage1Month = homePlayerWinningPercentage1Month;
	}

	public Double getHomePlayerWinningPercentage12Month() {
		return this.homePlayerWinningPercentage12Month;
	}

	public void setHomePlayerWinningPercentage12Month(
			Double homePlayerWinningPercentage12Month) {
		this.homePlayerWinningPercentage12Month = homePlayerWinningPercentage12Month;
	}

	public Double getHomePlayerWinningPercentageBySurface1Month() {
		return this.homePlayerWinningPercentageBySurface1Month;
	}

	public void setHomePlayerWinningPercentageBySurface1Month(
			Double homePlayerWinningPercentageBySurface1Month) {
		this.homePlayerWinningPercentageBySurface1Month = homePlayerWinningPercentageBySurface1Month;
	}

	public Double getAwayPlayerWinningPercentage1Month() {
		return this.awayPlayerWinningPercentage1Month;
	}

	public void setAwayPlayerWinningPercentage1Month(
			Double awayPlayerWinningPercentage1Month) {
		this.awayPlayerWinningPercentage1Month = awayPlayerWinningPercentage1Month;
	}

	public Double getAwayPlayerWinningPercentage12Month() {
		return this.awayPlayerWinningPercentage12Month;
	}

	public void setAwayPlayerWinningPercentage12Month(
			Double awayPlayerWinningPercentage12Month) {
		this.awayPlayerWinningPercentage12Month = awayPlayerWinningPercentage12Month;
	}

	public Double getAwayPlayerWinningPercentageBySurface1Month() {
		return this.awayPlayerWinningPercentageBySurface1Month;
	}

	public void setAwayPlayerWinningPercentageBySurface1Month(
			Double awayPlayerWinningPercentageBySurface1Month) {
		this.awayPlayerWinningPercentageBySurface1Month = awayPlayerWinningPercentageBySurface1Month;
	}

	public Double getHomePlayerWinningPercentage() {
		return this.homePlayerWinningPercentage;
	}

	public void setHomePlayerWinningPercentage(Double homePlayerWinningPercentage) {
		this.homePlayerWinningPercentage = homePlayerWinningPercentage;
	}

	public Double getAwayPlayerWinningPercentage() {
		return this.awayPlayerWinningPercentage;
	}

	public void setAwayPlayerWinningPercentage(Double awayPlayerWinningPercentage) {
		this.awayPlayerWinningPercentage = awayPlayerWinningPercentage;
	}

	public Double getH2hWinningPercentageBySurface() {
		return this.h2hWinningPercentageBySurface;
	}

	public void setH2hWinningPercentageBySurface(
			Double h2hWinningPercentageBySurface) {
		this.h2hWinningPercentageBySurface = h2hWinningPercentageBySurface;
	}

	public Double getH2hWinningPercentageBySurface12Month() {
		return this.h2hWinningPercentageBySurface12Month;
	}

	public void setH2hWinningPercentageBySurface12Month(
			Double h2hWinningPercentageBySurface12Month) {
		this.h2hWinningPercentageBySurface12Month = h2hWinningPercentageBySurface12Month;
	}

	public Double getH2hWinningPercentage() {
		return this.h2hWinningPercentage;
	}

	public void setH2hWinningPercentage(
			Double h2hWinningPercentage) {
		this.h2hWinningPercentage = h2hWinningPercentage;
	}

	public Double getH2hWinningPercentage12Month() {
		return this.h2hWinningPercentage12Month;
	}

	public void setH2hWinningPercentage12Month(
			Double h2hWinningPercentage12Month) {
		this.h2hWinningPercentage12Month = h2hWinningPercentage12Month;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public Integer getHomePlayerHeight() {
		return homePlayerHeight;
	}

	public void setHomePlayerHeight(Integer homePlayerHeight) {
		this.homePlayerHeight = homePlayerHeight;
	}

	public Integer getHomePlayerWeight() {
		return homePlayerWeight;
	}

	public void setHomePlayerWeight(Integer homePlayerWeight) {
		this.homePlayerWeight = homePlayerWeight;
	}

	public Integer getHomePlayerYearsPro() {
		return homePlayerYearsPro;
	}

	public void setHomePlayerYearsPro(Integer homePlayerYearsPro) {
		this.homePlayerYearsPro = homePlayerYearsPro;
	}

	public Integer getAwayPlayerHeight() {
		return awayPlayerHeight;
	}

	public void setAwayPlayerHeight(Integer awayPlayerHeight) {
		this.awayPlayerHeight = awayPlayerHeight;
	}

	public Integer getAwayPlayerWeight() {
		return awayPlayerWeight;
	}

	public void setAwayPlayerWeight(Integer awayPlayerWeight) {
		this.awayPlayerWeight = awayPlayerWeight;
	}

	public Integer getAwayPlayerYearsPro() {
		return awayPlayerYearsPro;
	}

	public void setAwayPlayerYearsPro(Integer awayPlayerYearsPro) {
		this.awayPlayerYearsPro = awayPlayerYearsPro;
	}

	public Double getHomePlayerSetsWonPercentage() {
		return homePlayerSetsWonPercentage;
	}

	public void setHomePlayerSetsWonPercentage(Double homePlayerSetsWonPercentage) {
		this.homePlayerSetsWonPercentage = homePlayerSetsWonPercentage;
	}

	public Double getAwayPlayerSetsWonPercentage() {
		return awayPlayerSetsWonPercentage;
	}

	public void setAwayPlayerSetsWonPercentage(Double awayPlayerSetsWonPercentage) {
		this.awayPlayerSetsWonPercentage = awayPlayerSetsWonPercentage;
	}

	public Double getHomePlayerSetsWonPercentage1Month() {
		return homePlayerSetsWonPercentage1Month;
	}

	public void setHomePlayerSetsWonPercentage1Month(
			Double homePlayerSetsWonPercentage1Month) {
		this.homePlayerSetsWonPercentage1Month = homePlayerSetsWonPercentage1Month;
	}

	public Double getHomePlayerSetsWonPercentage12Month() {
		return homePlayerSetsWonPercentage12Month;
	}

	public void setHomePlayerSetsWonPercentage12Month(
			Double homePlayerSetsWonPercentage12Month) {
		this.homePlayerSetsWonPercentage12Month = homePlayerSetsWonPercentage12Month;
	}

	public Double getAwayPlayerSetsWonPercentage1Month() {
		return awayPlayerSetsWonPercentage1Month;
	}

	public void setAwayPlayerSetsWonPercentage1Month(
			Double awayPlayerSetsWonPercentage1Month) {
		this.awayPlayerSetsWonPercentage1Month = awayPlayerSetsWonPercentage1Month;
	}

	public Double getAwayPlayerSetsWonPercentage12Month() {
		return awayPlayerSetsWonPercentage12Month;
	}

	public void setAwayPlayerSetsWonPercentage12Month(
			Double awayPlayerSetsWonPercentage12Month) {
		this.awayPlayerSetsWonPercentage12Month = awayPlayerSetsWonPercentage12Month;
	}

	public Double getHomePlayerWinningPercentage6Month() {
		return homePlayerWinningPercentage6Month;
	}

	public void setHomePlayerWinningPercentage6Month(
			Double homePlayerWinningPercentage6Month) {
		this.homePlayerWinningPercentage6Month = homePlayerWinningPercentage6Month;
	}

	public Double getAwayPlayerWinningPercentage6Month() {
		return awayPlayerWinningPercentage6Month;
	}

	public void setAwayPlayerWinningPercentage6Month(
			Double awayPlayerWinningPercentage6Month) {
		this.awayPlayerWinningPercentage6Month = awayPlayerWinningPercentage6Month;
	}

	public Double getHomePlayerSetsWonPercentage6Month() {
		return homePlayerSetsWonPercentage6Month;
	}

	public void setHomePlayerSetsWonPercentage6Month(
			Double homePlayerSetsWonPercentage6Month) {
		this.homePlayerSetsWonPercentage6Month = homePlayerSetsWonPercentage6Month;
	}

	public Double getAwayPlayerSetsWonPercentage6Month() {
		return awayPlayerSetsWonPercentage6Month;
	}

	public void setAwayPlayerSetsWonPercentage6Month(
			Double awayPlayerSetsWonPercentage6Month) {
		this.awayPlayerSetsWonPercentage6Month = awayPlayerSetsWonPercentage6Month;
	}

	public Double getHomePlayerSetsWonPercentageBySurface() {
		return homePlayerSetsWonPercentageBySurface;
	}

	public void setHomePlayerSetsWonPercentageBySurface(
			Double homePlayerSetsWonPercentageBySurface) {
		this.homePlayerSetsWonPercentageBySurface = homePlayerSetsWonPercentageBySurface;
	}

	public Double getHomePlayerSetsWonPercentageBySurface12Month() {
		return homePlayerSetsWonPercentageBySurface12Month;
	}

	public void setHomePlayerSetsWonPercentageBySurface12Month(
			Double homePlayerSetsWonPercentageBySurface12Month) {
		this.homePlayerSetsWonPercentageBySurface12Month = homePlayerSetsWonPercentageBySurface12Month;
	}

	public Double getAwayPlayerSetsWonPercentageBySurface() {
		return awayPlayerSetsWonPercentageBySurface;
	}

	public void setAwayPlayerSetsWonPercentageBySurface(
			Double awayPlayerSetsWonPercentageBySurface) {
		this.awayPlayerSetsWonPercentageBySurface = awayPlayerSetsWonPercentageBySurface;
	}

	public Double getAwayPlayerSetsWonPercentageBySurface12Month() {
		return awayPlayerSetsWonPercentageBySurface12Month;
	}

	public void setAwayPlayerSetsWonPercentageBySurface12Month(
			Double awayPlayerSetsWonPercentageBySurface12Month) {
		this.awayPlayerSetsWonPercentageBySurface12Month = awayPlayerSetsWonPercentageBySurface12Month;
	}
}
