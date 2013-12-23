package hr.java.chapter6.item30;

public enum Operation implements Stringable{
	PLUS("+"){
		public double apply(double a,double b){
			return a+b;
		}
	},
	MINUS("-"){
		public double apply(double a,double b){
			return a-b;
		}
	},
	MULTIPY("*"){
		public double apply(double a,double b){
			return a*b;
		}
	},
	DIVIDE("/"){
		public double apply(double a,double b){
			return a/b;
		}
	};
	
	private final String sign;

	private Operation(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString2() {
		return this.sign;
	}
	
	public abstract double apply(double a,double b);
}
