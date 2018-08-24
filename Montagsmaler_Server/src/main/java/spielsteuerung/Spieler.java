package spielsteuerung;

public class Spieler {

	private int playerID;
	private String nickname;
	private Spiel currentGame;
	private int currentScore = 0;

	public Spieler(int playerID, String nickname) {
		this.playerID = playerID;
		this.nickname = nickname;
	}

	public Spiel getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Spiel currentGame) {
		this.currentGame = currentGame;
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getNickname() {
		return nickname;
	}
}