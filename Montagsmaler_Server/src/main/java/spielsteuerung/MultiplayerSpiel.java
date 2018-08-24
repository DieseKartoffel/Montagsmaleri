package spielsteuerung;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import kommunikation.IServerClientkommunikation;

public class MultiplayerSpiel extends Spiel{
	
	private List<Spieler> players = new ArrayList<Spieler>();
	private Spieler malenderSpieler;
	private int rundenzahl = -1;
	private int timePerRound;
	private int aktuelleRound = 0;

	

	public MultiplayerSpiel(IServerClientkommunikation klientenKommunikation, String gameID) {
		// Superkonstruktor setzt gleichnamige Klassenvariablen dioe zur Kommunikation verwendet werden.
		super(klientenKommunikation, gameID);
	}

	public void addPlayer(Spieler player) {
		//Spieler zur Liste des Spiels hinzufügen:
		System.out.println(player.getNickname() + " hat sich mit " + this.gameID + " verbunden.");
		players.add(player);
		//Allen Spielern in der Liste eine Mitteilung senden:
		for(Spieler client : players) {
			klientenKommunikation.playerConnectToGame(client.getPlayerID(), player.getPlayerID(), player.getNickname());
		}
	}

	public void removePlayer(Spieler player) {
		
	}
	
	public void startGame(List<String> wortliste, int rundenzahl, int timePerRound) {
		this.rundenzahl = rundenzahl;
		this.timePerRound = timePerRound;
		for(Spieler client : players) {
			klientenKommunikation.startMultiplayerGame(client.getPlayerID(), rundenzahl, timePerRound);
		}
	}
	
	public void sendToChat(Spieler author, String message) {
		for(Spieler client : players) {
			klientenKommunikation.sendToChat(client.getPlayerID(), author.getPlayerID(), message);
		}
		
	}

	public void imageDrawn(Spieler paintingPlayer, BufferedImage image) {
		for(Spieler client : players) {
			klientenKommunikation.forwardCurrentImage(client.getPlayerID(), image);
		}
	}
}
