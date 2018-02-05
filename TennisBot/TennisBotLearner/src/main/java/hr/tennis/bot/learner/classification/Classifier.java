package hr.tennis.bot.learner.classification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Classifier implements IClassifier,Serializable {

	private static final long serialVersionUID = 1L;

	protected String name;

	public abstract void classify(double[] input, int dimensions, double[] output);

	public abstract void loadFrom(File file);

	@Override
	public void saveTo(File file) {
		ObjectOutputStream stream = null;
		try{
			stream = new ObjectOutputStream(new FileOutputStream(file));
			stream.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {}
			}
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s",this.getClass().getSimpleName(),this.name);
	}

}
