package hr.tennis.bot.optimizer.util;

import java.util.Random;

public class RandomUtil {
	public static final Random rand = new Random();

	public static double[] nextArray(int size,
			double xMin,
			double xMax) {

		double[] vector = new double[size];
		for(int i=0;i<size;i++){
			vector[i] = nextDouble(xMin,xMax);
		}

		return vector;
	}

	public static double[][] nextMatrix(int rows,
			int columns,
			double xMin,
			double xMax) {

		double[][] matrix = new double[rows][columns];

		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				matrix[i][j] = nextDouble(xMin,xMax);
			}
		}

		return matrix;
	}

	private static double nextDouble(double xMin, double xMax) {
		return rand.nextDouble() * (xMax-xMin) + xMin;
	}
}
