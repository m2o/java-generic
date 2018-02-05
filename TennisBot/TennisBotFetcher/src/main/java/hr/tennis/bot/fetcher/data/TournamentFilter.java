package hr.tennis.bot.fetcher.data;

public class TournamentFilter {

	public static boolean fetchTournament(String tournament){
		return !tournament.contains("Futures");
	}

}
