/**
 * 
 */
package hr.tennis.bot.fetcher;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Dajan
 *
 */
public class Test {

	private static final Log log = LogFactory.getLog(Test.class);

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws IOException, NumberFormatException, ParseException {

		log.info("testing log4j works!!!");

		//System.setProperty("http.proxySet", "true");
		//System.setProperty("http.proxyHost", "www-proxy.ericsson.se");
		//System.setProperty("http.proxyPort", "8080");

		PersistenceUtil.initialize();
		//		try {
		//			FetchResults.fetchATPSinglesResults("2011-09-24");
		//
		//		} catch (ParseException e) {
		//
		//			e.printStackTrace();
		//		}
		FetchPlayerRankings.fetchRankingsByDateFindingElegebleDate(Gender.MALE,DateUtil.createDate(2011,10,23));
		PersistenceUtil.close();

	}

}
