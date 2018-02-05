package hr.tennis.bot.model.entity.match.result;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CollectionOfElements;


@Embeddable
public class Result {

	private static final long serialVersionUID = 1L;

	@Column(nullable=true)
	private Boolean homeWin;

	@Column(nullable=true)
	private Boolean forfeited;

	//@ElementCollection(targetClass=SetResult.class)
	//@CollectionTable(name = "SetResult", joinColumns = {@JoinColumn(name="match_id")})
	@CollectionOfElements(fetch=FetchType.EAGER)
	@JoinTable(name = "setresult",joinColumns = {@JoinColumn(name = "match_id")})
	private List<SetResult> setResults;

	public Result() {

	}

	public Boolean getHomeWin() {
		return this.homeWin;
	}

	public void setHomeWin(Boolean homeWin) {
		this.homeWin = homeWin;
	}

	public Boolean getForfeited() {
		return this.forfeited;
	}

	public void setForfeited(Boolean forfeited) {
		this.forfeited = forfeited;
	}

	public List<SetResult> getSetResults() {
		return this.setResults;
	}

	public void setSetResults(List<SetResult> setResults) {
		if(this.setResults == null){
			this.setResults = new ArrayList<SetResult>();
		}
		this.setResults = setResults;
	}

	@Override
	public String toString() {
		return String.format("%s",
				StringUtils.join(getSetResults().toArray(),","),
				(getForfeited() ? "(F)" : ""));
	}
}
