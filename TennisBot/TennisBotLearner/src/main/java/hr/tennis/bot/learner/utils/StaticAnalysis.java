package hr.tennis.bot.learner.utils;

import hr.tennis.bot.model.Constants;
import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.betting.BettingHouse;
import hr.tennis.bot.model.entity.betting.Odd;
import hr.tennis.bot.model.entity.match.SinglesMatch;
import hr.tennis.bot.model.entity.player.Ranking;
import hr.tennis.bot.model.persistence.DAO;
import hr.tennis.bot.model.persistence.ParamMap;
import hr.tennis.bot.model.persistence.PersistenceUtil;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;


/**
 * Hello world!
 *
 */
public class StaticAnalysis{

	private static class Cell implements Comparable<Cell>{
		public Integer valueA;
		public Integer valueB;

		@Override
		public int compareTo(Cell arg0) {
			int compareA = this.valueA.compareTo(arg0.valueA);
			if(compareA!=0){
				return compareA;
			}
			return this.valueB.compareTo(arg0.valueB);
		}
	}

	private static int toBucket(double value,double size){

		BigDecimal dvalue = new BigDecimal(value);
		BigDecimal dsize = new BigDecimal(size);

//		int ivalue = (int)(value*100);
//		int isize = (int)(size*100);

		return dvalue.divideToIntegralValue(dsize).intValue();
	}

    public static void main( String[] args ){

    	Integer maleExpectedN = 0,maleTotalN=0;
    	Integer femaleExpectedN=0,femaleTotalN=0;

    	double BUCKET_SIZE = 0.05;

    	Map<Cell,Integer> maleAnalysisExpected = new TreeMap<Cell, Integer>();
    	Map<Cell,Integer> maleAnalysisTotal = new TreeMap<Cell, Integer>();

    	Map<Integer,Integer> maleAnalysisSingleExpected = new TreeMap<Integer, Integer>();
    	Map<Integer,Integer> maleAnalysisSingleTotal = new TreeMap<Integer, Integer>();

    	Map<Cell,Integer> femaleAnalysisExpected = new TreeMap<Cell, Integer>();
    	Map<Cell,Integer> femaleAnalysisTotal = new TreeMap<Cell, Integer>();

    	Map<Integer,Integer> femaleAnalysisSingleExpected = new TreeMap<Integer, Integer>();
    	Map<Integer,Integer> femaleAnalysisSingleTotal = new TreeMap<Integer, Integer>();

    	Map<Cell,Integer> analysisExpected,analysisTotal;
    	Map<Integer,Integer> analysisSingleExpected,analysisSingleTotal;

		PersistenceUtil.initialize();
		PersistenceUtil.getEntityManager();

		BettingHouse averageBettingHouse = (BettingHouse) DAO.getSingleResult("BettingHouse.findBettingHouseByName", new ParamMap("name",Constants.BETTING_HOUSE_AVERAGE));

		for(Odd odd : averageBettingHouse.getOdds()){

			SinglesMatch match = (SinglesMatch)odd.getMatch();
			Ranking homePlayerRanking = match.getWinnerPlayerRanking();
			Ranking awayPlayerRanking = match.getLoserPlayerRanking();

			if(!(homePlayerRanking!=null && homePlayerRanking.getPosition()<=100 ||
			     awayPlayerRanking!=null && awayPlayerRanking.getPosition()<=100)){

				continue;
			}

			Gender gender = match.getWinnerPlayer().getGender();

			double lowerOdd = Math.min(odd.getWinnerWinningCoefficient(),odd.getLoserWinningCoefficient());
			double higherOdd = Math.max(odd.getWinnerWinningCoefficient(),odd.getLoserWinningCoefficient());

			if(!(lowerOdd>1.0 && higherOdd > 1.0)){
				continue;
			}

			boolean expected = lowerOdd == odd.getWinnerWinningCoefficient();

			Cell cell = new Cell();
			cell.valueA = toBucket(lowerOdd,BUCKET_SIZE);
			cell.valueB = toBucket(higherOdd,BUCKET_SIZE);

			if(Gender.MALE.equals(gender)){
				analysisExpected = maleAnalysisExpected;
				analysisTotal = maleAnalysisTotal;
				analysisSingleExpected = maleAnalysisSingleExpected;
				analysisSingleTotal = maleAnalysisSingleTotal;
				maleTotalN++;
			}else{
				analysisExpected = femaleAnalysisExpected;
				analysisTotal = femaleAnalysisTotal;
				analysisSingleExpected = femaleAnalysisSingleExpected;
				analysisSingleTotal = femaleAnalysisSingleTotal;
				femaleTotalN++;
			}

			/*single*/
			if(!analysisSingleExpected.containsKey(cell.valueA)){
				analysisSingleExpected.put(cell.valueA,new Integer(0));
				analysisSingleTotal.put(cell.valueA,new Integer(0));
			}

			if(expected){
				analysisSingleExpected.put(cell.valueA,analysisSingleExpected.get(cell.valueA)+1);
				if(Gender.MALE.equals(gender)){
					maleExpectedN++;
				}else{
					femaleExpectedN++;
				}
			}
			analysisSingleTotal.put(cell.valueA,analysisSingleTotal.get(cell.valueA)+1);

			/*cell*/
			if(!analysisExpected.containsKey(cell)){
				analysisExpected.put(cell,new Integer(0));
				analysisTotal.put(cell,new Integer(0));
			}
			if(expected){
				analysisExpected.put(cell,analysisExpected.get(cell)+1);

			}
			analysisTotal.put(cell,analysisTotal.get(cell)+1);
		}

		PersistenceUtil.getEntityManager().close();
		PersistenceUtil.close();

		System.out.println("\n Cell male:");

		for(Map.Entry<Cell,Integer> entry : maleAnalysisTotal.entrySet()){

			Cell cell = entry.getKey();
			int expected = maleAnalysisExpected.get(cell);
			int total = entry.getValue();
			double percent_expected = ((double)expected)/total;
			double expected_outcome = 1.0*percent_expected*((cell.valueA+1)*BUCKET_SIZE);

			if(total<5){
				continue;
			}

			System.out.println(String.format("%.2f %.2f %d/%d=%.2f %.2f",BUCKET_SIZE*cell.valueA,
															      BUCKET_SIZE*cell.valueB,
															      expected,
															      total,
															      percent_expected,
															      expected_outcome));
		}


		System.out.println("\n Single male:");

		for(Map.Entry<Integer,Integer> entry : maleAnalysisSingleTotal.entrySet()){

			Integer cellSingle = entry.getKey();
			int expected = maleAnalysisSingleExpected.get(cellSingle);
			int total = entry.getValue();
			double percent_expected = ((double)expected)/total;
			double expected_outcome = 1.0*percent_expected*((cellSingle+1)*BUCKET_SIZE);

			if(total<5){
				continue;
			}

			System.out.println(String.format("%.2f %d/%d=%.2f %.2f",BUCKET_SIZE*cellSingle,
															      expected,
															      total,
															      percent_expected,
															      expected_outcome));
		}

		System.out.printf("%d/%d=%.5f\n",maleExpectedN,maleTotalN,maleExpectedN/(double)maleTotalN);

		System.out.println("\n Single female:");

		for(Map.Entry<Integer,Integer> entry : femaleAnalysisSingleTotal.entrySet()){

			Integer cellSingle = entry.getKey();
			int expected = femaleAnalysisSingleExpected.get(cellSingle);
			int total = entry.getValue();
			double percent_expected = ((double)expected)/total;
			double expected_outcome = 1.0*percent_expected*((cellSingle+1)*BUCKET_SIZE);

			if(total<5){
				continue;
			}

			System.out.println(String.format("%.2f %d/%d=%.2f  %.2f",BUCKET_SIZE*cellSingle,
															      expected,
															      total,
															      percent_expected,
															      expected_outcome));
		}

		System.out.printf("%d/%d=%.5f\n",femaleExpectedN,femaleTotalN,femaleExpectedN/(double)femaleTotalN);
    }
}
