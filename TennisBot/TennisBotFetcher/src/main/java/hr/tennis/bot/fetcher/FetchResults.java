/**
 * 
 */
package hr.tennis.bot.fetcher;

import hr.tennis.bot.fetcher.data.DataSource;
import hr.tennis.bot.fetcher.service.ParsingResultService;
import hr.tennis.bot.fetcher.service.WebFetcherService;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.util.exception.TennisBotException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author edajzve
 * 
 */
public class FetchResults {

	private static final Log log = LogFactory.getLog(FetchResults.class);

	public static void fetchSinglesResults(Gender gender, Date date) throws ParseException, IOException, TennisBotException {
		URL url = new URL(DataSource.constructResultsSource(gender, date));
		String html = WebFetcherService.fetchHTML(url);
		ParsingResultService.parseResults(html,date,TournamentType.SINGLE,gender);
	}
}
