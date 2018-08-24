package spielsteuerung;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class MultiplayerSpiel{
	
	private Spieler drawingPlayer;
	private String wordToDraw = "";
	private Map<Integer, Spieler> spielerListe;
	private BufferedImage currentImage;
	
	private int rundenzahl;
	private int aktuelleRunde;
	private int timeLeft;
	private int timePerRound;
	

	public MultiplayerSpiel() {
		spielerListe = new HashMap<Integer, Spieler>();
	}
	
	public void startGame(int rundenzahl, int timePerRound) {
		this.rundenzahl = rundenzahl;
		this.timePerRound = timePerRound;
	}
	
	public Spieler getPlayer(int playerID) {
		return spielerListe.get(playerID);
	}
	
	public void addPlayer(Spieler connectedPlayer) {
		spielerListe.put(connectedPlayer.getPlayerID(), connectedPlayer);
	}

	public void disconnectPlayer(int disconnectedPlayer) {
		spielerListe.remove(disconnectedPlayer);
	}
	
	public Spieler getDrawingPlayer() {
		return drawingPlayer;
	}

	public void setDrawingPlayer(Spieler drawingPlayer) {
		this.drawingPlayer = drawingPlayer;
	}
	
	public String getWordToDraw() {
		return wordToDraw;
	}

	public void setWordToDraw(String wordToDraw) {
		this.wordToDraw = wordToDraw;
	}

	public Map<Integer, Spieler> getPlayerList() {
		return this.spielerListe;
	}
	
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	public int getRundenzahl() {
		return this.rundenzahl;
	}

	public int getAktuelleRunde() {
		return this.aktuelleRunde;
	}

	public int getTimeLeft() {
		return this.timeLeft;
	}

	

}
