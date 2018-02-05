package hr.tennis.bot.fetcher.data;

import hr.tennis.bot.fetcher.Constants;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.util.DateUtil;

import java.util.Date;

public class DataSource {

	public static String constructRankingSource(Gender gender,Date date,String page){
		String genderParam = Gender.MALE.equals(gender) ? "atp-men" : "wta-women";
		String query = String.format("'/ranking/%s/'yyyy'/?submit=Search&date='yyyy-MM-dd'&page=%s'",genderParam,page);
		return Constants.TENNIS_EXPLORER_LINK +DateUtil.formatDate(date,query);
	}

	public static String constructResultsSource(Gender gender,Date date){
		String genderParam = Gender.MALE.equals(gender) ? "atp-single" : "wta-single";
		String query = String.format("'/results/?type=%s&year='yyyy'&month='MM'&day='dd",genderParam);
		return Constants.TENNIS_EXPLORER_LINK +DateUtil.formatDate(date,query);
	}

}
