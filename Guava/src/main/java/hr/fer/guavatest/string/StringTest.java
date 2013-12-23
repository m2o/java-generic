package hr.fer.guavatest.string;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class StringTest {

	public static void main(String[] args) {

		String val = Joiner.on("-").skipNulls().join(Arrays.asList("A","B",null,"C","D"));
		System.out.println(val);

		val = Joiner.on("-").useForNull("?").join(Arrays.asList("A","B",null,"C","D"));
		System.out.println(val);
		
		
		Iterable<String> vals = Splitter.fixedLength(3).split("helloworld!");
		System.out.println(vals);
		
		vals = Splitter.onPattern("\\s+").omitEmptyStrings().split("hel lo	world!  ");
		System.out.println(vals);
		
		vals = Splitter.on(CharMatcher.WHITESPACE).split("aa bbb hello world! ");
		System.out.println(vals);
		
		vals = Splitter.on(',').trimResults().omitEmptyStrings().split("aa, bbb,	c, d, e, ");
		System.out.println(vals);
		
		String value = "This is a str čćč!";
		byte[] vbytes = value.getBytes(Charsets.UTF_8); //doesn't throw UnsupportedEncodingException
		byte[] vbytes2 = value.getBytes(Charsets.US_ASCII);
		
		System.out.println(Arrays.toString(vbytes));
		System.out.println(Arrays.toString(vbytes2));
		
		//Strings.class
		value = "hello";
		value = Strings.emptyToNull(value);
		System.out.println(value);
		
		value = "";
		value = Strings.emptyToNull(value);
		System.out.println(value);
		
		value = "burek";
		value = Strings.nullToEmpty(value);
		System.out.println(value);
		
		value = null;
		value = Strings.nullToEmpty(value);
		System.out.println(value);
		
		value = "";
		boolean invalid = Strings.isNullOrEmpty(value);
		System.out.printf("%s:%b\n",value,invalid);
		
		value = null;
		invalid = Strings.isNullOrEmpty(value);
		System.out.printf("%s:%b\n",value,invalid);
		
		value = "burek";
		invalid = Strings.isNullOrEmpty(value);
		System.out.printf("%s:%b\n",value,invalid);
		
	}
}
