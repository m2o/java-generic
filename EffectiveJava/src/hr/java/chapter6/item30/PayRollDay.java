package hr.java.chapter6.item30;

public enum PayRollDay {

	MONDAY(PayType.WEEKDAY),
	TUESDAY(PayType.WEEKDAY),
	WEDNESDAY(PayType.WEEKDAY),
	THURSDAY(PayType.WEEKDAY),
	FRIDAY(PayType.WEEKDAY),
	SATURDAY(PayType.WEEKENDDAY),
	SUNDAY(PayType.WEEKENDDAY);
	
	private final PayType payType;
	
	private PayRollDay(PayType payType) {
		this.payType = payType;
	}
	
	public double pay(){
		return payType.pay();
	}
	
	private enum PayType{
		WEEKDAY{
			@Override
			public double pay() {
				return 10;
			}
		},
		WEEKENDDAY{
			@Override
			public double pay() {
				return 0;
			}
		};
		
		public abstract double pay();
	}
}
