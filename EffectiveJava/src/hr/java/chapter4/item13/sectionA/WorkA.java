package hr.java.chapter4.item13.sectionA;

public class WorkA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InternalWorker i = new InternalWorker();
	
		InternalWorker2 i2 = new InternalWorker2();
		i2.internalWork();
		i2.externalWork();
	}

}
