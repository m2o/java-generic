package hr.tennis.bot.model.entity.betting;

import hr.tennis.bot.model.entity.match.Match;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="odd")
//@Table(uniqueConstraints={@UniqueConstraint(columnNames={"match_id", "bettingHouse_id"})})
@NamedQueries({
	@NamedQuery(name="Odd.findOddByMatchAndBettingHouse",
			query="SELECT o FROM Odd o where o.match = :match and o.bettingHouse.name = :bettingHouseName"),
})
public class Odd {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false)
	private Double winnerWinningCoefficient;

	@Column(nullable=false)
	private Double loserWinningCoefficient;

	@ManyToOne(optional=false, cascade=CascadeType.PERSIST)
	@JoinColumn(name="_match_id")
	private Match match;

	@ManyToOne(optional=true, cascade=CascadeType.PERSIST)
	private BettingHouse bettingHouse;

	public Odd() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Double getWinnerWinningCoefficient() {
		return this.winnerWinningCoefficient;
	}

	public void setWinnerWinningCoefficient(Double homeWin) {
		this.winnerWinningCoefficient = homeWin;
	}

	public Double getLoserWinningCoefficient() {
		return this.loserWinningCoefficient;
	}

	public void setLoserWinningCoefficient(Double awayWin) {
		this.loserWinningCoefficient = awayWin;
	}

	public Match getMatch() {
		return this.match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public BettingHouse getBettingHouse() {
		return this.bettingHouse;
	}

	public void setBettingHouse(BettingHouse bettingHouse) {
		this.bettingHouse = bettingHouse;
	}

	@Override
	public String toString() {
		return String.format("Odd(%s,%f,%f)",getBettingHouse(),getWinnerWinningCoefficient(),getLoserWinningCoefficient());
	}
}
