package hr.tennis.bot.model.persistence;

import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.entity.match.result.SetResult;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.tournament.Tournament.Surface;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FeatureDAO extends DAO{

	public static Double calculatePlayerWinningPercentage(List<Surface> surfaces,Player player,Date fromDate,Date toDate){

		if(surfaces == null){
			surfaces = Arrays.asList(Surface.values());
		}

		Long nMatches = (Long) PersistenceUtil.getEntityManager()
				.createNamedQuery("SinglesMatch.countMatchesBySurfaceAndDateAndAnyPlayer")
				.setParameter("surfaceArray",surfaces)
				.setParameter("fromDate",fromDate)
				.setParameter("toDate",toDate)
				.setParameter("player", player)
				.getSingleResult();

		Long nWonMatches = (Long) PersistenceUtil.getEntityManager()
				.createNamedQuery("SinglesMatch.countMatchesBySurfaceAndDateAndWinnerPlayer")
				.setParameter("surfaceArray",surfaces)
				.setParameter("fromDate",fromDate)
				.setParameter("toDate",toDate)
				.setParameter("player", player)
				.getSingleResult();

		return nWonMatches.doubleValue()/nMatches.doubleValue();
	}

	public static Double calculateH2HPercentage(List<Surface> surfaces,Player homePlayer,Player awayPlayer, Date fromDate, Date toDate) {

		if(surfaces == null){
			surfaces = Arrays.asList(Surface.values());
		}

		Long nMatches = (Long) PersistenceUtil.getEntityManager()
				.createNamedQuery("SinglesMatch.countMatchesBySurfaceAndDateAndPlayers")
				.setParameter("surfaceArray",surfaces)
				.setParameter("fromDate", fromDate)
				.setParameter("toDate", toDate)
				.setParameter("p1", homePlayer)
				.setParameter("p2", awayPlayer)
				.getSingleResult();

		Long nWonMatches = (Long) PersistenceUtil.getEntityManager()
				.createNamedQuery("SinglesMatch.countMatchesBySurfaceAndDateAndWinnerAndLoserPlayers")
				.setParameter("surfaceArray",surfaces)
				.setParameter("fromDate", fromDate)
				.setParameter("toDate", toDate)
				.setParameter("p1", homePlayer)
				.setParameter("p2", awayPlayer)
				.getSingleResult();

		return nWonMatches.doubleValue()/nMatches.doubleValue();
	}
	
	public static Double calculatePlayerSetsWonPercentage(List<Surface> surfaces,Player player,Date fromDate,Date toDate){
		
		if(surfaces == null){
			surfaces = Arrays.asList(Surface.values());
		}

		List<SinglesMatch> matches = (List<SinglesMatch>) PersistenceUtil.getEntityManager()
				.createNamedQuery("SinglesMatch.findMatchesByPlayer")
				.setParameter("player", player)
				.setParameter("surfaceArray", surfaces)
				.setParameter("fromDate", fromDate)
				.setParameter("toDate", toDate)
				.getResultList();
		
		Integer totalSets = 0;
		Integer setsWon = 0;
		
		for(SinglesMatch match : matches){
			
			List<SetResult> results = match.getResult().getSetResults();
			
			if(match.getWinnerPlayer().equals(player)){
				
				for(SetResult result : results){
					
					totalSets ++;
					if(result.getHomeGames() > result.getAwayGames()) setsWon++;
					
				}
				
			}else{
				
				for(SetResult result : results){
					
					totalSets ++;
					if(result.getHomeGames() < result.getAwayGames()) setsWon++;
					
				}
				
			}
			
		}
		
		return setsWon.doubleValue()/totalSets.doubleValue();
		
	}
}
