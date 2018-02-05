/**
 * 
 */
package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.model.entity.match.DoublesMatch;
import hr.tennis.bot.model.entity.match.Match;
import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.entity.match.result.Result;
import hr.tennis.bot.model.entity.match.result.SetResult;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.entity.tournament.Round;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author edajzve
 *
 */
public class MatchFactory {

	public static final Log log = LogFactory.getLog(MatchFactory.class);

	public static Match createMatchWithResult(String[] matchParams, Date date,TournamentInstance tourInstance, Round round, Player player1, Player player2, Ranking rankingPlayer1, Ranking rankingPlayer2) throws ParseException{

		Match match = null;

		if(tourInstance.getType() == TournamentType.SINGLE){
		//if(true){

			match = new SinglesMatch(player1, player2, rankingPlayer1, rankingPlayer2);
			match.setDate(date);
			match.setRound(round);

			if(matchParams[5]!=null){
				int setsHome = Integer.parseInt(matchParams[5]);
				int setsAway = Integer.parseInt(matchParams[6]);
				int totalSets = setsHome + setsAway;
	
				Result result = new Result();
				result.setHomeWin(true);
				result.setForfeited(setsHome==1 && setsAway==0);
	
				int currentSet = 1;
				List<SetResult> setResults = new ArrayList<SetResult>();
				for(int i = 7; i < matchParams.length-2; i++){
					//prolazim po setovima.. formatirati ulaz seta.. 6-2 primjer..
					SetResult setResult = new SetResult();
					setResult.setNumber(currentSet++);
	
					if(matchParams[i] != null && i <= 11){
						String[] setPoints = matchParams[i].split("-");
	
						int diff = Math.abs(Integer.parseInt(setPoints[0])-Integer.parseInt(setPoints[1]));
						boolean isTieBrake = !(diff >=2 && diff <=6);
	
						if(isTieBrake && setPoints[0].startsWith("6")){
							char[] numbers = setPoints[0].toCharArray();
							setResult.setHomeGames(Integer.parseInt(Character.toString(numbers[0])));
	
							if(numbers.length==2){
								setResult.setTiebreakLower(Integer.parseInt(Character.toString(numbers[1])));
							}else{
								setResult.setTiebreakLower(-1); //missing tiebrake lower
							}
						}else{
							setResult.setHomeGames(Integer.parseInt(setPoints[0]));
						}
	
						if(isTieBrake && setPoints[1].startsWith("6")){
							char[] numbers = setPoints[1].toCharArray();
							setResult.setAwayGames(Integer.parseInt(Character.toString(numbers[0])));
	
							if(numbers.length==2){
								setResult.setTiebreakLower(Integer.parseInt(Character.toString(numbers[1])));
							}else{
								setResult.setTiebreakLower(-1); //missing tiebrake lower
							}
						}else{
							setResult.setAwayGames(Integer.parseInt(setPoints[1]));
						}
	
						setResults.add(setResult);
					}
	
				}
				
				result.setSetResults(setResults);
				match.setResult(result);
			
			}else{
				match.setResult(null);
				log.warn(String.format("%s - no score",match));
			}
		}else{
			match = new DoublesMatch();
			throw new IllegalStateException("not yet implemented exception!");
		}
		log.debug(String.format("created %s",match));
		return match;
	}

	public static void main(String[] args) {





	}

}
