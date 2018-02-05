package hr.tennis.bot.model.learning;

import hr.tennis.bot.model.comparator.RoundComparator;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Player.Hand;
import hr.tennis.bot.model.entity.tournament.Round;
import hr.tennis.bot.model.entity.tournament.Tournament;
import hr.tennis.bot.model.entity.tournament.Tournament.Surface;
import hr.tennis.bot.model.entity.tournament.TournamentInstance;
import hr.tennis.bot.model.persistence.FeatureDAO;
import hr.tennis.bot.util.DateUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MatchLearningInstanceMaker{

	private static final RoundComparator roundComparator = new RoundComparator();

	private static final Date BEGINNING = DateUtil.createDate(1900, 1, 1);

	private final SinglesMatch match;
	private final Player winnerPlayer;
	private final Player loserPlayer;
	private final Date date;
	private final Round round;
	private final TournamentInstance tournamentInstance;
	private final Tournament tournament;
	private final Surface surface;
	private final Odd averageOdd;
	private final boolean expectedOutcome;

	public MatchLearningInstanceMaker(SinglesMatch match) {
		this.match = match;
		this.winnerPlayer = match.getWinnerPlayer();
		this.loserPlayer = match.getLoserPlayer();
		this.date = match.getDate();
		this.round = match.getRound();
		this.tournamentInstance = this.round.getTournamentInstance();
		this.tournament = this.tournamentInstance.getTournament();
		this.surface = this.tournament.getSurface();

		this.averageOdd = match.getAverageOdd();
		this.expectedOutcome = match.isExpectedOutcome();
	}

	private Player getAwayPlayer() {
		return this.expectedOutcome ? this.loserPlayer : this.winnerPlayer;
	}

	private Player getHomePlayer() {
		return this.expectedOutcome ? this.winnerPlayer : this.loserPlayer;
	}

	/* name */

	public String calculateName(){
		return String.format("#%-12d %-16s %-36s %-36s %-36s %-36s %-12s",
				this.match.getId(),
				DateUtil.formatDateSimple(this.match.getDate()),
				String.format("%s(%s)",this.tournament.getName(),
						this.tournament.getValue() != null ?
								this.tournament.getValue().name().toLowerCase() : "other"),
								this.round.getName(),
								this.match.getWinnerPlayer().getName(),
								this.match.getLoserPlayer().getName(),
								this.match.getResult()
				);
	}

	/* out */

	public Boolean calculateHomeWin(){
		return this.expectedOutcome;
	}

	/* generic */

	public Integer calculateDayOfYear(){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(this.match.getDate());
		return gc.get(Calendar.DAY_OF_YEAR);
	}

	public Double calculateRound(){
		int roundIndex = roundComparator.ROUND_NAMES.indexOf(this.round.getName());
		return ((double)roundIndex)/(roundComparator.ROUND_NAMES.size()-1);
	}

	/* player */

	/* ranking */

	/* results */

	/* match statistics */

	/* odds */

	public Double calculateHomePlayerAverageOdd(){
		if(this.expectedOutcome){
			return this.averageOdd.getWinnerWinningCoefficient();
		}else{
			return this.averageOdd.getLoserWinningCoefficient();
		}
	}

	public Double calculateAwayPlayerAverageOdd(){
		if(this.expectedOutcome){
			return this.averageOdd.getLoserWinningCoefficient();
		}else{
			return this.averageOdd.getWinnerWinningCoefficient();
		}
	}

	public Integer calculateHomePlayerRankingPoints(){
		if(this.expectedOutcome){
			return this.match.getWinnerPlayerRanking().getPoints();
		}else{
			return this.match.getLoserPlayerRanking().getPoints();
		}
	}

	public Integer calculateAwayPlayerRankingPoints(){
		if(this.expectedOutcome){
			return this.match.getLoserPlayerRanking().getPoints();
		}else{
			return this.match.getWinnerPlayerRanking().getPoints();
		}
	}

	public Integer calculateHomePlayerRankingPosition(){
		if(this.expectedOutcome){
			return this.match.getWinnerPlayerRanking().getPosition();
		}else{
			return this.match.getLoserPlayerRanking().getPosition();
		}
	}

	public Integer calculateAwayPlayerRankingPosition(){
		if(this.expectedOutcome){
			return this.match.getLoserPlayerRanking().getPosition();
		}else{
			return this.match.getWinnerPlayerRanking().getPosition();
		}
	}

	public Integer calculateHomePlayerPrefferedHand(){

		Hand hand = null;

		if(!this.expectedOutcome){
			hand = this.match.getLoserPlayer().getPreferredHand();
		}else{
			hand =  this.match.getWinnerPlayer().getPreferredHand();
		}
		return hand!=null ? hand.ordinal() : null;
	}

	public Integer calculateAwayPlayerPrefferedHand(){
		Hand hand = null;
		if(this.expectedOutcome){
			hand = this.match.getLoserPlayer().getPreferredHand();
		}else{
			hand =  this.match.getWinnerPlayer().getPreferredHand();
		}
		return hand!=null ? hand.ordinal() : null;
	}

	public Double calculateHomePlayerWinningPercentage(){
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerWinningPercentage(null,getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentage(){
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerWinningPercentage(null,getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateHomePlayerWinningPercentage1Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentage1Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateHomePlayerWinningPercentage12Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getHomePlayer(),fromDate,this.date);
	}
	
	public Double calculateHomePlayerWinningPercentage6Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -6);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentage12Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getAwayPlayer(),fromDate,this.date);
	}
	
	public Double calculateAwayPlayerWinningPercentage6Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerWinningPercentage(null,getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateHomePlayerWinningPercentageBySurface(){
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentageBySurface(){
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateHomePlayerWinningPercentageBySurface1Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentageBySurface1Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateHomePlayerWinningPercentageBySurface12Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getHomePlayer(),fromDate,this.date);
	}

	public Double calculateAwayPlayerWinningPercentageBySurface12Month(){
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerWinningPercentage(Arrays.asList(this.surface),getAwayPlayer(),fromDate,this.date);
	}

	public Double calculateH2hWinningPercentage(){

		Player homePlayer = getHomePlayer();
		Player awayPlayer = getAwayPlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculateH2HPercentage(null, homePlayer,awayPlayer, fromDate, this.date);
	}

	public Double calculateH2hWinningPercentage12Month(){

		Player homePlayer = getHomePlayer();
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculateH2HPercentage(null, homePlayer,awayPlayer, fromDate, this.date);
	}

	public Double calculateH2hWinningPercentageBySurface(){

		Player homePlayer = getHomePlayer();
		Player awayPlayer = getAwayPlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculateH2HPercentage(Arrays.asList(this.surface), homePlayer,awayPlayer, fromDate, this.date);
	}

	public Double calculateH2hWinningPercentageBySurface12Month(){

		Player homePlayer = getHomePlayer();
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculateH2HPercentage(Arrays.asList(this.surface), homePlayer,awayPlayer, fromDate, this.date);
	}
	
	public Integer calculateHomePlayerHeight(){
		
		Player homePlayer = getHomePlayer();		
		return homePlayer.getHeight();
	}
	
	public Integer calculateAwayPlayerHeight(){
		
		Player awayPlayer = getAwayPlayer();		
		return awayPlayer.getHeight();
	}
	
	public Integer calculateHomePlayerWeight(){
	
		Player homePlayer = getHomePlayer();
		return homePlayer.getWeight();
		
	}
	
	public Integer calculateAwayPlayerWeight(){
	
		Player awayPlayer = getAwayPlayer();
		return awayPlayer.getWeight();
	
	}
	
	public Integer calculateHomePlayerYearsPro(){
		
		Player homePlayer = getHomePlayer();
		if(homePlayer.getDateOfBirth() == null) return null;
		return DateUtil.getDatePart(this.date, Calendar.YEAR) - DateUtil.getDatePart(homePlayer.getDateOfBirth(), Calendar.YEAR);
	}
	
	public Integer calculateAwayPlayerYearsPro(){
		
		Player awayPlayer = getAwayPlayer();
		if(awayPlayer.getDateOfBirth() == null) return null;
		return DateUtil.getDatePart(this.date, Calendar.YEAR) - DateUtil.getDatePart(awayPlayer.getDateOfBirth(), Calendar.YEAR);
	}
	
	public Double calculateHomePlayerSetsWonPercentage(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, homePlayer, fromDate, this.date);
	}
	
	public Double calculateHomePlayerSetsWonPercentage1Month(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, homePlayer, fromDate, this.date);
	}
	
	public Double calculateHomePlayerSetsWonPercentage6Month(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -6);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, homePlayer, fromDate, this.date);
	}
	
	public Double calculateHomePlayerSetsWonPercentage12Month(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, homePlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentage(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, awayPlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentage1Month(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -1);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, awayPlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentage6Month(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -6);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, awayPlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentage12Month(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerSetsWonPercentage(null, awayPlayer, fromDate, this.date);
	}
	
	public Double calculateHomePlayerSetsWonPercentageBySurface(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerSetsWonPercentage(Arrays.asList(this.surface), homePlayer, fromDate, this.date);
	}
	
	public Double calculateHomePlayerSetsWonPercentageBySurface12Month(){
		
		Player homePlayer = getHomePlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerSetsWonPercentage(Arrays.asList(this.surface), homePlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentageBySurface(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = BEGINNING;
		return FeatureDAO.calculatePlayerSetsWonPercentage(Arrays.asList(this.surface), awayPlayer, fromDate, this.date);
	}
	
	public Double calculateAwayPlayerSetsWonPercentageBySurface12Month(){
		
		Player awayPlayer = getAwayPlayer();
		Date fromDate = DateUtil.add(this.date, Calendar.MONTH, -12);
		return FeatureDAO.calculatePlayerSetsWonPercentage(Arrays.asList(this.surface), awayPlayer, fromDate, this.date);
	}

}
