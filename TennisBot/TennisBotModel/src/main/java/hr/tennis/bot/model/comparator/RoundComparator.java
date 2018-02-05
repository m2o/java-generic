package hr.tennis.bot.model.comparator;

import hr.tennis.bot.model.entity.tournament.Round;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class RoundComparator implements Comparator<Round> {

	public static List<String> ROUND_NAMES;

	static{

	    String[] roundNames = { "-",
								"Qualification - 1. round",
								"Qualification - 2. round",
								"Qualification - 3. round",
								"Qualification - round of 16",
								"Qualification - quarterfinal",
								"Qualification - semifinal",
								"Qualification - final",
								"1. round",
								"2. round",
								"3. round",
								"4. round",
								"5. round",
								"6. round",
								"7. round",
								"8. round",
								"9. round",
								"round of 16",
								"quarterfinal",
								"semifinal",
								"final"};

	    ROUND_NAMES = Arrays.asList(roundNames);
	}

	@Override
	public int compare(Round r1, Round r2) {

		Integer r1Index = ROUND_NAMES.indexOf(r1.getName());
		Integer r2Index = ROUND_NAMES.indexOf(r2.getName());

		if(r1Index==-1){
			throw new IllegalArgumentException("illegal round name: "+r1.getName());
		}
		if(r2Index==-1){
			throw new IllegalArgumentException("illegal round name: "+r1.getName());
		}

		return r1Index.compareTo(r2Index);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		Tournament t = (Tournament) DAO.getSingleResult("Tournament.findByNameAnyYear",
									new ParamMap("name", "Wimbledon")
									.addParameter("year",DateUtil.createDate(2011)));

		System.out.println(t);

		for(TournamentInstance ti : t.getTournamentInstances()){

			System.out.println(ti);

			SortedSet<Round> sortedRounds = new TreeSet<Round>(new RoundComparator());
			sortedRounds.addAll(ti.getRounds());

			for(Round r : sortedRounds){
				System.out.println(r);
			}
		}

		PersistenceUtil.getEntityManager().close();
		PersistenceUtil.close();

	}

}
