package hr.tennis.bot.learner.result.scenario;

public class ValueMultipliedSingleBetScenario extends FixedSingleBetScenario {

	public ValueMultipliedSingleBetScenario(ScenarioSettings scenarioSettings) {
		super(scenarioSettings);
	}

	@Override
	protected boolean isBettable(int prediction, double probability,double coeffcient) {
		return (!this.settings.betOnlyFavorite || (prediction==0)) &&
		(probability * coeffcient >= this.settings.valueThreashold);
	}
}
