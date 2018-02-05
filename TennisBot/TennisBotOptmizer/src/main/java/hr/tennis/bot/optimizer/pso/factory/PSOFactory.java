package hr.tennis.bot.optimizer.pso.factory;

import hr.tennis.bot.optimizer.pso.FullyInformedPSO;
import hr.tennis.bot.optimizer.pso.LocalPSO;
import hr.tennis.bot.optimizer.pso.PSO;
import hr.tennis.bot.optimizer.pso.PSO.PSO_TYPE;
import hr.tennis.bot.optimizer.pso.PSOSettings;

public class PSOFactory {

	public static <T> PSO<T> createPSO(PSOSettings<T> settings) {

		PSO<T> pso = null;

		if(settings.type.equals(PSO_TYPE.LOCAL)){
			pso = new LocalPSO<T>(settings);
		}else{
			pso = new FullyInformedPSO<T>(settings);
		}

		return pso;
	}

}
