package hr.tennis.bot.model.test;


import hr.tennis.bot.model.Constants;
import hr.tennis.bot.model.persistence.PersistenceUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class GenericPersistenceTest {

	@BeforeClass
	public static void initAll(){
		PersistenceUtil.initialize(Constants.PERSISTENCE_UNIT_TEST);
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

}
