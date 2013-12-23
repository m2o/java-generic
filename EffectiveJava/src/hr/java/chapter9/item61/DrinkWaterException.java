package hr.java.chapter9.item61;

public class DrinkWaterException extends Exception {

	public DrinkWaterException(WaterPollutedException e) {
		super(e);
	}

}
