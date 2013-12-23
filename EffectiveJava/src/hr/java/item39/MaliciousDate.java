package hr.java.item39;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MaliciousDate extends Date {

	private static List<Date> instantiatedDates = new LinkedList<Date>();
	
	public MaliciousDate() {
		super();
		instantiatedDates.add(this);
	}
	
	public static List<Date> getInstantiatedDates() {
		return instantiatedDates;
	}
	
	public static void clearInstantiatedDates(){
		instantiatedDates.clear();
	}
	
	public Date clone() {
		Date d = (Date) super.clone();
		instantiatedDates.add(d);
		return d;
	}
}
