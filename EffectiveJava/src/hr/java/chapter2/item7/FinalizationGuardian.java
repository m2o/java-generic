package hr.java.chapter2.item7;

public class FinalizationGuardian {

	private final String name;

	@SuppressWarnings("unused") //only reference to finalizer guardian
	private final Object finalizer = new Object(){
		@Override
		protected void finalize() throws Throwable {
			System.out.println(name+" called anonymous finalizer");
		};
	};

	public FinalizationGuardian(String name) {
		this.name = name;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println(name+" called regular finalizer");
		super.finalize();
	}

	public static void main(String[] args) throws InterruptedException {

		FinalizationGuardian f = new FinalizationGuardian("auto");
		FinalizationGuardian g = new FinalizationGuardian("prikolica");

		//		f = null;
		g = null;

		System.gc();
		Thread.sleep(10000);

	}




}
