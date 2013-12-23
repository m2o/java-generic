package hr.java.item39;

import java.util.Date;

public class ImutableDate {

	private Date date;

	public ImutableDate(Date date) {
		this.date = new Date(date.getTime());
	}
	
	public Date getDate() {
		return (Date) date.clone();
	}
	
	@Override
	public String toString() {
		return date.toString();
	}
	
}
