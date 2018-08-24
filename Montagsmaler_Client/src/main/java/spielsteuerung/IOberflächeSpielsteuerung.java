package spielsteuerung;

import java.awt.image.BufferedImage;
import java.util.List;

public interface IOberflächeSpielsteuerung {
	
	
	public String getNextChatMessage();
	public int getPoints(String playerName);
	public boolean hasGuessed(String playerName);
	public String getMalenderSpieler();
	public void sendMessage(String message);
	public List<String> getPlayerNames();
	public String getWordToDraw();
	public BufferedImage getCurrentImage();
	public String createMultiplayerGame();
	public void startMultiplayerGame(String wortliste, int rundenZahl, int timePerRound);
	public void joinMultiplayerGame(String gameID);
	public void createSingleplayerGame();
	public void startSingleplayerGame(int difficulty);
	public int getRundenzahl();
	public int getTimeLeft();


}
