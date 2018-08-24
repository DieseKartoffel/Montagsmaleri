package spielsteuerung;

public class Spieler {

	private int playerID;
	private String nickname;
	private boolean hasGuessed;
//	private boolean isDrawing;
	private int punkte;
	
	public Spieler(int playerID, String nickname) {
		this.playerID = playerID;
		this.nickname = nickname;
		punkte = 0;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public String getNickname() {
		return nickname;
	}

	public boolean hasGuessed() {
		return hasGuessed;
	}

//	public boolean isDrawing() {
//		return isDrawing;
//	}

	public int getPunkte() {
		return punkte;
	}

	public void setHasGuessed(boolean hasGuessed) {
		this.hasGuessed = hasGuessed;
		
	}

	public void assignPoints(int points) {
		this.punkte += points;
		
	}

	public void resetPoints() {
		this.punkte = 0;
	}


}
