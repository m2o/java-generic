package hr.tennis.bot.model.entity.match;

import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.util.DateUtil;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "singlesmatch")
// @Table(uniqueConstraints={@UniqueConstraint(columnNames={"homePlayer_id",
// "awayPlayer_id","date"})})
@NamedQueries({
		@NamedQuery(name = "SinglesMatch.findMatchesByGender", query = "SELECT sm FROM SinglesMatch sm where sm.winnerPlayer.gender = :gender"),
		@NamedQuery(name = "SinglesMatch.findEarliestMatchDateByGender", query = "SELECT min(sm.date) FROM SinglesMatch sm where sm.winnerPlayer.gender = :gender"),
		@NamedQuery(name = "SinglesMatch.findLatestMatchDateByGender", query = "SELECT max(sm.date) FROM SinglesMatch sm where sm.winnerPlayer.gender = :gender"),
		@NamedQuery(name = "SinglesMatch.findMatchesByDateAndGender", query = "SELECT sm FROM SinglesMatch sm where sm.date = :date and sm.winnerPlayer.gender = :gender"),
		@NamedQuery(name = "SinglesMatch.findMatchesByIntervalAndGender", query = "SELECT sm FROM SinglesMatch sm where sm.date >= :dateFrm and sm.date <= :dateTo and sm.winnerPlayer.gender = :gender"),
		@NamedQuery(name = "SinglesMatch.countMatchesBySurfaceAndDateAndAnyPlayer", query = "select count(s) "
				+ "from SinglesMatch s "
				+ "where s.round.tournamentInstance.tournament.surface in (:surfaceArray) and "
				+ "s.date < :toDate and "
				+ "s.date >= :fromDate and "
				+ "(s.winnerPlayer = :player or s.loserPlayer = :player)"),
		@NamedQuery(name = "SinglesMatch.countMatchesBySurfaceAndDateAndWinnerPlayer", query = "select count(s) "
				+ "from SinglesMatch s "
				+ "where s.round.tournamentInstance.tournament.surface in (:surfaceArray) and "
				+ "s.date < :toDate and "
				+ "s.date >= :fromDate and "
				+ "s.winnerPlayer = :player"),
		@NamedQuery(name = "SinglesMatch.countMatchesBySurfaceAndDateAndPlayers", query = "select count(s) "
				+ "from SinglesMatch s "
				+ "where s.round.tournamentInstance.tournament.surface in (:surfaceArray) and "
				+ "s.date < :toDate and "
				+ "s.date >= :fromDate and "
				+ "((s.winnerPlayer = :p1 and s.loserPlayer = :p2) or "
				+ "(s.winnerPlayer = :p2 and s.loserPlayer = :p1))"),
		@NamedQuery(name = "SinglesMatch.countMatchesBySurfaceAndDateAndWinnerAndLoserPlayers", query = "select count(s) "
				+ "from SinglesMatch s "
				+ "where s.round.tournamentInstance.tournament.surface in (:surfaceArray) and "
				+ "s.date < :toDate and "
				+ "s.date >= :fromDate and "
				+ "s.winnerPlayer = :p1 and s.loserPlayer = :p2"),
		@NamedQuery(name = "SinglesMatch.findMatchesByPlayer", query = "select sm from SinglesMatch sm "
				+ "where (sm.winnerPlayer = :player or sm.loserPlayer = :player) and "
				+ "sm.round.tournamentInstance.tournament.surface in (:surfaceArray)"
				+ " and sm.date < :toDate and " + "sm.date >= :fromDate") })
public class SinglesMatch extends Match {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "homePlayer_id")
	private Player winnerPlayer;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "awayPlayer_id")
	private Player loserPlayer;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "homePlayerRanking_id")
	private Ranking winnerPlayerRanking;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "awayPlayerRanking_id")
	private Ranking loserPlayerRanking;

	public SinglesMatch() {

	}

	public SinglesMatch(Player winnerPlayer, Player loserPlayer,
			Ranking winnerPlayerRanking, Ranking loserPlayerRanking) {

		this.winnerPlayer = winnerPlayer;
		this.loserPlayer = loserPlayer;
		this.winnerPlayerRanking = winnerPlayerRanking;
		this.loserPlayerRanking = loserPlayerRanking;

	}

	public Player getWinnerPlayer() {
		return this.winnerPlayer;
	}

	public void setWinnerPlayer(Player homePlayer) {
		this.winnerPlayer = homePlayer;
	}

	public Player getLoserPlayer() {
		return this.loserPlayer;
	}

	public void setLoserPlayer(Player awayPlayer) {
		this.loserPlayer = awayPlayer;
	}

	public Ranking getWinnerPlayerRanking() {
		return this.winnerPlayerRanking;
	}

	public void setWinnerPlayerRanking(Ranking homePlayerRanking) {
		this.winnerPlayerRanking = homePlayerRanking;
	}

	public Ranking getLoserPlayerRanking() {
		return this.loserPlayerRanking;
	}

	public void setLoserPlayerRanking(Ranking awayPlayerRanking) {
		this.loserPlayerRanking = awayPlayerRanking;
	}

	@Override
	public String toString() {
		return String.format("Match(%s,%s,%s,%s,%s)", this.getWinnerPlayer(),
				this.getLoserPlayer(),
				DateUtil.formatDateSimple(this.getDate()), getResult(),
				getAverageOdd());
	}
}
