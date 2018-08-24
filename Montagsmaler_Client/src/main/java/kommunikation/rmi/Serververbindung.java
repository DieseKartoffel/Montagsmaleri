package kommunikation.rmi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;

import spielsteuerung.IServerSpielsteuerung;


public class Serververbindung extends UnicastRemoteObject implements IClientMethoden {

	private static final long serialVersionUID = -4987528502869910912L;

	private final static String SERVERNAME = "Server";
	private static final int CONNECTION_RETRYS = 5;
	
	private IServerSpielsteuerung spielsteuerung;
	
	
	public Serververbindung(IServerSpielsteuerung spielsteuerung) throws RemoteException {
		super();
		this.spielsteuerung = spielsteuerung;
	}

	public IServerMethoden connectToServer(String serverIpAdress, int port, int myPlayerID) {

		int anzahlVersuche = 1;

		while (anzahlVersuche <= CONNECTION_RETRYS) {

			System.err.println("Connection try " + anzahlVersuche + "...");

			try {

				Registry registry = LocateRegistry.getRegistry(serverIpAdress, port);
				IServerMethoden stub = (IServerMethoden) registry.lookup(SERVERNAME);

				Serververbindung clientverbindung = this;
				stub.registerConnection(myPlayerID, clientverbindung);

				System.err.println("Connection to server succesful");
				
				return stub;

			} catch (RemoteException | NotBoundException re) {
				anzahlVersuche++;
				if (anzahlVersuche > CONNECTION_RETRYS) {
					re.printStackTrace();
				}
			}
		}
		return null;
	}

	
	/**
	 * Folgende Methoden kann der Server auf der Clientseite aufrufen. Dise sind für die UMSETZUNG der Kommunikation relevant.
	 */

	@Override
	public void playerConnectToGame(int playerID, String playerName) throws RemoteException {
		spielsteuerung.playerConnect(playerID, playerName);

	}

	@Override
	public void playerDisconnectFromGame(int playerID) throws RemoteException {
		spielsteuerung.playerDisconnect(playerID);

	}

	@Override
	public void forwardDrawingPlayer(int playerID) throws RemoteException {
		spielsteuerung.setDrawingPlayer(playerID);
	}

	@Override
	public void forwardWordToDraw(String word) throws RemoteException {
		spielsteuerung.setWordToDraw(word);

	}

	@Override
	public void sendToChat(int playerID, String message) throws RemoteException {
		spielsteuerung.appendToChat(playerID, message);
//		System.out.println(playerID +" schreibt: "+message);

	}

	@Override
	public void playerGuessedCurrentWord(int playerID) throws RemoteException {
		spielsteuerung.wordGuessed(playerID);

	}

	@Override
	public void forwardCurrentImage(byte[] image) throws RemoteException {
		spielsteuerung.setImage(byteToImage(image));
	}
	
	public BufferedImage byteToImage(byte[] bytes) {
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	

	@Override
	public void givePoints(int playerID, int points) throws RemoteException {
		spielsteuerung.assignPoints(playerID, points);

	}

	@Override
	public void rundeVorbei() throws RemoteException {
		spielsteuerung.roundOver();

	}

	@Override
	public void spielVorbei() throws RemoteException {
		spielsteuerung.gameOver();

	}

	@Override
	public void startMultiplayerGame(int rundenzahl, int timePerRound) {
		spielsteuerung.startMultiplayergame(rundenzahl, timePerRound);
		
	}

}
