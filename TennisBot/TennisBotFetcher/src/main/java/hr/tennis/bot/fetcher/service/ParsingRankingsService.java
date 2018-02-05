/**
 * 
 */
package hr.tennis.bot.fetcher.service;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.factories.PlayerFactory;
import hr.tennis.bot.model.factories.RankingFactory;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author edajzve
 *
 */
public class ParsingRankingsService {

	private static final Log log = LogFactory.getLog(ParsingRankingsService.class);

	/**
	 * @param html
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<Ranking> parseRankings(String html, Date date,Gender gender) throws NumberFormatException, ParseException, IOException {

		Document document = Jsoup.parse(html);
		Element element = null;
		Elements elementsNames = null;
		Elements elementsPoints = null;
		Elements elementsRankPosition = null;

		List<Ranking> rankingsList = new ArrayList<Ranking>();

		// String dates[] = date.split("-");

		element = document.select("table[class=result]").get(1);// table
		// class="result"
		elementsNames = element.select("th[class=t-name]");
		elementsPoints = element.select("td[class=long-point]");// <td
		// class="long-point"
		elementsRankPosition = element.select("td[class=rank first]");
		// System.out.println(element.toString());

		for (int i = 0; i < elementsNames.size(); i++) {

			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("name", elementsNames.get(i).text());

			// check if player exists.. if not create player and parse data..
			Player player = (Player) DAO.getSingleResult("Player.findByName",params);

			if (player == null) {
				String[] playerParameters = ParsingPlayerService.parsePlayerData(elementsNames.get(i).select("a").attr("href"));
				player = PlayerFactory.createPlayer(elementsNames.get(i).text(), playerParameters,gender);
			}

			Ranking ranking = RankingFactory.createRanking(
					player,
					Integer.parseInt(elementsRankPosition.get(i).text().replace(".", "")),
					Integer.parseInt(elementsPoints.get(i + 1).text()),
					date);
			rankingsList.add(ranking);
		}

		return rankingsList;

	}

	/**
	 * @param html
	 * @return
	 * @throws ParseException
	 */
	public static List<String> parseYearRankingDates(String html)
	throws ParseException {

		List<String> yearDates = new ArrayList<String>();
		Document document = Jsoup.parse(html);
		Element htmlElements = document.select("select[name=date]").get(0);
		Elements dateElements = htmlElements.select("option");

		for (Element el : dateElements) {
			String date = el.text();
			yearDates.add(DateUtil.convertDate(date, "dd. MM. yyyy",
			"yyyy-MM-dd"));

		}

		return yearDates;

	}
}
