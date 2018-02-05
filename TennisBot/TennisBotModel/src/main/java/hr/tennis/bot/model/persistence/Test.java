package hr.tennis.bot.model.persistence;

import hr.tennis.bot.model.entity.tournament.Tournament;

import java.util.Date;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		PersistenceUtil.beginTransaction();

		Tournament t = new Tournament();
		t.setName("MyTournament");
		t.setYear(new Date(2011,1,1));
		t.setCountry("Croatia");
		t.setPrize("$1000");

		DAO.save(t);

		PersistenceUtil.commitTransaction();

		PersistenceUtil.closeEntityManager();
		PersistenceUtil.close();
	}

}
