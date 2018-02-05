package hr.tennis.bot.optimizer.pso;

import hr.tennis.bot.optimizer.util.RandomUtil;

import java.util.Arrays;

public class Particle {
	
	private double[] position;
	private double[] velocity;
	private double value;
	
	private double personalBestValue;
	private double[] personalBestPosition;
	
	private double localBestValue;
	private double[] localBestPosition;
	
	public Particle(double[] position,double[] velocity) {
		this.position=position;
		this.velocity=velocity;
	}

	public static Particle getRandomInstance(double[][] xIntervals,double[][] vIntervals) {
		
		double[] position=new double[xIntervals.length];
		double[] velocity=new double[vIntervals.length];
		
		for(int i=0;i<xIntervals.length;i++){
			position[i]=RandomUtil.rand.nextDouble()*(xIntervals[i][1]-xIntervals[i][0])+xIntervals[i][0];
			velocity[i]=RandomUtil.rand.nextDouble()*(vIntervals[i][1]-vIntervals[i][0])+vIntervals[i][0];
		}
		
		return new Particle(position, velocity);
	}
	
	@Override
	public String toString() {
		return String.format("{x:%s,v:%s}", Arrays.toString(position),Arrays.toString(velocity));
	}

	public double getPersonalBestValue() {
		return personalBestValue;
	}

	public void setPersonalBestValue(double pBest) {
		this.personalBestValue = pBest;
	}
	public double[] getPersonalBestPosition() {
		return personalBestPosition;
	}

	public double[] getPosition() {
		return position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(double currentValue) {
		this.value = currentValue;
	}

	public void updatePersonalBest() {
		this.personalBestValue=this.value;
		this.personalBestPosition=this.position.clone();
	}

	public double getLocalBestValue() {
		return localBestValue;
	}

	public void setLocalBestValue(double localBestValue) {
		this.localBestValue = localBestValue;
	}

	public double[] getLocalBestPosition() {
		return localBestPosition;
	}

	public void setLocalBestPosition(double[] localBestPosition) {
		this.localBestPosition = localBestPosition;
	}
}
