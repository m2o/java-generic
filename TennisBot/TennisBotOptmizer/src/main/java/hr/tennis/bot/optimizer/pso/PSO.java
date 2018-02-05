package hr.tennis.bot.optimizer.pso;

import hr.tennis.bot.optimizer.pso.factory.ParticleFactory;

public abstract class PSO<T> {

	public static enum PSO_TYPE{
		FULL,LOCAL
	}

	protected PSOSettings<T> settings;

	protected Particle[] population;
	protected double globalBestValue;
	protected double[] globalBestPosition=null;

	protected int iter = 0;
	private double startTime;

	public PSO(PSOSettings<T> settings) {
		this.settings = settings;
	}

	public void runOneInteration(){
		refreshCurrentValues();
		refreshGlobalBest();
		refreshPersonalBests();
		refreshLocalBests();
		move();
		this.iter++;
		printPopulation();
	}

	public T optimize(){

		initialize();

		while(!isStoppingConditionMet()){
			runOneInteration();
		}

		return this.settings.adapter.deserialize(this.globalBestPosition);
	}

	private boolean isStoppingConditionMet() {

		return (this.iter >= this.settings.maxIter) ||
		((System.currentTimeMillis()/1000-this.startTime) >= this.settings.maxTime);
	}

	protected void printPopulation() {
		System.out.printf("#%d %s\n ",this.iter,this.globalBestValue);
	}

	protected abstract void refreshLocalBests();
	protected abstract void move();

	protected void refreshGlobalBest() {
		for(Particle particle : this.population){
			if(compare(particle.getValue(), this.globalBestValue)>0){
				this.globalBestValue = particle.getValue();
				this.globalBestPosition = particle.getPosition().clone();
			}
		}
	}

	private void refreshCurrentValues() {
		for(Particle particle : this.population){
			T solution = this.settings.adapter.deserialize(particle.getPosition());
			double value = this.settings.adapter.evaluate(solution);
			particle.setValue(value);
		}
	}

	private void refreshPersonalBests() {
		for(Particle particle : this.population){
			if(compare(particle.getValue(), particle.getPersonalBestValue())>0){
				particle.updatePersonalBest();
			}
		}
	}

	public void initialize() {

		this.population=new Particle[this.settings.particleCount];
		this.iter = 0;
		this.startTime = System.currentTimeMillis()/1000d;

		double initialBest = -1.0*Double.MAX_VALUE;
		this.globalBestValue=initialBest;

		for(int i=0;i<this.settings.particleCount;i++){
			T solution = this.settings.adapter.create();
			double[] position = this.settings.adapter.serialize(solution);
			Particle p = ParticleFactory.createParticle(position,this.settings.vIntervals);

			p.setPersonalBestValue(initialBest);
			p.setLocalBestValue(initialBest);
			this.population[i] = p;
		}
	}

	protected int compare(Double a,Double b){
		return a.compareTo(b);
	}

	protected double fitToInterval(double value, double[] intervals){
		if(value<intervals[0]){
			value=intervals[0];
		}else if(value>intervals[1]){
			value=intervals[1];
		}
		return value;
	}

	protected double inertia(){
		double inertia;
		if(this.settings.useInertia){
			if(this.iter < this.settings.maxIter){
				inertia = (this.settings.wMax-this.settings.wMin)*(this.settings.maxIter-this.iter)/this.settings.maxIter+this.settings.wMin;
			}else{
				inertia = this.settings.wMin;
			}
		}else{
			inertia = 1.0;
		}
		return inertia;
	}

	protected double constriction() {
		double constrictionFactor;
		if(this.settings.useConstriction){
			constrictionFactor = this.settings.getConstictionFactor();
		}else{
			constrictionFactor = 1.0;
		}
		return constrictionFactor;
	}


	public Particle[] getPopulation() {
		return this.population;
	}

	public double[][] getXIntervals() {
		return this.settings.xIntervals;
	}

	public double[] getGlobalBestPosition() {
		return this.globalBestPosition;
	}

	public int getParticleCount() {
		return this.settings.particleCount;
	}
}
