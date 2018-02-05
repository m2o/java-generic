package hr.tennis.bot.util;

import java.io.File;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

import com.google.gson.Gson;

public class MatlabConnector {

	private static final MatlabProxyFactoryOptions OPTIONS = new MatlabProxyFactoryOptions.Builder()
															         .setHidden(true)
															         //.setProxyTimeout(30000L)
	 															     .setUsePreviouslyControlledSession(true)
	 															     .setMatlabStartingDirectory(new File("../TennisBotLearner/src/main/matlab"))
															         .build();

	private static final MatlabProxyFactory factory = new MatlabProxyFactory(OPTIONS);

	private static final Gson gson = new Gson();

	public static void func(String name, Object[] args) throws MatlabConnectionException, MatlabInvocationException {
//		MatlabProxy proxy = factory.getProxy();

		String stdin = String.format("%s(%s)",name,encode(args));
		System.out.println(stdin);

//	    proxy.eval(stdin);
//	    proxy.disconnect();
	}

	private static String encode(Object[] args) {
		String val = "";
		if(args!=null){
			val = gson.toJson(args);
			val = val.substring(1, val.length()-1);
		}
		return val;
	}

	public static void main(String[] args) throws MatlabConnectionException, MatlabInvocationException {
		func("mlp_learn", new Object[]{1,2,3.4,"bla"});
	}
}
