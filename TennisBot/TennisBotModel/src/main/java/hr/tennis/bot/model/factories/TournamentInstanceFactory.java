/**
 * 
 */
package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author edajzve
 *
 */
public class TournamentInstanceFactory {

	private static final Log log = LogFactory.getLog(TournamentInstanceFactory.class);

	public static TournamentInstance createTournamentInstance(Tournament tour,
			Gender gender, TournamentType tournamentType) {

		TournamentInstance instance = new TournamentInstance();
		instance.setTournament(tour);

		instance.setGender(gender);
		instance.setType(tournamentType);

		log.debug(String.format("created tournament instance %s",instance));

		return instance;
	}



}
