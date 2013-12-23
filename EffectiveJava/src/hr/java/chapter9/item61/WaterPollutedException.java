package hr.java.chapter9.item61;

public class WaterPollutedException extends Exception {

	public WaterPollutedException(LeadPoisoningException e) {
		super(e);
	}

}
