/**
 * 
 */
package hr.tennis.bot.fetcher.service;

import hr.tennis.bot.fetcher.Constants;
import hr.tennis.bot.fetcher.data.TournamentFilter;
import hr.tennis.bot.fetcher.exception.MissingTournamentPageException;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;
import hr.tennis.bot.model.factories.TournamentFactory;
import hr.tennis.bot.model.factories.TournamentInstanceFactory;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.util.DateUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ParsingTournamentService {

	private static final Log log = LogFactory.getLog(ParsingTournamentService.class);

	private static final Pattern NAME_YEAR_COUNTRY_PATTERN = Pattern.compile("^\\s*(.*)\\s+(\\d{4})\\s+\\(\\s*([\\w\\s\\(\\)\\.]+)\\s*\\)$");
	private static final Pattern PRIZE_SURFACE_GENDER_PATTERN = Pattern.compile("^\\(\\s*(.+)\\s*,\\s*([^,]+)\\s*,\\s*(men|women)\\s*\\)$");

	/**
	 * Check if given tournaments exists, if not parse and save new tournament
	 * 
	 * @param tournamentsJSoupElements
	 * @param type
	 * @param gender
	 * @param year
	 * @throws IOException
	 */
	public static void checkAndSaveTournaments(Elements tournamentsJSoupElements, TournamentType type, Gender gender, Date year) throws IOException{

		Set<String> tournamentNameFetched = new HashSet<String>();

		for (Element tournamentJSoupElement : tournamentsJSoupElements) {

			String tournamentName = tournamentJSoupElement.select("td[class=t-name]").text().trim();
			if(tournamentNameFetched.contains(tournamentName)){
				log.warn(String.format("ignoring tournament %s, already fetched in this check",tournamentName));
				continue;
			}
			tournamentNameFetched.add(tournamentName);

			ParamMap params = new ParamMap("name",tournamentName).addParameter("year",year);

			// check if tournament exists.. if not create tournament and parse data..
			Tournament tournament = (Tournament) DAO.getSingleResult("Tournament.findByNameAnyYear", params);
			TournamentInstance tournamentInstance = null;
			String[] tournamentParameters = null;

			if (tournament == null) {
				log.warn(String.format("missing Tournament(%s,%s)",tournamentName,DateUtil.formatDate(year,"yyyy")));

				if(!TournamentFilter.fetchTournament(tournamentName)){
					log.warn(String.format("ignoring tournament %s",tournamentName));
					continue;
				}

				String link = tournamentJSoupElement.select("a").attr("href");

				if (!link.equals("")) {
					URL url = null;
					try {
						url = new URL(Constants.TENNIS_EXPLORER_LINK + link);
					} catch (MalformedURLException e) {
						//ignorable
						e.printStackTrace();
					}

					try {
						tournamentParameters = parseTournament(url);
						tournament = TournamentFactory.createTournament(tournamentParameters, tournamentName,year);
					} catch (MissingTournamentPageException e) {
						log.warn("missing tournament page "+url.toString());
						tournament = TournamentFactory.createTournament(tournamentName, year);
					}
					
					tournamentInstance = TournamentInstanceFactory.createTournamentInstance(tournament,gender, type);

					DAO.save(tournament);
					DAO.save(tournamentInstance);
				} else {
					break;
				}
			} else {
				log.debug(String.format("found %s",tournament));

				tournamentInstance = (TournamentInstance) DAO.getSingleResult("TournamentInstance.findByTournamentGenderType",
						new ParamMap("tournament",tournament).addParameter("gender",gender).addParameter("type",type));

				if (tournamentInstance != null) {
					log.debug(String.format("found %s ",tournamentInstance));
				}else{
					log.warn(String.format("missing TournamentInstance(%s,%s,%s)",tournament,type,gender));
					tournamentInstance = TournamentInstanceFactory.createTournamentInstance(tournament, gender,type);
					DAO.save(tournamentInstance);
				}
			}
		}
	}

	/**
	 * @param link
	 * @return
	 * @throws IOException
	 * @throws MissingTournamentPageException 
	 */
	private static String[] parseTournament(URL url) throws IOException, MissingTournamentPageException {

		String html = WebFetcherService.fetchHTML(url);

		Document document = Jsoup.parse(html);
		Element element = document.select("div[id=center]").get(0);
		
		if(element.getElementsContainingText("Tournament does not exist.").size()>0){
			throw new MissingTournamentPageException(url.toString());
		}

		String nameYearCountryStr = element.select("h1[class=bg]").get(0).text();
		String prizeSurfaceGenderStr = element.select("div[class=box boxBasic lGray]").get(0).text();

		Matcher nameYearCountryMatcher = NAME_YEAR_COUNTRY_PATTERN.matcher(nameYearCountryStr);
		Matcher prizeSurfaceGenderMatcher = PRIZE_SURFACE_GENDER_PATTERN.matcher(prizeSurfaceGenderStr);

		nameYearCountryMatcher.matches();
		prizeSurfaceGenderMatcher.matches();

		//String name = nameYearCountryMatcher.group(1);
		//String yearStr = nameYearCountryMatcher.group(2);
		String country = nameYearCountryMatcher.group(3);

		String prize = prizeSurfaceGenderMatcher.group(1);
		String surface = prizeSurfaceGenderMatcher.group(2);
		//String gender = prizeSurfaceGenderMatcher.group(3);

		return new String[]{country,prize,surface};
	}

	public static void main(String[] args) {

		//String nameYearCountryStr = "Bla 2007 (Italy)";
		//String nameYearCountryStr = "Doha Moha 2007 (New Caledonia)";
		String nameYearCountryStr = "Kooyong - exh. 2007 (Australia)";
		//String nameYearCountryStr = "Sarajevo challenger 2007 (Bosnia and Herzeg.)";
		//String nameYearCountryStr = "Taoyuan ITF 2007 (Taipei (CHN))";
		
		String prizeSurfaceGenderStr = "($1,000,000, hard, men)";

		Matcher nameYearCountryMatcher = NAME_YEAR_COUNTRY_PATTERN.matcher(nameYearCountryStr);
		
		if(nameYearCountryMatcher.matches()){
			String name = nameYearCountryMatcher.group(1);
			String yearStr = nameYearCountryMatcher.group(2);
			String country = nameYearCountryMatcher.group(3);

			System.out.println(name);
			System.out.println(yearStr);
			System.out.println(country);
		}else{
			System.out.println("no match");
		}

		Matcher prizeSurfaceGenderMatcher = PRIZE_SURFACE_GENDER_PATTERN.matcher(prizeSurfaceGenderStr);
		if(prizeSurfaceGenderMatcher.matches()){
			String prize = prizeSurfaceGenderMatcher.group(1);
			String surface = prizeSurfaceGenderMatcher.group(2);
			String gender = prizeSurfaceGenderMatcher.group(3);

			System.out.println(prize);
			System.out.println(surface);
			System.out.println(gender);
		}else{
			System.out.println("no match");
		}

	}

}
