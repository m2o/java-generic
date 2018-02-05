package hr.tennis.bot.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.factories.PlayerFactory;
import hr.tennis.bot.model.factories.RankingFactory;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;
import hr.tennis.bot.util.DateUtil;

import java.util.Date;

import org.junit.Test;

public class QueryTests extends GenericPersistenceTest {

	@Test
	public void Ranking_findCurrentRankingByPlayerAndDate() {

		PersistenceUtil.beginTransaction();

		Player playerA = PlayerFactory.createPlayer("playerA");
		Player playerB = PlayerFactory.createPlayer("playerB");

		Date week1Minus = DateUtil.createDate(2011,10,1);
		Date week1 = DateUtil.createDate(2011,10,3);
		Date week1Plus = DateUtil.createDate(2011,10,7);
		Date week2 = DateUtil.createDate(2011,10,10);
		Date week2Plus = DateUtil.createDate(2011,10,12);

		Ranking rankingA1 = RankingFactory.createRanking(playerA, 1, 1000, week1);
		Ranking rankingB1 = RankingFactory.createRanking(playerB, 2, 800, week1);

		Ranking rankingA2 = RankingFactory.createRanking(playerA, 2, 1100, week2);
		Ranking rankingB2 = RankingFactory.createRanking(playerB, 1, 1200, week2);

		DAO.save(rankingA1);
		DAO.save(rankingB1);

		DAO.save(rankingA2);
		DAO.save(rankingB2);

		PersistenceUtil.commitTransaction();

		Ranking foundRanking = null;

		foundRanking = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",
				new ParamMap("player",playerA).addParameter("date",week1));
		assertNotNull(foundRanking);
		assertEquals(playerA,foundRanking.getPlayer());
		assertEquals(week1,foundRanking.getDate());

		foundRanking = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",
				new ParamMap("player",playerA).addParameter("date",week1Plus));
		assertNotNull(foundRanking);
		assertEquals(playerA,foundRanking.getPlayer());
		assertEquals(week1,foundRanking.getDate());

		foundRanking = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",
				new ParamMap("player",playerB).addParameter("date",week2));
		assertNotNull(foundRanking);
		assertEquals(playerB,foundRanking.getPlayer());
		assertEquals(week2,foundRanking.getDate());

		foundRanking = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",
				new ParamMap("player",playerB).addParameter("date",week2Plus));
		assertNotNull(foundRanking);
		assertEquals(playerB,foundRanking.getPlayer());
		assertEquals(week2,foundRanking.getDate());

		foundRanking = (Ranking) DAO.getSingleResult("Ranking.findCurrentRankingByPlayerAndDate",
				new ParamMap("player",playerB).addParameter("date",week1Minus));
		assertNull(foundRanking);
	}

}
