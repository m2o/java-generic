/**
 * 
 */
package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.Tournament.Surface;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author edajzve
 *
 */
public class TournamentFactory {

	private static final Log log = LogFactory.getLog(TournamentFactory.class);

	public static Tournament createTournament(String[] tournamentParameters, String tournamentName, Date year){

		Tournament tournament = new Tournament();
		tournament.setName(tournamentName.trim());
		tournament.setCountry(tournamentParameters[0]);
		tournament.setPrize(tournamentParameters[1]);
		tournament.setYear(year);

		Surface surface = Surface.valueOf(tournamentParameters[2].replace(' ','_').toUpperCase());
		tournament.setSurface(surface);

		log.debug(String.format("created tournament %s",tournament));
		return tournament;
	}
	
	public static Tournament createTournament(String tournamentName, Date year){

		Tournament tournament = new Tournament();
		tournament.setName(tournamentName.trim());
		tournament.setCountry(null);
		tournament.setPrize(null);
		tournament.setYear(year);
		tournament.setSurface(Surface.NOT_SET);

		log.debug(String.format("created tournament %s",tournament));
		return tournament;
	}
}
