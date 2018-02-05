package hr.tennis.bot.optimizer.pso.factory;

import hr.tennis.bot.optimizer.pso.Particle;
import hr.tennis.bot.optimizer.util.RandomUtil;

public class ParticleFactory {

	public static Particle createParticle(double[] position,double[][] vIntervals) {
		
		double[] velocity=new double[vIntervals.length];
		
		for(int i=0;i<vIntervals.length;i++){
			velocity[i]=RandomUtil.rand.nextDouble()*(vIntervals[i][1]-vIntervals[i][0])+vIntervals[i][0];
		}
		
		return new Particle(position, velocity);
	}

}
