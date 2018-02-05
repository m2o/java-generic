package hr.tennis.bot.model.factories;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.entity.player.Player;
import hr.tennis.bot.model.entity.player.Player.Hand;
import hr.tennis.bot.util.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * @author toni
 *
 */
public class PlayerFactory {

	public static Player createPlayer(String playerName){
		return createPlayer(playerName,null,null,null,null,null,null);
	}

	public static Player createPlayer(String playerName,Integer height,
			Integer weight,Date dateOfBirth, Date firstProYear,
			Gender gender,Hand playsHand){

		Player player = new Player();
		player.setName(playerName);
		player.setHeight(height);
		player.setWeight(weight);
		player.setDateOfBirth(dateOfBirth);
		player.setFirstProYear(firstProYear);
		player.setGender(gender);
		player.setPreferredHand(playsHand);
		return player;
	}

	public static Player createPlayer(String playerName, String[] playerParameters,Gender gender) throws ParseException {

		Player player = new Player();

		player.setName(playerName);
		if(playerParameters[0] != null){
			player.setHeight(Integer.parseInt(playerParameters[0]));
		}
		if(playerParameters[1] != null){
			player.setWeight(Integer.parseInt(playerParameters[1]));
		}
		if(playerParameters[2] != null){
			player.setDateOfBirth(DateUtil.parseDate(playerParameters[2], "dd. MM. yyyy"));
		}
		if(playerParameters[3] != null){
			player.setFirstProYear(DateUtil.createDate(Integer.parseInt(playerParameters[3]), 1, 1));
		}

		//		if(playerParameters[4] != null){
		//
		//			if(playerParameters[4].equals("man")){
		//				player.setGender(Gender.MALE);
		//			}
		//			if(playerParameters[4].equals("woman")){
		//				player.setGender(Gender.FEMALE);
		//			}
		//		}
		player.setGender(gender);

		if(playerParameters[5] != null){

			if(playerParameters[5].equals("right")){
				player.setPreferredHand(Hand.RIGHT);
			}

			if(playerParameters[5].equals("left")){
				player.setPreferredHand(Hand.LEFT);
			}
		}


		return player;
	}

	@Deprecated
	public static Player createPlayerWithLink(String playerName, String tennisExplorerLink) {

		Player player = new Player();
		player.setName(playerName);
		player.setLink(tennisExplorerLink);
		return player;
	}



}
