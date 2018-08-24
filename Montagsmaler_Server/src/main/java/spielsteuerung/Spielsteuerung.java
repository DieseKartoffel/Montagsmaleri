package spielsteuerung;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kommunikation.IServerClientkommunikation;
import kommunikation.ServerClientkommunikation;
import nutzerverwaltung.IServerSpielNutzerverwaltung;
import nutzerverwaltung.Nutzerverwaltung;

public class Spielsteuerung implements IClientSpielsteuerung {

	private IServerClientkommunikation klientenKommunikation = null;
	private IServerSpielNutzerverwaltung nutzerverwaltung = null;

	Map<String, MultiplayerSpiel> multiplayerSpiele = new HashMap<String, MultiplayerSpiel>();
	Map<String, SingleplayerSpiel> singleplayerSpiele = new HashMap<String, SingleplayerSpiel>();
	Map<Integer, Spieler> verbundeneSpieler = new HashMap<Integer, Spieler>();

	public Spielsteuerung() {
		klientenKommunikation = new ServerClientkommunikation(this);
		nutzerverwaltung = new Nutzerverwaltung();
	}

	@Override
	public String createMultiplayerGame(int playerID) {
		String gameID = createUniqueGameID();
		MultiplayerSpiel mpGame = new MultiplayerSpiel(klientenKommunikation, gameID);
		multiplayerSpiele.put(gameID, mpGame);

		Spieler host = new Spieler(playerID, nutzerverwaltung.getNickname(playerID));
		verbundeneSpieler.put(playerID, host);
		mpGame.addPlayer(host);
		host.setCurrentGame(mpGame);
		return gameID;
	}

	private String createUniqueGameID() {
		Date d = new Date();
		String original = "" + d.getTime();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// lege bytes von "original" (hashcode der spiele map) in den digester
		md.update(original.getBytes());
		// get hashcode bytes:
		byte[] digest = md.digest();

		// Nur die ersten 3 Bytes werden im buffer verarbeitet -> 6 Zeichen Joincode!
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			sb.append(String.format("%02x", digest[i] & 0xff));
		}
		return sb.toString();
	}

	@Override
	public void joinMultiplayerGame(int playerID, String gameID) {
		Spieler guest = new Spieler(playerID, nutzerverwaltung.getNickname(playerID));
		verbundeneSpieler.put(playerID, guest);
		MultiplayerSpiel mpGame = multiplayerSpiele.get(gameID);
		guest.setCurrentGame(mpGame);
		mpGame.addPlayer(guest);
	}

	@Override
	public void startMultiplayerGame(String gameID, int playerID, String nameWortliste, int rundenzahl,
			int timePerRound) {

		List<String> wortliste = loadWortliste(nameWortliste, playerID);
		multiplayerSpiele.get(gameID).startGame(wortliste, rundenzahl, timePerRound);

	}
	
	private List<String> loadWortliste(String bezeichnung, int playerID) {

		List<String> wortliste;
		try {
			BufferedReader br;
			switch (bezeichnung) {
			case "standard":
				br = new BufferedReader(new FileReader("wortlisten/standard.txt"));
				wortliste = Arrays.asList(br.readLine().split(","));
				br.close();
				return wortliste;
			case "englisch":
				br = new BufferedReader(new FileReader("wortlisten/englisch.txt"));
				wortliste = Arrays.asList(br.readLine().split(","));
				br.close();
				return wortliste;
			case "lebensmittel":
				br = new BufferedReader(new FileReader("wortlisten/lebensmittel.txt"));
				wortliste = Arrays.asList(br.readLine().split(","));
				br.close();
				return wortliste;
			case "tiere":
				br = new BufferedReader(new FileReader("wortlisten/tiere.txt"));
				wortliste = Arrays.asList(br.readLine().split(","));
				br.close();
				return wortliste;
			case "custom":
				wortliste = nutzerverwaltung.getWortliste(playerID);
				return wortliste;
			default:
				System.err.println("Wortliste mit Namen " + bezeichnung
						+ " konnte nicht gefunden werden. Wechsel zur Standardwortliste.");
				return loadWortliste("standard", playerID);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String createSingleplayerGame(int playerID) {
		String gameID = createUniqueGameID();
		SingleplayerSpiel spGame = new SingleplayerSpiel(klientenKommunikation, gameID);
		singleplayerSpiele.put(gameID, spGame);

		Spieler host = new Spieler(playerID, nutzerverwaltung.getNickname(playerID));
		verbundeneSpieler.put(host.getPlayerID(), host);
		host.setCurrentGame(spGame);
		spGame.setPlayer(host);
		return gameID;
	}

	@Override
	public void startSingleplayerGame(String gameID, int playerID, int difficulty) {
		singleplayerSpiele.get(gameID).startGame(difficulty);

	}

	@Override
	public void sendToGameChat(String gameID, int playerID, String message) {
		Spieler author = verbundeneSpieler.get(playerID);
		System.out.println("Test:");
		System.out.println("Spieler "+ verbundeneSpieler.size() + " " + verbundeneSpieler);
		System.out.println(author.getPlayerID());
		System.out.println("LOL");
		MultiplayerSpiel mpGame = multiplayerSpiele.get(gameID);
		mpGame.sendToChat(author, message);

	}

	@Override
	public void sendeBild(String gameID, int playerID, BufferedImage image) {
		Spieler paintingPlayer = verbundeneSpieler.get(playerID);
		MultiplayerSpiel mpGame = multiplayerSpiele.get(gameID);
		mpGame.imageDrawn(paintingPlayer, image);
	}

	@Override
	public boolean bewerteBild(String gameID, int playerID, BufferedImage image) {
		// Spieler paintingPlayer = verbundeneSpieler.get(playerID);
		SingleplayerSpiel spGame = singleplayerSpiele.get(gameID);
		return spGame.imageDrawn(image);
	}

}
