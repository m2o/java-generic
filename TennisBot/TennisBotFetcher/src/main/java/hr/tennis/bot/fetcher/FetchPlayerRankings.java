/**
 * 
 */
package hr.tennis.bot.fetcher;

import hr.tennis.bot.fetcher.data.DataSource;
import hr.tennis.bot.fetcher.service.ParsingRankingsService;
import hr.tennis.bot.fetcher.service.WebFetcherService;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Dajan
 * 
 */
public class FetchPlayerRankings {

	public static final Log log = LogFactory.getLog(FetchPlayerRankings.class);

	/**
	 * Get rankigs date for given date.
	 * @param gender
	 * 
	 * @param currentDate
	 *            in format yyyy-MM-dd
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static int fetchRankingsByDate(Gender gender, Date currentDate) throws IOException, NumberFormatException, ParseException {

		log.info("fetching ranking for date "+DateUtil.formatDateSimple(currentDate));

		Long cnt = (Long)DAO.getSingleResult("Ranking.rankingsCountOnDate",new ParamMap("date",currentDate).addParameter("gender",gender));
		if(cnt>0){
			throw new IllegalArgumentException("rankings exist for date: "+currentDate);
		}

		List<Ranking> newRankings;
		Set<Ranking> rankingSet = new TreeSet<Ranking>(new Comparator<Ranking>() { //to ignore duplicate values in ranking
			@Override
			public int compare(Ranking r1, Ranking r2) {
				return r1.getPosition().compareTo(r2.getPosition());
			}
		});

		String urlTemplate = DataSource.constructRankingSource(gender, currentDate, "%d");

		for(int i = 1; true; i++) {
			String htmlSrc = WebFetcherService.fetchHTML(String.format(urlTemplate,i));
			newRankings = ParsingRankingsService.parseRankings(htmlSrc,currentDate, gender);
			if(newRankings.size() == 0){
				log.warn("no rankings for date "+currentDate);
				break;
			}
			rankingSet.addAll(newRankings);
		}

		for(Ranking ranking : rankingSet){
			DAO.save(ranking);
		}
		return rankingSet.size();
	}

	/**
	 * Get rankigs date for given date. If nothing available for the given date
	 * find the next date with rankings.
	 * 
	 * @param currentDate
	 *            in format yyyy-MM-dd
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static void fetchRankingsByDateFindingElegebleDate(Gender gender,Date currentDate) throws IOException, NumberFormatException, ParseException {
		log.info(String.format("fetching %s rankings for elegeble date %s",gender,DateUtil.formatDateSimple(currentDate)));
		while(fetchRankingsByDate(gender,currentDate)==0) {
			currentDate = DateUtil.add(currentDate,Calendar.DATE,-1);
		}
	}

	public static void main(String[] args) throws ParseException, IOException {
		PersistenceUtil.initialize();
		EntityTransaction transaction = PersistenceUtil.getEntityManager().getTransaction();
		transaction.begin();

		fetchRankingsByDateFindingElegebleDate(Gender.MALE,DateUtil.createDate(2007,1,1));

		transaction.commit();
		PersistenceUtil.close();
	}

}
