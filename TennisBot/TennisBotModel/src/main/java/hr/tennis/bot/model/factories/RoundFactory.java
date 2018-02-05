/**
 * 
 */
package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.entity.tournament.Round;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;

/**
 * @author edajzve
 *
 */
public class RoundFactory {
	
	public static Round createRound(String name, TournamentInstance tourInstance){
		
		Round round = new Round();
		
		round.setName(name);
		round.setTournamentInstance(tourInstance);
		
		return round;
		
	}

}
