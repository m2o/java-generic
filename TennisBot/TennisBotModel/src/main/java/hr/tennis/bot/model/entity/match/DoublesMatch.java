package hr.tennis.bot.model.entity.match;

import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Ranking;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="doublesmatch")
//@Table(uniqueConstraints={@UniqueConstraint(columnNames={"homePlayerA_id", "homePlayerB_id","awayPlayerA_id", "awayPlayerB_id","date"})})
public class DoublesMatch extends Match{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	private Player homePlayerA;

	@ManyToOne(optional=false)
	private Player homePlayerB;

	@ManyToOne(optional=false)
	private Player awayPlayerA;

	@ManyToOne(optional=false)
	private Player awayPlayerB;

	@ManyToOne(optional=false)
	private Ranking homePlayerARanking;

	@ManyToOne(optional=false)
	private Ranking homePlayerBRanking;

	@ManyToOne(optional=false)
	private Ranking awayPlayerARanking;

	@ManyToOne(optional=false)
	private Ranking awayPlayerBRanking;

	public DoublesMatch() {

	}

	public Player getHomePlayerA() {
		return this.homePlayerA;
	}

	public void setHomePlayerA(Player homePlayerA) {
		this.homePlayerA = homePlayerA;
	}

	public Player getHomePlayerB() {
		return this.homePlayerB;
	}

	public void setHomePlayerB(Player homePlayerB) {
		this.homePlayerB = homePlayerB;
	}

	public Player getAwayPlayerA() {
		return this.awayPlayerA;
	}

	public void setAwayPlayerA(Player awayPlayerA) {
		this.awayPlayerA = awayPlayerA;
	}

	public Player getAwayPlayerB() {
		return this.awayPlayerB;
	}

	public void setAwayPlayerB(Player awayPlayerB) {
		this.awayPlayerB = awayPlayerB;
	}

	public Ranking getHomePlayerARanking() {
		return this.homePlayerARanking;
	}

	public void setHomePlayerARanking(Ranking homePlayerARanking) {
		this.homePlayerARanking = homePlayerARanking;
	}

	public Ranking getHomePlayerBRanking() {
		return this.homePlayerBRanking;
	}

	public void setHomePlayerBRanking(Ranking homePlayerBRanking) {
		this.homePlayerBRanking = homePlayerBRanking;
	}

	public Ranking getAwayPlayerARanking() {
		return this.awayPlayerARanking;
	}

	public void setAwayPlayerARanking(Ranking awayPlayerARanking) {
		this.awayPlayerARanking = awayPlayerARanking;
	}

	public Ranking getAwayPlayerBRanking() {
		return this.awayPlayerBRanking;
	}

	public void setAwayPlayerBRanking(Ranking awayPlayerBRanking) {
		this.awayPlayerBRanking = awayPlayerBRanking;
	}
}
