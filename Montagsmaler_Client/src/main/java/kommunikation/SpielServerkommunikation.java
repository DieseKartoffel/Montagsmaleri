package kommunikation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;

import kommunikation.rmi.IServerMethoden;
import kommunikation.rmi.Serververbindung;
import spielsteuerung.IServerSpielsteuerung;

public class SpielServerkommunikation implements ISpielServerkommunikation {


	private int myPlayerID = -1;

	private String currentGameID = "undefined";
	private Serververbindung verbindung = null;
	private IServerMethoden server = null;
	
//	public void setSpielsteuerung(IServerSpielsteuerung steuerung) {
//		spielsteuerung = steuerung;
//	}

	
	
	public SpielServerkommunikation(IServerSpielsteuerung spielsteuerung, int ownPlayerID) {
		this.myPlayerID = ownPlayerID;
		establishConnection(spielsteuerung);
		// In Testphase auskommentiert:
		// Danach wieder einfügen!

		// if(spielsteuerung == null) {
		// throw new NullPointerException("Referenz zur Spielsteuerung noch nicht
		// gesetzt. Dafür setSpielsteuerung aufrufen!");
		// }

	}

	private void establishConnection(IServerSpielsteuerung spielsteuerung) {

		try {

			verbindung = new Serververbindung(spielsteuerung);
			server = verbindung.connectToServer("localhost", 8888, this.myPlayerID);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// Methoden, die vom Spielsteuerung aufgerufen werden, um Daten an den Server zu
	// senden:

	@Override
	public String createMultiplayerGame() {
		try {
			this.currentGameID = server.createMultiplayerGame(myPlayerID);
			return this.currentGameID;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "invalid";
	}

	@Override
	public void startMultiplayerGame(String nameWortliste, int rundenAnzahl, int timePerRound) {
		try {
			server.startMultiplayerGame(currentGameID, myPlayerID, nameWortliste, rundenAnzahl, timePerRound);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void joinMultiplayerGame(String gameID) {
		this.currentGameID = gameID;
		try {
			server.joinMultiplayerGame(gameID, myPlayerID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String createSingleplayerGame() {
		try {
			this.currentGameID = server.createSingleplayerGame(myPlayerID);
			return currentGameID;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "invalid";
	}

	@Override
	public void startSinglePlayerGame(int difficulty) {
		try {
			server.startSinglePlayerGame(currentGameID, myPlayerID, difficulty);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public boolean joinSinglePlayerGame() {
//		try {
//			server.joinGame(currentGameID, myPlayerID);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}

	@Override
	public void sendToGameChat(String message) {
		try {
			server.sendToGameChat(currentGameID, myPlayerID, message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendeBild(BufferedImage image) {
		try {
			server.sendeBild(currentGameID, myPlayerID, imageToBytes(image));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean bewerteBild(BufferedImage image) {
		try {
			return server.bewerteBild(currentGameID, myPlayerID, imageToBytes(image));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	byte[] imageToBytes(BufferedImage img) {
		byte[] imageInByte = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

		} catch (Exception e) {

		}
		return imageInByte;
	}
}
