package hr.tennis.bot.model.entity.tournament;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name="tournamentinstance",uniqueConstraints={@UniqueConstraint(columnNames={"tournament_id", "type", "gender"})})
@NamedQueries({
	@NamedQuery(name="TournamentInstance.findByTournamentGenderType", query="SELECT t FROM TournamentInstance t where t.tournament = :tournament and t.gender =:gender and t.type = :type")
})
public class TournamentInstance {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false)
	private TournamentType type;

	@Column(nullable=false)
	private Gender gender;

	@ManyToOne(optional=false, cascade=CascadeType.PERSIST)
	private Tournament tournament;

	@OneToMany(mappedBy="tournamentInstance")
	private Set<Round> rounds;

	public TournamentInstance() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public TournamentType getType() {
		return this.type;
	}

	public void setType(TournamentType type) {
		this.type = type;
	}

	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Set<Round> getRounds() {
		return this.rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return String.format("TournamentInstance(%s,%s,%s)",this.getTournament(),this.getType(),this.getGender());
	}
}
