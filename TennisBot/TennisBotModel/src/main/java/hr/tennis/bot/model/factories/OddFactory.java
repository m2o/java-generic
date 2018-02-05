/**
 * 
 */
package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.entity.betting.BettingHouse;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.Match;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author edajzve
 *
 */
public class OddFactory {
	
	public static final Log log = LogFactory.getLog(OddFactory.class);
	
	public static Odd createOdd(String homeOdd, String awayOdd, BettingHouse bettingHouse, Match match){
		
		Odd odd = new Odd();
		odd.setMatch(match);
		odd.setWinnerWinningCoefficient(Double.parseDouble(homeOdd));
		odd.setLoserWinningCoefficient(Double.parseDouble(awayOdd));
		odd.setBettingHouse(bettingHouse);
		log.debug(String.format("created %s",odd));
		return odd;
	}

}
