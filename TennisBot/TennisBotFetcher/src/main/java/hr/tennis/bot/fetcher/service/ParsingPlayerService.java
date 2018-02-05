/**
 * 
 */
package hr.tennis.bot.fetcher.service;

import hr.tennis.bot.fetcher.Constants;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

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
public class ParsingPlayerService {

	private static final Log log = LogFactory
	.getLog(ParsingPlayerService.class);

	/**
	 * @param link
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String[] parsePlayerData(String link) throws ParseException, IOException {

		URL url = null;
		try {
			url = new URL(Constants.TENNIS_EXPLORER_LINK + link);
		} catch (MalformedURLException e) {
			// ignorable
			e.printStackTrace();
		}

		String html = WebFetcherService.fetchHTML(url);

		Document document = Jsoup.parse(html);
		String[] playerParameters = new String[6];

		Element htmlElements = document.select("div[id=center]").get(0);
		Elements playerData = htmlElements.select("div[class=date]");

		for (Element el : playerData) {
			if (el.text().contains("Height / Weight:")) {
				String heightWeight = el.text();
				heightWeight = heightWeight.replaceAll("Height / Weight: ", "");
				heightWeight = heightWeight.replaceAll("cm", "");
				heightWeight = heightWeight.replaceAll("kg", "");
				String heightWeightSep[] = heightWeight.split("/");
				playerParameters[0] = heightWeightSep[0].trim();
				playerParameters[1] = heightWeightSep[1].trim();
			}

			if (el.text().contains("Born:")) {
				String dateBorn = el.text();
				dateBorn = dateBorn.replaceAll("Born: ", "").trim();

				playerParameters[2] = dateBorn;
			}

			if (el.text().contains("Turned pro:")) {
				String turnedPro = el.text();
				turnedPro = turnedPro.replaceAll("Turned pro: ", "").trim();

				playerParameters[3] = turnedPro;
			}

			if (el.text().contains("Sex:")) {
				String gender = el.text();
				gender = gender.replaceAll("Sex: ", "").trim();
				playerParameters[4] = gender;
			}

			if (el.text().contains("Plays:")) {
				String playsHand = el.text();
				playsHand = playsHand.replaceAll("Plays: ", "").trim();
				playerParameters[5] = playsHand;
			}
		}

		return playerParameters;
	}

}
