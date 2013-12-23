package hr.java.chapter6.item34;

public enum BasicOperation implements Operation {
	ADD{	
		@Override
		public double apply(double a, double b) {
			return a+b;
		}
	},
	SUB{	
		@Override
		public double apply(double a, double b) {
			return a-b;
		}
	};
}
