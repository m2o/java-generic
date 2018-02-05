package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;

import java.util.Date;

/**
 * @author toni
 *
 */
public class RankingFactory {

	public static Ranking createRanking(Player player,Integer position,Integer points,Date date){
		Ranking ranking = new Ranking();
		ranking.setGender(player.getGender());
		ranking.setPlayer(player);
		ranking.setPoints(points);
		ranking.setPosition(position);
		ranking.setDate(date);
		return ranking;
	}

	public static void main(String[] args) {

		//		PersistenceUtil.initialize();
		//
		//		//Ranking ranking = createRanking("Andy Murray", 5, 1212, DateUtil.createDate(2010, 11, 1), "link");
		//
		//		PersistenceUtil.beginTransaction();
		//		DAO.save(ranking);
		//		PersistenceUtil.commitTransaction();
		//
		//		PersistenceUtil.close();
	}

}
