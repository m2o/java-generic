/**
 * 
 */
package hr.tennis.bot.model.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hr.tennis.bot.model.entity.betting.BettingHouse;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.Match;


public class BettingHouseFactory {
	
	public static final Log log = LogFactory.getLog(BettingHouseFactory.class);
	
	public static BettingHouse createBettingHouse(String name,String homepage){
		
		BettingHouse bh = new BettingHouse();
		bh.setName(name);
		bh.setHomepage(homepage);
		log.debug(String.format("created %s",bh));
		return bh;
	}
	
}
