/**
 * 
 */
package hr.tennis.bot.learner.result.scenario;

/**
 * @author dajan
 *
 */
public class ValueAddedSingleBetScenario extends FixedSingleBetScenario {

	public ValueAddedSingleBetScenario(ScenarioSettings scenarioSettings) {
		super(scenarioSettings);
	}

	@Override
	protected boolean isBettable(int prediction, double probability,double coeffcient) {
		return (!this.settings.betOnlyFavorite || (prediction==0)) &&
		(probability + coeffcient >= this.settings.valueThreashold);
	}
	


}
