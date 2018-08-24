package kommunikation;

import java.awt.image.BufferedImage;

public interface IServerClientkommunikation {
	public void startMultiplayerGame(int recievingPlayerID, int rundenzahl, int timePerRound);
	
	public void playerConnectToGame(int recievingPlayerID, int playerID, String playerName);

	public void playerDisconnectFromGame(int recievingPlayerID, int playerID);

	public void forwardDrawingPlayer(int recievingPlayerID, int playerID);

	public void forwardWordToDraw(int recievingPlayerID, String word);

	public void sendToChat(int recievingPlayerID, int playerID, String message);

	public void playerGuessedCurrentWord(int recievingPlayerID, int playerID);

	public void forwardCurrentImage(int recievingPlayerID, BufferedImage image);

	public void givePoints(int recievingPlayerID, int playerID, int points);

	public void rundeVorbei(int recievingPlayerID);

	public void spielVorbei(int recievingPlayerID);


}
