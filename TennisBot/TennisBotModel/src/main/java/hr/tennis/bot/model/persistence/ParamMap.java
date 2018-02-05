package hr.tennis.bot.model.persistence;

import java.util.HashMap;

public class ParamMap extends HashMap<String,Object>{

	private static final long serialVersionUID = 1L;

	public ParamMap() {
		
	}

	public ParamMap(String firstKey,Object firstValue) {
		this.addParameter(firstKey,firstValue);
	}

	public ParamMap addParameter(String name,Object value){
		this.put(name,value);
		return this;
	}
}
