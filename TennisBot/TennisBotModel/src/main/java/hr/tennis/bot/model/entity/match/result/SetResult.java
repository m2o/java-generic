package hr.tennis.bot.model.entity.match.result;

import javax.persistence.Embeddable;

@Embeddable
public class SetResult{

	private Integer number;
	private Integer homeGames;
	private Integer awayGames;
	private Integer tiebreakLower;

	public SetResult() {

	}

	public Integer getHomeGames() {
		return this.homeGames;
	}

	public void setHomeGames(Integer homeGames) {
		this.homeGames = homeGames;
	}

	public Integer getAwayGames() {
		return this.awayGames;
	}

	public void setAwayGames(Integer awayGames) {
		this.awayGames = awayGames;
	}

	public Integer getTiebreakLower() {
		return this.tiebreakLower;
	}

	public void setTiebreakLower(Integer tiebreakLower) {
		this.tiebreakLower = tiebreakLower;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		if(this.tiebreakLower!=null){
			return String.format("%d:%d(%d)",getHomeGames(),getAwayGames(),getTiebreakLower());
		}else{
			return String.format("%d:%d",getHomeGames(),getAwayGames());
		}
	}
}