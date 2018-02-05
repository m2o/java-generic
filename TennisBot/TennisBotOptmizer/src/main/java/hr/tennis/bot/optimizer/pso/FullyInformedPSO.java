package hr.tennis.bot.optimizer.pso;

import hr.tennis.bot.optimizer.util.RandomUtil;

public class FullyInformedPSO<T> extends PSO<T>{

	public FullyInformedPSO(PSOSettings<T> settings) {
		super(settings);
	}

	@Override
	protected void move() {
		for(Particle particle : this.population){

			double[] position = particle.getPosition();
			double[] velocity = particle.getVelocity();
			double[] personalBestPosition = particle.getPersonalBestPosition();

			for(int i=0;i<this.settings.xIntervals.length;i++){

				velocity[i]*=inertia();
				velocity[i]+=
					this.settings.C1 *
					RandomUtil.rand.nextDouble()*
					(personalBestPosition[i]-position[i])
					+
					this.settings.C2 *
					RandomUtil.rand.nextDouble()*
					(this.globalBestPosition[i]-position[i]);

				velocity[i]*=constriction();
				velocity[i]=fitToInterval(velocity[i], this.settings.vIntervals[i]);

				position[i]+=velocity[i];
				position[i]=fitToInterval(position[i], this.settings.xIntervals[i]);
			}
			//			System.out.println(Arrays.toString(position));
		}
	}

	@Override
	protected void refreshLocalBests() {
		//does nothing here
	}

}
