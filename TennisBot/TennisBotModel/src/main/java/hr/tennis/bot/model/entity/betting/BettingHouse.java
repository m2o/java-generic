package hr.tennis.bot.model.entity.betting;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bettinghouse")
@NamedQueries({
	@NamedQuery(name="BettingHouse.findBettingHouseByName",
			query="SELECT r FROM BettingHouse r where r.name = :name")
})
public class BettingHouse {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false,unique=true)
	private String name;

	@Column(nullable=true)
	private String homepage;

	@OneToMany(mappedBy="bettingHouse")
	private Set<Odd> odds;

	public BettingHouse() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Set<Odd> getOdds() {
		return this.odds;
	}

	public void setOdds(Set<Odd> odds) {
		this.odds = odds;
	}

	@Override
	public String toString() {
		return String.format("BettingHouse(%s)",getName());
	}
}
