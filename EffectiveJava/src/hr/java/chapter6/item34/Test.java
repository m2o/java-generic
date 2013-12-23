package hr.java.chapter6.item34;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		testOperation(BasicOperation.ADD);
		testOperation(BasicOperation.SUB);
		testOperation(ExtendedOperation.MUL);
		testOperation(ExtendedOperation.DIV);
		
		Enum<BasicOperation> basicOpEnum = BasicOperation.ADD;
		
		Enum<? extends Operation> opEnum = BasicOperation.ADD;
		Enum<? extends Operation> opEnum2 = ExtendedOperation.DIV;
		
	}

	private static void testOperation(Operation op){
		System.out.printf("%f @ %f = %f\n",1d,2d,op.apply(1d,2d));
	}
}
