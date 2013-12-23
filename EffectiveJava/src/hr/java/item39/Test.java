package hr.java.item39;

import java.util.Date;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		MaliciousDate.clearInstantiatedDates();
		MaliciousDate d = new MaliciousDate();
		ImutableDate id = new ImutableDate(d);
		System.out.println(id);
		
		//constructor attack
		d.setTime(100000);
		System.out.println(id);
		
		//accessor attack
		id.getDate().setTime(100000);
		System.out.println(id);
		
		//constructor-clone attack
		List<Date> dates = MaliciousDate.getInstantiatedDates();
		System.out.println(dates);
		//dates.get(1).setTime(10000000);
		//System.out.println(id);
		
		System.out.println("broken:");
		MaliciousDate.clearInstantiatedDates();
		MaliciousDate d2 = new MaliciousDate();
		BrokenImutableDate id2 = new BrokenImutableDate(d2);
		System.out.println(id2);
		
		//constructor attack
		d2.setTime(100000);
		System.out.println(id2);
		
		//accessor attack
		id2.getDate().setTime(100000);
		System.out.println(id2);
		
		//constructor-clone attack
		dates = MaliciousDate.getInstantiatedDates();
		System.out.println(dates);
		dates.get(1).setTime(10000000);
		System.out.println(id2);
		
	
	}
}
