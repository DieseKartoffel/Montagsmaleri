package kommunikation.rmi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import spielsteuerung.IClientSpielsteuerung;

public class Clientverbindungen implements IServerMethoden{
	
	IClientSpielsteuerung spielsteuerung;
	
	public Clientverbindungen(IClientSpielsteuerung spielsteuerung) {
		super();
		this.spielsteuerung = spielsteuerung;
	}
		
	/**
	 * Remote Objekte der dem Clienten:
	 * Implementiert die IServerMethoden, die die Clienten auf dem Server aufrufen.
	 */	
	Map<Integer, RegistrierteVerbindung> verbindungen = new HashMap<Integer, RegistrierteVerbindung>();
	
	public IClientMethoden getClientVerbindung(int playerID) {
		try {
			return verbindungen.get(playerID).getClientVerbindung();
		}catch(NullPointerException np) {
			throw new NullPointerException("Zu dem Spieler mit ID "+ playerID + " konnte keine Verbindung gefunden werden");
		}
	}
	
	//REFERENZ ZUR SPIELSTEUERUNG;
	
	private final static String SERVERNAME = "Server";

	public void hostServer() {
		try {
			
			Registry reg = LocateRegistry.createRegistry(8888);
			
			IServerMethoden stub = (IServerMethoden) UnicastRemoteObject.exportObject(this, 0);
			reg.bind(SERVERNAME, stub);
			System.err.println("Server ready");
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
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

	
	//Methoden die Clienten auf dem Server aufrufen:
	
	
	@Override
	public synchronized void registerConnection(int playerID, IClientMethoden remote) throws RemoteException {
		System.out.println(playerID+ " ist nun mit dem Server verbunden");
		RegistrierteVerbindung reg = new RegistrierteVerbindung(playerID, remote);
		verbindungen.put(playerID, reg);
	}

	@Override
	public synchronized String createMultiplayerGame(int playerID) throws RemoteException {
		return spielsteuerung.createMultiplayerGame(playerID);
	}


	@Override
	public synchronized void startMultiplayerGame(String gameID, int playerID, String nameWortliste, int rundenAnzahl, int timePerRound) throws RemoteException {
		spielsteuerung.startMultiplayerGame(gameID, playerID, nameWortliste, rundenAnzahl, timePerRound);
	}


	@Override
	public synchronized void joinMultiplayerGame(String gameID, int playerID) throws RemoteException {
		spielsteuerung.joinMultiplayerGame(playerID, gameID);
	}


	@Override
	public synchronized String createSingleplayerGame(int playerID) throws RemoteException {
		return spielsteuerung.createSingleplayerGame(playerID);
	}


	@Override
	public synchronized void startSinglePlayerGame(String gameID, int playerID, int difficulty) throws RemoteException {
		spielsteuerung.startSingleplayerGame(gameID, playerID, difficulty);
	}


//	@Override
//	public synchronized boolean joinGame(String gameID, int playerID) throws RemoteException {
//		// TODO Auto-generated method stub
//		return false;
//	}


	@Override
	public synchronized void sendToGameChat(String gameID, int playerID, String message) throws RemoteException {
		System.out.println("Per RMI eingegangen -> An Spielsteuerung senden: "+message);
		spielsteuerung.sendToGameChat(gameID, playerID, message);
		
		
	}



	@Override
	public synchronized void sendeBild(String gameID, int playerID, byte[] image) throws RemoteException {
		spielsteuerung.sendeBild(gameID, playerID, byteToImage(image));
	}
	

	@Override
	public synchronized boolean bewerteBild(String gameID, int playerID, byte[] image) throws RemoteException {
		return spielsteuerung.bewerteBild(gameID, playerID, byteToImage(image));
	}
	
	public synchronized BufferedImage byteToImage(byte[] bytes) {
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
