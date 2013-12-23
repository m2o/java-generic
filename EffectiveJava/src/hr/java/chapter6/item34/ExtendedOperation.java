package hr.java.chapter6.item34;

public enum ExtendedOperation implements Operation {
	MUL{	
		@Override
		public double apply(double a, double b) {
			return a*b;
		}
	},
	DIV{	
		@Override
		public double apply(double a, double b) {
			return a/b;
		}
	};
	
}
