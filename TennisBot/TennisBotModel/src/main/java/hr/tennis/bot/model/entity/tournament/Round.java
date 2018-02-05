package hr.tennis.bot.model.entity.tournament;

import hr.tennis.bot.model.entity.match.Match;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="round",uniqueConstraints={@UniqueConstraint(columnNames={"name", "tournamentInstance_id"})})
@NamedQueries({
	@NamedQuery(name="Round.findByNameTournamentInstance", query="SELECT t FROM Round t where t.tournamentInstance = :tournamentInstance and t.name =:name")
})
public class Round {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false)
	private String name;

	@ManyToOne(optional=false)
	private TournamentInstance tournamentInstance;

	@OneToMany(mappedBy="round")
	private Set<Match> matches;

	public Round() {

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

	public TournamentInstance getTournamentInstance() {
		return this.tournamentInstance;
	}

	public void setTournamentInstance(TournamentInstance tournamentInstance) {
		this.tournamentInstance = tournamentInstance;
	}

	public Set<Match> getMatches() {
		return this.matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}
	
	@Override
	public String toString() {
		return String.format("Round(%s,%s)",getTournamentInstance(),getName());
	}
}
