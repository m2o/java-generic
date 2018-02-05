package hr.tennis.bot.model.entity.tournament;

import hr.tennis.bot.model.TournamentValue;
import hr.tennis.bot.model.comparator.RoundComparator;
import hr.tennis.bot.model.entity.match.Match;
import hr.tennis.bot.util.DateUtil;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="tournament",uniqueConstraints={@UniqueConstraint(columnNames={"name", "year"})})
@NamedQueries({
	@NamedQuery(name="Tournament.findByNameAnyYear", query="SELECT t FROM Tournament t where t.name = :name and t.year = :year")
})
public class Tournament {

	public enum Surface{
		INDOORS,CLAY,GRASS,HARD,VARIOUS_SURFACES,NOT_SET
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false)
	private String name;

	@Temporal(value=TemporalType.DATE)
	@Column(nullable=false)
	private Date year;

	@Column(nullable=true)
	private String country;

	@Column(nullable=true)
	private String prize;

	@Column(nullable=true)
	private Surface surface;

	private TournamentValue value;

	@OneToMany(mappedBy="tournament")
	private Set<TournamentInstance> tournamentInstances;

	public Tournament() {

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

	public Date getYear() {
		return this.year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getPrize() {
		return this.prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Surface getSurface() {
		return this.surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}

	public Set<TournamentInstance> getTournamentInstances() {
		return this.tournamentInstances;
	}

	public void setTournamentInstances(Set<TournamentInstance> tournamentInstances) {
		this.tournamentInstances = tournamentInstances;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	//	public TournamentInstance findTournamentInstance(Gender gender,TournamentType type){
	//		for(TournamentInstance instance : this.getTournamentInstances()){
	//			if(instance.getGender().equals(gender) && instance.getType().equals(type)){
	//				return instance;
	//			}
	//		}
	//		return null;
	//	}

	public void setValue(TournamentValue value) {
		this.value = value;
	}

	public TournamentValue getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Tournament other = (Tournament) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Tournament(%s,%s,%s)",this.getName(),DateUtil.formatDate(this.getYear(),"yyyy"),getSurface());
	}

	public void printSchedule() {

		System.out.println(this);
		for(TournamentInstance ti : this.getTournamentInstances()){
			System.out.printf("\t%s\n",ti);
			Set<Round> rounds = new TreeSet<Round>(new RoundComparator());
			rounds.addAll(ti.getRounds());
			for(Round r : rounds){
				System.out.printf("\t\t%s\n",r);
				for(Match m : r.getMatches()){
					System.out.printf("\t\t\t%s\n",m);
				}
			}
		}

	}
}
