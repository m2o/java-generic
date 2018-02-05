package hr.tennis.bot.optimizer.pso;

import hr.tennis.bot.optimizer.util.RandomUtil;

public class LocalPSO<T> extends PSO<T>{

	public LocalPSO(PSOSettings<T> settings) {
		super(settings);
	}

	@Override
	protected void move() {
		for(Particle particle : this.population){

			double[] position = particle.getPosition();
			double[] velocity = particle.getVelocity();
			double[] personalBestPosition = particle.getPersonalBestPosition();
			double[] localBestPosition = particle.getLocalBestPosition();

			for(int i=0;i<this.settings.xIntervals.length;i++){

				velocity[i]*=inertia();
				velocity[i] +=
					this.settings.C1 *
					RandomUtil.rand.nextDouble() *
					(personalBestPosition[i]-position[i]) +
					this.settings.C2 *
					RandomUtil.rand.nextDouble() *
					(localBestPosition[i]-position[i]);

				velocity[i]*=constriction();

				velocity[i]=fitToInterval(velocity[i], this.settings.vIntervals[i]);
				position[i]+=velocity[i];
				position[i]=fitToInterval(position[i], this.settings.xIntervals[i]);
			}
		}
	}

	@Override
	protected void refreshLocalBests() {

		for(int i=0;i<this.settings.particleCount;i++){

			for(int j=-this.settings.localK;j<=this.settings.localK;j++){
				int index = (i+this.settings.particleCount+j)%this.settings.particleCount;

				if(compare(this.population[index].getValue(), this.population[i].getLocalBestValue())>0){
					this.population[i].setLocalBestValue(this.population[index].getValue());
					this.population[i].setLocalBestPosition(this.population[index].getPosition().clone());
				}
			}
		}
	}

	public Particle[] getNeighbours(int i){

		Particle[] neighbours = new Particle[this.settings.localK*2];
		int k=0;

		for(int j=-this.settings.localK;j<=this.settings.localK;j++){
			if(j==0){
				continue;
			}
			int index = (i+this.settings.particleCount+j)%this.settings.particleCount;
			neighbours[k++]=this.population[index];
		}

		return neighbours;
	}
}
