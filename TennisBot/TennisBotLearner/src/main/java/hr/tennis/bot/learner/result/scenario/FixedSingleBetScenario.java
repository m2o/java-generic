package hr.tennis.bot.learner.result.scenario;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FixedSingleBetScenario implements IScenario{

	protected ScenarioSettings settings;

	private Double account;
	private Double lowestAccountBalance;
	private Integer nBets;
	private Integer allBets;
	private Integer nSuccessBets;
	private Integer allSuccessBets;
	private Integer i;
	private List<BetData> betsData;

	public FixedSingleBetScenario(ScenarioSettings scenarioSettings) {
		this.settings = scenarioSettings;
	}

	@Override
	public void init() {
		this.account = this.settings.initialAmount;
		this.lowestAccountBalance = this.account;
		this.nBets = 0;
		this.allBets = 0;
		this.allSuccessBets = 0;
		this.i = 1;
		this.nSuccessBets = 0;
		this.betsData = new ArrayList<FixedSingleBetScenario.BetData>();
	}

	protected boolean isBettable(int prediction, double probability, double coeffcient){
		return (!this.settings.betOnlyFavorite || (prediction==0)) &&
		(probability >= this.settings.probabilityThreashold) &&
		(coeffcient >= this.settings.coefficientThreashold);
	}

	@Override
	public void classified(int prediction, int expectedClass,double probability, double[] coeffcients) {

		double bet = this.settings.betAmount;

		if((bet>this.account) || (bet < this.settings.minAllowedBet)){

			if(this.settings.showAllBetsPercentage){

				this.allBets ++;

				if(prediction == expectedClass){
					this.allSuccessBets ++;
				}
			}
			return;
		}

		boolean doBet = isBettable(prediction,probability,coeffcients[prediction]);

		if(this.settings.debug && doBet){
			System.out.printf("#%d - prediction: %d(%.2f%%) - actual:%d(%.2f) - %s\n",this.i,prediction,probability*100,expectedClass,coeffcients[expectedClass],doBet ? "bet" : "ignore");
		}
		this.i++;

		if(doBet){

			this.account-=bet;
			this.nBets++;
			this.allBets ++;

			BetData data = new BetData();
			data.coefficients = coeffcients;
			data.prediction = prediction;
			data.expectation = expectedClass;
			this.betsData.add(data);

			if(prediction==expectedClass){
				double winnings = bet*coeffcients[prediction]*(1-this.settings.tax);
				this.account+=winnings;
				this.nSuccessBets++;
				this.allSuccessBets ++;
			}
			if(this.account < this.lowestAccountBalance) {
				this.lowestAccountBalance = this.account;
			}

		}else if(this.settings.showAllBetsPercentage){

			this.allBets ++;

			if(prediction == expectedClass){

				this.allSuccessBets ++;

			}

		}
	}

	@Override
	public void printStatus() {

		StringBuffer b = new StringBuffer();

		b.append('\n');
		b.append(getClass().getSimpleName());
		b.append('\n');
		b.append(StringUtils.repeat('*',getClass().getSimpleName().length()));
		b.append('\n');

		b.append(String.format("probabilityThreashold:%.2f\n",this.settings.probabilityThreashold));
		b.append(String.format("coefficientThreashold:%.2f\n",this.settings.coefficientThreashold));
		b.append(String.format("valueThreashold:%.2f\n",this.settings.valueThreashold));
		b.append(String.format("betOnlyFavorite:%b\n",this.settings.betOnlyFavorite));


		b.append(String.format("initial amount:%.2f\n",this.settings.initialAmount));
		b.append(String.format("final amount:%.2f\n",this.account));

		b.append(String.format("amount diff:%.2f\n",getAmountDifference()));
		b.append(String.format("total bet amount:%.2f\n",getTotalBetAmount()));

		b.append("# of bets:");
		b.append(this.nBets);
		b.append('\n');

		b.append("# of correct bets:");
		b.append(this.nSuccessBets);
		b.append('\n');

		b.append(String.format("success rate:%.4f\n",getSuccessRate()*100));
		b.append(String.format("marginal return:%.4f\n",getMarginalReturn()));
		b.append(String.format("lowest account balance:%.4f\n", this.lowestAccountBalance));

		if(this.settings.showAllBetsPercentage){

			b.append("# of all bets:");
			b.append(this.allBets);
			b.append('\n');

			b.append("# of all correct bets:");
			b.append(this.allSuccessBets);
			b.append('\n');

			b.append(String.format("all bets success rate:%.4f\n", getAllBetsSuccessRate()*100));

		}

		System.out.println(b);

		//		System.out.printf("%s: %.2f --> %.2f (%.4f/#%d bets/%.2f%%) ",getClass().getSimpleName(),
		//											 this.initialAmount,
		//											 this.account,
		//											 (this.account-this.initialAmount)/(this.nBets*this.betAmount),
		//											 this.nBets,
		//											 this.nSuccessBets/(double)this.nBets * 100);
		//
		//		Collections.sort(this.betsData,new Comparator<BetData>() {
		//
		//			@Override
		//			public int compare(BetData o1, BetData o2) {
		//				int c1 = Double.valueOf(o1.coefficients[0]).compareTo(o2.coefficients[0]);
		//				if(c1!=0){
		//					return c1;
		//				}
		//				return Double.valueOf(o1.coefficients[1]).compareTo(o2.coefficients[1]);
		//			}
		//
		//		});
		//
		//		System.out.println(this.betsData);
	}

	public double getMarginalReturn() {
		return getAmountDifference()/getTotalBetAmount();
	}

	public double getAmountDifference() {
		return this.account-this.settings.initialAmount;
	}

	public double getTotalBetAmount() {
		return this.nBets*this.settings.betAmount;
	}

	public double getSuccessRate() {
		return this.nSuccessBets/(double)this.nBets;
	}

	public double getAllBetsSuccessRate(){

		return this.allSuccessBets/(double)this.allBets;

	}

	public static class BetData {
		public double[] coefficients;
		private int prediction;
		public int expectation;

		@Override
		public String toString() {
			return String.format("bet %d %f-%f %s",
					this.prediction,
					this.coefficients[0],
					this.coefficients[1],
					this.prediction==this.expectation ? "success" : "fail");
		}
	}
}