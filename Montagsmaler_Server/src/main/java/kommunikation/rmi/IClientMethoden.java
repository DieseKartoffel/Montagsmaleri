package kommunikation.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientMethoden extends Remote {
	
	public void playerConnectToGame(int playerID, String playerName) throws RemoteException;

	public void playerDisconnectFromGame(int playerID) throws RemoteException;

	public void forwardDrawingPlayer(int playerID) throws RemoteException;

	public void forwardWordToDraw(String word) throws RemoteException;

	public void sendToChat(int playerID, String message) throws RemoteException;

	public void playerGuessedCurrentWord(int playerID) throws RemoteException;

	public void forwardCurrentImage(byte[] image) throws RemoteException;

	public void givePoints(int playerID, int points) throws RemoteException;

	public void rundeVorbei() throws RemoteException;

	public void spielVorbei() throws RemoteException;

	public void startMultiplayerGame(int rundenzahl, int timePerRound) throws RemoteException;
	
}
