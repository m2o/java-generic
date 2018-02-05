package hr.tennis.bot.model.entity.player;

import hr.tennis.bot.model.Gender;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="player")
@NamedQueries({
	@NamedQuery(name="Player.findByName", query="SELECT p FROM Player p where p.name = :name")
})
public class Player {

	public enum Hand{
		LEFT,RIGHT
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(nullable=false,unique=true)
	private String name;

	@Temporal(value=TemporalType.DATE)
	@Column(nullable=true)
	private Date dateOfBirth;

	@Column(nullable=true)
	private Integer height;

	@Column(nullable=true)
	private Integer weight;

	@Column(nullable=true)
	private Date firstProYear;

	@Column(nullable=false)
	private Gender gender;

	@Column(nullable=true)
	private Hand preferredHand;

	@OneToMany(mappedBy="player")
	private Set<Ranking> rankings;

	@Column(nullable=true)
	private String link;

	public Player() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Date getFirstProYear() {
		return this.firstProYear;
	}

	public void setFirstProYear(Date firstProYear) {
		this.firstProYear = firstProYear;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Hand getPreferredHand() {
		return this.preferredHand;
	}

	public void setPreferredHand(Hand playsHand) {
		this.preferredHand = playsHand;
	}

	public Set<Ranking> getRankings() {

		return this.rankings;
	}

	public void setRankings(Set<Ranking> rankings) {
		this.rankings = rankings;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return String.format("Player(%s)",this.getName());
	}
}
