package hr.tennis.bot.optimizer.pso.main;

import hr.tennis.bot.optimizer.function.Function;
import hr.tennis.bot.optimizer.function.impl.RastringFunction;
import hr.tennis.bot.optimizer.pso.PSO;
import hr.tennis.bot.optimizer.pso.PSO.PSO_TYPE;
import hr.tennis.bot.optimizer.pso.PSOSettings;
import hr.tennis.bot.optimizer.pso.factory.PSOFactory;
import hr.tennis.bot.optimizer.pso.interfaces.IPSOAdapter;
import hr.tennis.bot.optimizer.util.RandomUtil;
import hr.tennis.bot.util.ArrayUtil;

import java.util.Arrays;



public class RastringPSO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int dim=2;
		double velIntervalPercent=0.10;

		double xMin=-10.0;
		double xMax=10.0;

		double vMin=-1.0*(xMax-xMin)*velIntervalPercent/2;
		double vMax=(xMax-xMin)*velIntervalPercent/2;

		final Function func = new RastringFunction(dim).negate();
		final double[][] xIntervals = ArrayUtil.expand(xMin,xMax,dim);
		double[][] vIntervals = ArrayUtil.expand(vMin,vMax,dim);

		PSOSettings<Solution> settings = new PSOSettings<Solution>();
		settings.particleCount = 100;
		settings.xIntervals = xIntervals;
		settings.vIntervals = vIntervals;
		settings.type = PSO_TYPE.LOCAL;
		settings.localK = 5;
		settings.useInertia = true;
		settings.wMax = 1.0;
		settings.wMin = 0.001;
		settings.maxIter = 100;

		settings.adapter = new IPSOAdapter<Solution>() {
			@Override
			public Solution create() {
				Solution s = new Solution();

				double[] position = new double[xIntervals.length];

				for(int i=0;i<xIntervals.length;i++){
					position[i]=RandomUtil.rand.nextDouble()*(xIntervals[i][1]-xIntervals[i][0])+xIntervals[i][0];
				}

				s.x = position;
				return s;
			}

			@Override
			public double evaluate(Solution solution) {
				return func.value(solution.x);
			};

			@Override
			public double[] serialize(Solution solution) {
				return solution.x;
			}

			@Override
			public Solution deserialize(double[] position) {
				Solution solution = new Solution();
				solution.x = position;
				return solution;
			}
		};

		PSO<Solution> pso = PSOFactory.createPSO(settings);
		pso.initialize();
		Solution s = pso.optimize();
		System.out.println(s);
	}

	public static class Solution{
		public double[] x;

		@Override
		public String toString() {
			return Arrays.toString(this.x);
		}

	}
}
