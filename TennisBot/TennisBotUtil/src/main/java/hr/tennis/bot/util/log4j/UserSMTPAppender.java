package hr.tennis.bot.util.log4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.net.SMTPAppender;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserSMTPAppender extends SMTPAppender {

	private static final Gson gson = new Gson();

	public void setToDict(String toDictStr) {

		Map<String,String> toDict = gson.fromJson(toDictStr,new TypeToken<Map<String,String>>(){}.getType());
		List<String> recipients = new LinkedList<String>();

		for(Map.Entry<String, String> entry : toDict.entrySet()){
			String username = entry.getKey();

			if(username.equals(System.getenv("USER")) || username.equals(System.getenv("USERNAME"))){
				recipients.add(entry.getValue());
			}
		}
		setTo(StringUtils.join(recipients,','));
	}
}
