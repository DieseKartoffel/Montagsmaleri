package kommunikation.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerMethoden extends Remote {
	
	public void registerConnection(int playerID, IClientMethoden remote) throws RemoteException;

	public String createMultiplayerGame(int playerID) throws RemoteException;

	public void startMultiplayerGame(String gameID, int playerID, String nameWortliste, int rundenAnzahl,
			int timePerRound) throws RemoteException;

	public void joinMultiplayerGame(String gameID, int playerID) throws RemoteException;

	public String createSingleplayerGame(int playerID) throws RemoteException;

	public void startSinglePlayerGame(String gameID, int playerID, int difficulty) throws RemoteException;

//	public boolean joinGame(String gameID, int playerID) throws RemoteException;

	public void sendToGameChat(String gameID, int playerID, String message) throws RemoteException;

	public void sendeBild(String gameID, int playerID, byte[] image) throws RemoteException;

	public boolean bewerteBild(String gameID, int playerID, byte[] image) throws RemoteException;
}
