package hr.fer.guavatest.string;

import com.google.common.base.CaseFormat;

public class CaseFormatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String val = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "THIS_IS_A_CONSTANT_VALUE");
		System.out.println(val);

	}

}
