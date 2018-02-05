package hr.tennis.bot.model.entity.match;

import hr.tennis.bot.model.Constants;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.result.Result;
import hr.tennis.bot.model.entity.tournament.Round;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name="Match.findEarliestMatchDate",
			query="SELECT min(m.date) FROM Match m"),
			@NamedQuery(name="Match.findLatestMatchDate",
					query="SELECT max(m.date) FROM Match m")
})
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="`match`")
public abstract class Match {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Temporal(value=TemporalType.DATE)
	@Column(nullable=false)
	protected Date date;

	@Embedded
	private Result result;

	@ManyToOne(optional=false, cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Round round;

	@OneToMany(mappedBy="match")
	private Set<Odd> odds;

	public Match() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Odd> getOdds() {
		return this.odds;
	}

	public void setOdds(Set<Odd> odds) {
		this.odds = odds;
	}

	public Odd getAverageOdd(){
		if(getOdds()!=null){
			for(Odd odd : getOdds()){
				if(odd.getBettingHouse().getName().equals(Constants.BETTING_HOUSE_AVERAGE)){
					return odd;
				}
			}
		}
		return null;
	}

	public boolean isExpectedOutcome(){
		Odd averageOdd = getAverageOdd();
		return averageOdd.getWinnerWinningCoefficient()<=averageOdd.getLoserWinningCoefficient();
	}

	public Round getRound() {
		return this.round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Result getResult() {
		return this.result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
