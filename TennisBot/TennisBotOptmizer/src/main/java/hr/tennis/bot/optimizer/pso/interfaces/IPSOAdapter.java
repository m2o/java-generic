package hr.tennis.bot.optimizer.pso.interfaces;

public interface IPSOAdapter<T> {

	public double[] serialize(T solution);
	public T deserialize(double[] particle);
	public T create();
	public double evaluate(T solution);

}
