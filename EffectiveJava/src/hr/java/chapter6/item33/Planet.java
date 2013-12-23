package hr.java.chapter6.item33;

import java.util.EnumMap;
import java.util.Map;

public class Planet {

	private String name;
	
	public Planet(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<PlanetType, Planet> planetsByType = new EnumMap<PlanetType, Planet>(PlanetType.class);
		planetsByType.put(PlanetType.GAS, new Planet("Jupiter"));
		planetsByType.put(PlanetType.GAS, new Planet("Saturn"));
		planetsByType.put(PlanetType.SOLID, new Planet("Earth"));
		planetsByType.put(PlanetType.SOLID, new Planet("Mars"));
		
		System.out.println(planetsByType);
	}

}
 