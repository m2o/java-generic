/**
 *
 */
package hr.tennis.bot.fetcher.service;

import hr.tennis.bot.fetcher.Constants;
import hr.tennis.bot.fetcher.FetchPlayerRankings;
import hr.tennis.bot.fetcher.data.TournamentFilter;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.model.entity.betting.BettingHouse;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.Match;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.entity.tournament.Round;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;
import hr.tennis.bot.model.factories.BettingHouseFactory;
import hr.tennis.bot.model.factories.MatchFactory;
import hr.tennis.bot.model.factories.OddFactory;
import hr.tennis.bot.model.factories.PlayerFactory;
import hr.tennis.bot.model.factories.RoundFactory;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.util.ArrayUtil;
import hr.tennis.bot.util.DateUtil;
import hr.tennis.bot.util.exception.TennisBotException;
import hr.tennis.bot.util.exception.TennisBotParsingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
public class ParsingResultService {

	private static final Log log = LogFactory.getLog(ParsingResultService.class);

	private static final Pattern MATCH_DETAILS_PATTERN = Pattern.compile("^\\s*([^,]+)\\s*(,\\s*([^,]+)\\s*)?,\\s*([^,]+)\\s*,\\s*([^,]+)\\s*,\\s*([^,]+)\\s*$");


	/**
	 * @param html
	 * @param currentDate
	 * @param type
	 * @param gender
	 * @throws ParseException
	 * @throws TennisBotException
	 * @throws IOException
	 */
	public static void parseResults(String html, Date currentDate, TournamentType type, Gender gender) throws ParseException,TennisBotException, IOException {

		Document document = Jsoup.parse(html);
		document.select(":containsOwn(\u00a0)").remove();
		Element element = document.select("table[class=result]").get(0);
		Elements tournaments = element.select("tr[class=head flags]");

		Date currentYear = DateUtil.floorToYear(currentDate);
		ParsingTournamentService.checkAndSaveTournaments(tournaments,type,gender,currentYear);

		// when all tournaments are saved.. get all matches.. parse them and
		// create rounds etc..

		for(Element tournamentElement : tournaments){

			String tournamentName = tournamentElement.select("td[class=t-name]").text().trim();

			if(!TournamentFilter.fetchTournament(tournamentName)){
				log.warn(String.format("ignoring matches in tournament %s",tournamentName));
				continue;
			}

			//			Elements matchDetailsLinks = tournamentElement.select("a[title=Click for match detail]");
			//			List<URL> matchLinks = new LinkedList<URL>();
			//
			//			for (Element el : matchDetailsLinks) {
			//				matchLinks.add(new URL(Constants.TENNIS_EXPLORER_LINK+el.attr("href")));
			//			}

			Element currentMatchElement = tournamentElement.nextElementSibling();

			while(currentMatchElement!=null && !currentMatchElement.attr("class").equals("head flags")){
				String matchUrl = currentMatchElement.children().last().select("a").attr("href");
				saveMatch(new URL(Constants.TENNIS_EXPLORER_LINK+matchUrl),currentDate,currentYear,type,gender);
				currentMatchElement = currentMatchElement.nextElementSibling().nextElementSibling();
			}
		}
	}

	/**
	 * Loops through all match links and saves matches
	 *
	 * @param matchLinks
	 * @param date
	 * @param tournamentType
	 * @param gender
	 * @throws ParseException
	 * @throws IOException
	 */
	private static void saveMatch(URL url,Date currentDate,Date currentYear,TournamentType tournamentType, Gender gender) throws ParseException, IOException{

		String matchDetailHtml = WebFetcherService.fetchHTML(url);
		String[] matchParameters = parseMatch(matchDetailHtml);

		if(matchParameters==null){
			return;
		}

		//TODO - to 1 query
		ParamMap params = new ParamMap("name", matchParameters[2]).addParameter("year", currentYear);
		Tournament tour = (Tournament) DAO.getSingleResult("Tournament.findByNameAnyYear", params);

		params = new ParamMap("tournament",tour).addParameter("gender", gender).addParameter("type", tournamentType);
		TournamentInstance tourInstance = (TournamentInstance) DAO.getSingleResult("TournamentInstance.findByTournamentGenderType",params);

		params = new ParamMap("tournamentInstance",tourInstance).addParameter("name",matchParameters[3]);
		Round round = (Round) DAO.getSingleResult("Round.findByNameTournamentInstance", params);

		if (round == null) {
			log.debug(String.format("missing Round(%s,%s)",tourInstance,matchParameters[3]));
			round = RoundFactory.createRound(matchParameters[3],tourInstance);
			DAO.save(round);
		}else{
			log.debug(String.format("found %s",round));
		}

		//players

		// check if player1 exists.. if not create player and parse data..
		Player player1 = (Player) DAO.getSingleResult("Player.findByName", new ParamMap("name",matchParameters[15]));

		if (player1 == null) {
			log.warn(String.format("missing Player(%s)",matchParameters[15]));
			String[] playerParameters = null;
			try {
				playerParameters = ParsingPlayerService.parsePlayerData(matchParameters[16]);
				player1 = PlayerFactory.createPlayer(matchParameters[15],playerParameters,gender);
			} catch (FileNotFoundException e) {
				log.error("player page doesn't exist!", e);
				player1 = PlayerFactory.createPlayer(matchParameters[15]);
			}
			DAO.save(player1);
		}else{
			log.debug(String.format("found %s",player1));
		}

		// check if player1 exists.. if not create player and pars  data..
		Player player2 = (Player) DAO.getSingleResult("Player.findByName",new ParamMap("name",matchParameters[17]));

		if (player2 == null) {
			log.warn(String.format("missing Player(%s)",matchParameters[17]));
			String[] playerParameters;
			try {
				playerParameters = ParsingPlayerService.parsePlayerData(matchParameters[18]);
				player2 = PlayerFactory.createPlayer(matchParameters[17],playerParameters,gender);
			} catch (FileNotFoundException e) {
				log.error("player page doesn't exist!", e);
				player2 = PlayerFactory.createPlayer(matchParameters[17]);
			}
			DAO.save(player2);
		}else{
			log.debug(String.format("found %s",player2));
		}

		//ranking
		ParamMap rankingPlayer1Params = new ParamMap("player",player1).addParameter("date",currentDate);
		Ranking rankingPlayer1 = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",rankingPlayer1Params);

		ParamMap rankingPlayer2Params = new ParamMap("player",player2).addParameter("date",currentDate);
		Ranking rankingPlayer2 = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",rankingPlayer2Params);

		if (rankingPlayer1 == null && rankingPlayer2 == null) {

			log.warn(String.format("missing Ranking for date %s",DateUtil.formatDateSimple(currentDate)));
			try{
				FetchPlayerRankings.fetchRankingsByDateFindingElegebleDate(gender,currentDate);
			}catch (IllegalArgumentException e) {
				log.warn(e);
				//ignorable
			}
			rankingPlayer1 = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",rankingPlayer1Params);
			rankingPlayer2 = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",rankingPlayer2Params);
		}

		if(rankingPlayer1==null){
			log.warn(String.format("missing Ranking(%s,%s) - will use null",player1,DateUtil.formatDateSimple(currentDate)));
		}else{
			log.debug(String.format("found %s",rankingPlayer1));
		}

		if(rankingPlayer2==null){
			log.warn(String.format("missing Ranking(%s,%s) - will use null",player2,DateUtil.formatDateSimple(currentDate)));
		}else{
			log.debug(String.format("found %s",rankingPlayer2));
		}

		Match match = MatchFactory.createMatchWithResult(matchParameters, currentDate,tourInstance, round, player1, player2,rankingPlayer1, rankingPlayer2);
		DAO.save(match);

		for (Odd odd : getAllOdds(matchDetailHtml, match)) {
			DAO.save(odd);
		}
	}


	/**
	 * Getting all odds for given match.
	 *
	 * @param html
	 * @param match
	 * @return
	 * @throws NotValidTournamentException
	 */
	private static List<Odd> getAllOdds(String html, Match match) {

		Document document = Jsoup.parse(html);
		Element oddElement = document.select("tbody").get(5);
		List<Odd> odds = new ArrayList<Odd>();
		String[] oddsParameters = new String[4];

		Element element = document.select("div[id=center]").get(0);
		Element round = element.select("div[class=box boxBasic lGray]").get(0);

		String matchDetails = round.text();

		// handle average odds

		Element averageOdd = null;

		try {
			averageOdd = oddElement.select("tr[class=average]").get(0);
		} catch (IndexOutOfBoundsException e) {
			log.warn(String.format("%s - no odds defined!",match));
			return Collections.emptyList();
		}
		oddsParameters[0] = averageOdd.select("td[class=k1]").get(0).text().trim();
		oddsParameters[1] = averageOdd.select("td[class=k2]").get(0).text().trim();

		BettingHouse avgBettingHouse = (BettingHouse) DAO.getSingleResult("BettingHouse.findBettingHouseByName", new ParamMap("name",hr.tennis.bot.model.Constants.BETTING_HOUSE_AVERAGE));
		if(avgBettingHouse == null) {
			avgBettingHouse = BettingHouseFactory.createBettingHouse(hr.tennis.bot.model.Constants.BETTING_HOUSE_AVERAGE,null);
		}

		Odd odd = OddFactory.createOdd(oddsParameters[0],oddsParameters[1], avgBettingHouse, match);
		odds.add(odd);

		Elements oneOdds = oddElement.select("tr[class=one]");
		Elements twoOdds = oddElement.select("tr[class=two]");

		oneOdds.addAll(twoOdds);

		for (Element el : oneOdds) {

			oddsParameters = new String[4];
			Odd otherOdd = null;
			BettingHouse bh = null;

			Elements tds = el.select("td");

			oddsParameters[0] = tds.get(1).text().trim();
			oddsParameters[1] = tds.get(2).text().trim();

			//			try {
			//
			//				oddsParameters[0] = el.select("td").get(0).text().trim();
			//				oddsParameters[1] = el.select("td[class=k2]").get(0).text().trim();
			//
			//			} catch (IndexOutOfBoundsException e) {
			//				log.info(e.getMessage());
			//
			//				try {
			//					oddsParameters[0] = el.select("td[class=k1 deactivated]").get(0).text().trim();
			//					oddsParameters[1] = el.select("td[class=k2 deactivated]").get(0).text().trim();
			//				} catch (IndexOutOfBoundsException e1) {
			//					log.info(e1.getMessage());
			//					throw e;
			//					//continue;
			//				}
			//				throw e;
			//			}

			try {
				oddsParameters[2] = el.select("span[class=t]").get(0).text().trim();
				oddsParameters[3] = el.select("a").attr("href");
			} catch (IndexOutOfBoundsException e) {
				log.info(e.getMessage());
				//continue;
				throw e;
			}

			bh = (BettingHouse) DAO.getSingleResult("BettingHouse.findBettingHouseByName", new ParamMap("name",oddsParameters[2]));
			if (bh == null) {
				bh = BettingHouseFactory.createBettingHouse(oddsParameters[2], oddsParameters[3]);
			}

			otherOdd = OddFactory.createOdd(oddsParameters[0],oddsParameters[1], bh, match);
			odds.add(otherOdd);
		}

		return odds;
	}

	/**
	 * @param html
	 * @return
	 * @throws TennisBotParsingException
	 */
	private static String[] parseMatch(String html) {

		String[] resultParams = new String[4];
		Document document = Jsoup.parse(html);
		String player1 = null;
		String player1Link = null;
		String player2 = null;
		String player2Link = null;
		String[] matchParams = null;
		String score = null;

		Element element = document.select("div[id=center]").get(0);
		Elements roundResultElements = element.select("div[class=box boxBasic lGray]");
		Element round = roundResultElements.get(0);
		Element result = roundResultElements.get(1);

		String matchDetails = round.text();

		Matcher matchDetailsMatcher = MATCH_DETAILS_PATTERN.matcher(matchDetails);
		matchDetailsMatcher.matches();

		String dateStr = matchDetailsMatcher.group(1);
		String timeStr = matchDetailsMatcher.group(3); //ignored
		String tournamentName = matchDetailsMatcher.group(4);
		String roundName = matchDetailsMatcher.group(5);
		String surfaceStr = matchDetailsMatcher.group(6);

		if(!TournamentFilter.fetchTournament(tournamentName)){
			log.warn(String.format("ignoring match in tournament %s",tournamentName));
			return null;
		}

		matchParams = new String[]{dateStr,timeStr,tournamentName,roundName,surfaceStr};

		player1 = result.select("th[class=plName]").get(0).text();
		player1Link = result.select("th[class=plName]").get(0).select("a").attr("href");
		player2 = result.select("th[class=plName]").get(1).text();
		player2Link = result.select("th[class=plName]").get(1).select("a").attr("href");
		score = result.select("td[class=gScore]").get(0).text();

		resultParams[0] = player1;
		resultParams[1] = player1Link;
		resultParams[2] = player2;
		resultParams[3] = player2Link;
		// resultParams[2] = score;

		String[] normalizedScore = normalizeMatchScore(score);

		//			Element oddElement = document.select("tbody").get(5);
		//			String[] oddsParameters = new String[2];
		//
		//			Element averageOdd = oddElement.select("tr[class=average]").get(0);
		//			oddsParameters[0] = averageOdd.select("td[class=k1]").get(0).text().trim();
		//			oddsParameters[1] = averageOdd.select("td[class=k2]").get(0).text().trim();

		String[] tmpParams = ArrayUtil.concatArrays(matchParams,normalizedScore);
		String[] tmpParams2 = ArrayUtil.concatArrays(tmpParams,resultParams);
		//			String[] allParams = ArrayUtil.concatArrays(tmpParams2,oddsParameters);

		return tmpParams2;
	}

	public static String[] normalizeMatchScore(String score) {

		String[] normalizedScore = new String[10];

		if(score.equals(":")){
			return normalizedScore;
		}

		String[] tmp = score.split(" ");
		//winner sets won
		normalizedScore[0] = tmp[0].trim();
		//loser sets won
		normalizedScore[1] = tmp[2].trim();

		//loop through all sets
		for(int i = 3; i < tmp.length; i++){

			normalizedScore[i-1] = tmp[i].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", "").trim();

		}

		return normalizedScore;
	}


	public static void main(String[] args) throws IOException, ParseException {

		//		//String value = "01.01.2007, 10:00, Doha, 1. round, hard";
		//		//String value = "01.01.2007, Doha, 1. round, hard";
		//		String value = "01.01.2007, 23:55, Hopman Cup, -, hard";
		//
		//
		//		Pattern matchDetailsPattern = Pattern.compile("^\\s*([^,]+)\\s*(,\\s*([^,]+)\\s*)?,\\s*([^,]+)\\s*,\\s*([^,]+)\\s*,\\s*([^,]+)\\s*$");
		//
		//		Matcher matchDetailsMatcher = matchDetailsPattern.matcher(value);
		//		if(matchDetailsMatcher.matches()){
		//			System.out.println(matchDetailsMatcher.group(1));
		//			System.out.println(matchDetailsMatcher.group(3));
		//			System.out.println(matchDetailsMatcher.group(4));
		//			System.out.println(matchDetailsMatcher.group(5));
		//			System.out.println(matchDetailsMatcher.group(6));
		//			//			String name = matchDetailsMatcher.group(1);
		//			//			String yearStr = matchDetailsMatcher.group(2);
		//			//			String country = matchDetailsMatcher.group(3);
		//			//
		//			//			System.out.println(name);
		//			//			System.out.println(yearStr);
		//			//			System.out.println(country);
		//		}else{
		//			System.out.println("no match");
		//		}


		//String matchDetailHtml = WebFetcherService.fetchHTML("http://www.tennisexplorer.com/match-detail/?id=869788");
		String matchDetailHtml = WebFetcherService.fetchHTML("http://www.tennisexplorer.com/match-detail/?id=900378");
		String[] matchParameters = parseMatch(matchDetailHtml);
		Match match = MatchFactory.createMatchWithResult(matchParameters,null,null, null, null, null,null, null);

	}

}
