package hr.tennis.bot.fetcher;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;
import hr.tennis.bot.util.exception.TennisBotException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PeriodicFetcher {

	private static final Log log = LogFactory.getLog(PeriodicFetcher.class);

	private static final Date BEGGINING_DATE = DateUtil.createDate(2007, 1, 1);
	private static final Integer FETCH_DAYS = 30;

	private static Date fetchSingles(Gender gender,Date endDate,Integer maxDays) throws IOException, ParseException,TennisBotException {

		EntityTransaction transaction = PersistenceUtil.getEntityManager().getTransaction();

		Date earliestMatchDate = (Date) DAO.getSingleResult("SinglesMatch.findEarliestMatchDateByGender",new ParamMap("gender",gender));
		Date latestMatchDate = (Date) DAO.getSingleResult("SinglesMatch.findLatestMatchDateByGender",new ParamMap("gender",gender));

		log.debug(String.format("earliest match date:%s",DateUtil.formatDateSimple(earliestMatchDate)));
		log.debug(String.format("latest match date:%s",DateUtil.formatDateSimple(latestMatchDate)));

		Date startDate = latestMatchDate != null ? DateUtil.add(latestMatchDate,Calendar.DATE,1) : BEGGINING_DATE;
		if(endDate==null){
			endDate = DateUtil.add(DateUtil.today(),Calendar.DATE,-2);
		}

		log.debug(String.format("fetching interval start date:%s",DateUtil.formatDateSimple(startDate)));
		log.debug(String.format("fetching interval end date:%s",DateUtil.formatDateSimple(endDate)));

		Date currentDate = startDate;

		if(currentDate.equals(BEGGINING_DATE)){
			transaction.begin();
			try {
				FetchPlayerRankings.fetchRankingsByDateFindingElegebleDate(gender,currentDate);
				transaction.commit();
			} catch (IllegalArgumentException e) {
				log.warn(String.format("%s rankings for date %s already exist",gender,currentDate));
				transaction.rollback();
			}
		}

		int i=0;

		while(!currentDate.after(endDate) && i++<maxDays){
			log.info(String.format("fetching date %s for %s",DateUtil.formatDateSimple(currentDate),gender));

			transaction.begin();
			try {
				FetchPlayerRankings.fetchRankingsByDate(gender,currentDate);
				transaction.commit();
			} catch (IllegalArgumentException e) {
				//log.warn(String.format("rankings for date %s already exist",currentDate));
				transaction.rollback();
			}
			transaction.begin();
			FetchResults.fetchSinglesResults(gender,currentDate);
			transaction.commit();

			currentDate = DateUtil.add(currentDate,Calendar.DATE,1);
		}

		return DateUtil.add(currentDate,Calendar.DATE,-1);
	}

	private static void fetchAll(Date endDate) throws IOException, ParseException,TennisBotException {

		log.info(String.format("running %s",PeriodicFetcher.class.getName()));

		if(endDate==null){
			endDate = DateUtil.add(DateUtil.today(),Calendar.DATE,-2);
		}
		Date maleCurrentDate = null;
		Date femaleCurrentDate = null;

		while(maleCurrentDate==null ||
				endDate.after(maleCurrentDate) ||
				femaleCurrentDate==null ||
				endDate.after(femaleCurrentDate)){

			if(maleCurrentDate==null || endDate.after(maleCurrentDate)){
				maleCurrentDate = fetchSingles(Gender.MALE,endDate,FETCH_DAYS);
			}
			if(femaleCurrentDate==null || endDate.after(femaleCurrentDate)){
				femaleCurrentDate = fetchSingles(Gender.FEMALE,endDate,FETCH_DAYS);
			}
//			endDate = DateUtil.add(DateUtil.today(),Calendar.DATE,-2);
		}
	}

	public static void main(String[] args) throws Exception {

			PersistenceUtil.initialize();
			PersistenceUtil.getEntityManager();

			Calendar cal = new GregorianCalendar(2011,GregorianCalendar.DECEMBER,31);
			Date endDate = cal.getTime();

			try{
				fetchAll(null);
			}catch (Exception e) {
				log.error("fetchAll failed",e);
				throw e;
			}finally{
				PersistenceUtil.getEntityManager().close();
				PersistenceUtil.close();
			}
	}
}
