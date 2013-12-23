package hr.java.item11;

public class Fruit {

	protected String color;
	protected Integer[] data;

	public Fruit(String color) {
		this.color = color;
		this.data = new Integer[]{1,2,3};
	}
	
	public Integer[] getData() {
		return data;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	protected Fruit clone() {
		try {
			Fruit f = (Fruit) super.clone();
			f.data = this.data.clone();
			return f;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
