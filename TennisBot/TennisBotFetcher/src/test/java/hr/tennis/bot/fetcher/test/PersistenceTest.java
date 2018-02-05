package hr.tennis.bot.fetcher.test;


import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Player.Hand;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class PersistenceTest {

	@BeforeClass
	public static void initAll(){
		PersistenceUtil.initialize();
	}

	@AfterClass
	public static void destroyAll(){
		PersistenceUtil.close();
	}

	@Before
	public void init(){
		PersistenceUtil.getEntityManager();
	}

	@After
	public void destroy(){
		PersistenceUtil.closeEntityManager();
	}

	@Test
	public void insertTest() {
		PersistenceUtil.beginTransaction();

		Player p = new Player();
		p.setDateOfBirth(DateUtil.createDate(1975, 1, 2));
		p.setFirstProYear(DateUtil.createDate(2000));
		p.setGender(Gender.MALE);
		p.setName("Marco Rossi");
		p.setPreferredHand(Hand.RIGHT);
		p.setWeight(120);
		p.setHeight(100);

		DAO.save(p);

		PersistenceUtil.commitTransaction();
	}

}
