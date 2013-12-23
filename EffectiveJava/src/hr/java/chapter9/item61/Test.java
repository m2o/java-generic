package hr.java.chapter9.item61;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			drinkWater();
		} catch (DrinkWaterException e) {
			e.printStackTrace();
		}
	}

	private static void drinkWater() throws DrinkWaterException {
		
		try {
			getTapWatter();
		} catch (WaterPollutedException e) {
			throw new DrinkWaterException(e);
		}
		
	}

	private static void getTapWatter() throws WaterPollutedException{
		
		try {
			getCityCollector();
		} catch (LeadPoisoningException e) {
			throw new WaterPollutedException(e);
		}
		
	}

	private static void getCityCollector() throws LeadPoisoningException {
		throw new LeadPoisoningException();
	}

}
