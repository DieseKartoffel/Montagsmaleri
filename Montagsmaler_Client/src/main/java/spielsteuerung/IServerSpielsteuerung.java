package spielsteuerung;

import java.awt.image.BufferedImage;


public interface IServerSpielsteuerung {

	public void playerConnect(int playerID, String nickname);
	public void playerDisconnect(int playerID);
	public void setDrawingPlayer(int playerID);
	public void setWordToDraw(String word);
	public void appendToChat(int playerIdAuthor, String message);
	public void wordGuessed(int playerID);
	public void assignPoints(int playerID, int points);
	public void roundOver();
	public void gameOver();
	public void setImage(BufferedImage image);
	public void startMultiplayergame(int rundenzahl, int timePerRound);
	
	
	
	
	
}
