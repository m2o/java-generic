package hr.tennis.bot.learner.result.scenario;

public class SimpleScenario implements IScenario {

	private Integer bets;
	private Integer succBets;
	
	@Override
	public void init() {
		
		bets = 0;
		succBets = 0;

	}

	@Override
	public void classified(int prediction, int expectedClass,
			double probability, double[] coeffients) {
		
		bets ++;

		if(prediction == expectedClass){
			succBets ++;
		}

	}

	@Override
	public void printStatus() {
		
		System.out.println("total bets: " + bets);
		System.out.println("succ bets: " + succBets);
		System.out.println("success rate: " + ((double)succBets/bets)*100 + "%");

	}

	@Override
	public double getAmountDifference() {
		// TODO Auto-generated method stub
		return 0;
	}

}
