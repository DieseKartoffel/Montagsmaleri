package kommunikation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

import kommunikation.rmi.Clientverbindungen;
import kommunikation.rmi.IClientMethoden;
import spielsteuerung.Spielsteuerung;

public class ServerClientkommunikation implements IServerClientkommunikation{
	
	Clientverbindungen verbindungen = null;
	
	public ServerClientkommunikation(Spielsteuerung spielsteuerung) {
		verbindungen = new Clientverbindungen(spielsteuerung);
		verbindungen.hostServer();
	}

	@Override
	public void playerConnectToGame(int recievingPlayerID, int playerID, String playerName){
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.playerConnectToGame(playerID, playerName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void playerDisconnectFromGame(int recievingPlayerID, int playerID){
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.playerDisconnectFromGame(playerID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void forwardDrawingPlayer(int recievingPlayerID, int playerID){
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.forwardDrawingPlayer(playerID);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void forwardWordToDraw(int recievingPlayerID, String word){
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.forwardWordToDraw(word);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendToChat(int recievingPlayerID, int playerID, String message) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			System.out.println("Sende '" + message + "' an Client " + recievingPlayerID);
			client.sendToChat(playerID, message);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void playerGuessedCurrentWord(int recievingPlayerID, int playerID) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.playerGuessedCurrentWord(playerID);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void forwardCurrentImage(int recievingPlayerID, BufferedImage image) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.forwardCurrentImage(imageToBytes(image));
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void givePoints(int recievingPlayerID, int playerID, int points) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.givePoints(playerID, points);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rundeVorbei(int recievingPlayerID) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.rundeVorbei();
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void spielVorbei(int recievingPlayerID) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			client.spielVorbei();
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static byte[] imageToBytes(BufferedImage img) {
		byte[] imageInByte = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageInByte;
	}

	@Override
	public void startMultiplayerGame(int recievingPlayerID, int rundenzahl, int timePerRound) {
		try {
			IClientMethoden client = verbindungen.getClientVerbindung(recievingPlayerID);
			System.out.println("Sende an Client " + recievingPlayerID + ":" + rundenzahl);
			client.startMultiplayerGame(rundenzahl, timePerRound);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}
	}	
}
