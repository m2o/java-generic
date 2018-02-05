package hr.tennis.bot.model.test;


import static org.junit.Assert.assertEquals;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import java.util.Date;

import org.junit.Test;

public class PersistenceTest extends GenericPersistenceTest {

	@Test
	public void insertTest() {
		PersistenceUtil.beginTransaction();

		Date year = DateUtil.createDate(2011);

		Tournament t = new Tournament();
		t.setName("MyTournament");
		t.setYear(year);
		t.setPrize("$100");
		t.setSurface(Tournament.Surface.CLAY);
		t.setCountry("China");

		DAO.save(t);

		PersistenceUtil.commitTransaction();

		Tournament newT = (Tournament) DAO.getSingleResult("Tournament.findByNameAnyYear", new ParamMap()
		.addParameter("name", "MyTournament")
		.addParameter("year",year));

		assertEquals(t,newT);
		assertEquals(t.getName(),newT.getName());
		assertEquals(t.getCountry(),newT.getCountry());

		PersistenceUtil.beginTransaction();
		DAO.delete(newT);
		PersistenceUtil.commitTransaction();
	}

}
