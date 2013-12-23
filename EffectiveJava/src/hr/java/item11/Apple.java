package hr.java.item11;

import java.util.Arrays;

public class Apple extends Fruit implements Cloneable {

	private boolean newtons;

	public Apple(String color, boolean newtons) {
		super(color);
		this.newtons = newtons;
	}
	
	@Override
	protected Apple clone() {
		return (Apple) super.clone();
	}
	
	public void setNewtons(boolean newtons) {
		this.newtons = newtons;
	}
	
	@Override
	public String toString() {
		return String.format("Apple(%s,%s,%s)", this.color, this.newtons, Arrays.toString(data));
	}
}
