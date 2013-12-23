package hr.java.item39;

import java.util.Date;

public class BrokenImutableDate {

	private Date date;

	public BrokenImutableDate(Date date) {
		this.date = (Date) date.clone(); //security hole
	}
	
	public Date getDate() {
		return (Date) date.clone();
	}
	
	@Override
	public String toString() {
		return date.toString();
	}
	
}
