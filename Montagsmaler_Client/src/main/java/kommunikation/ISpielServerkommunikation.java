package kommunikation;

import java.awt.image.BufferedImage;

public interface ISpielServerkommunikation {
		
	public String createMultiplayerGame();

	public void startMultiplayerGame(String nameWortliste, int rundenAnzahl,
			int timePerRound);

	public void joinMultiplayerGame(String gameID);

	public String createSingleplayerGame();

	public void startSinglePlayerGame(int difficulty);

//	public boolean joinSinglePlayerGame();

	public void sendToGameChat(String message);

	public void sendeBild(BufferedImage image);

	public boolean bewerteBild(BufferedImage image);
	

}
