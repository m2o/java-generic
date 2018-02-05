package hr.tennis.bot.optimizer.function;

/**
 * Sučelje predstavlja realnu funkciju varijabilnog broja ulaznih varijabli.
 * @author toni
 */
public interface Function {
	
	/**
	 * Vraća vrijednost funkcije za dane ulazne varijable.
	 * @param variables ulazne varijable
	 * @return vrijednost funkcije za dane ulazne varijable
	 */
	public double value(double[] variables);
	
	/**
	 * Vraća broj ulaznih varijabli funkcije.
	 * @return broj ulaznih varijabli funkcije
	 */
	public int getVariableCount(); 
}
