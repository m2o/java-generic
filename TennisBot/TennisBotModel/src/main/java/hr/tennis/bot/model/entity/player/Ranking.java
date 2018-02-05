package hr.tennis.bot.model.entity.player;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.util.DateUtil;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="ranking",uniqueConstraints={@UniqueConstraint(columnNames={"date", "player_id"})})
@NamedQueries({
	@NamedQuery(name="Ranking.findCurrentRankingByPlayerAndDate",
			query="SELECT r FROM Ranking r where r.player = :player and r.date = (SELECT max(r2.date) from Ranking r2 where r2.date <= :date )"),
	@NamedQuery(name="Ranking.findLatestRankingDate",
			query="SELECT max(r.date) FROM Ranking r"),
	@NamedQuery(name="Ranking.rankingsCountOnDate",
			query="SELECT count(r.id) FROM Ranking r where r.date = :date and r.gender = :gender")
})
public class Ranking {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@ManyToOne(optional=false,cascade=CascadeType.PERSIST)
	private Player player;

	@Column(nullable=true)
	private Gender gender;

	@Column(nullable=false)
	private Integer position;

	@Column(nullable=false)
	private Integer points;

	@Temporal(value=TemporalType.DATE)
	@Column(nullable=false)
	private Date date;

	public Ranking() {

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return String.format("Ranking(%s,%s)",this.getPlayer(),DateUtil.formatDate(this.getDate(),DateUtil.SIMPLE_DATE_FORMAT));
	}
}
