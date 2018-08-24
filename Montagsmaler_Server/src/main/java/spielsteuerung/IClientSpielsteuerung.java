package spielsteuerung;

import java.awt.image.BufferedImage;

public interface IClientSpielsteuerung {
	public String createMultiplayerGame(int playerID);
	public void joinMultiplayerGame(int playerID, String gameID); //Gibt anzahl der runden im beigetretenen Spiel zurück
	public void startMultiplayerGame(String gameID, int playerID, String nameWortliste, int rundenzahl, int timePerRound);
	public String createSingleplayerGame(int playerID);
	public void startSingleplayerGame(String gameID, int playerID, int difficulty); //automatischer join des spielers
	public void sendToGameChat(String gameID, int playerID, String message);
	public void sendeBild(String gameID, int playerID, BufferedImage image);
	public boolean bewerteBild(String gameID, int playerID, BufferedImage image);
}
