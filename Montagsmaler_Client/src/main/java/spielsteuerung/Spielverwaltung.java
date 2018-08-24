package spielsteuerung;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import kommunikation.ISpielServerkommunikation;
import kommunikation.SpielServerkommunikation;
import spieloberfläche.ISpielSpieloberfläche;

public class Spielverwaltung implements IOberflächeSpielsteuerung, IServerSpielsteuerung {

	private boolean playingMultiplayer;

	private MultiplayerSpiel currentMPGame;
	private SingleplayerSpiel currentSPGame;

	private ISpielSpieloberfläche oberfläche;
	private ISpielServerkommunikation serverKommunikation;
	private int ownPlayerID = -1;

	public Spielverwaltung(ISpielSpieloberfläche oberfläche, int ownPlayerID) {
		this.oberfläche = oberfläche;
		this.ownPlayerID = ownPlayerID;
		this.serverKommunikation = new SpielServerkommunikation(this, ownPlayerID);
	}

	/*
	 * Methoden die von der Serverkommunikation aufgerufen werden:
	 */

	@Override
	public void playerConnect(int playerID, String nickname) {
		if (playingMultiplayer) {
			System.err.println(nickname + " ist dem Spiel beigetreten.");
			Spieler connectedPlayer = new Spieler(playerID, nickname);
			currentMPGame.addPlayer(connectedPlayer);
		} else {
			// ein spieler ist verbunden und ich bin im singleplayer? dann bin ich das wohl
			// selbst!
			Spieler probablyMyself = new Spieler(playerID, nickname);
			currentSPGame.setPlayer(probablyMyself);
		}
		oberfläche.updateScoreBoard();
	}

	@Override
	public void playerDisconnect(int playerID) {
		if (playingMultiplayer) {
			System.err.println(currentMPGame.getPlayer(playerID).getNickname() + " hat das Spiel verlassen.");
			currentMPGame.disconnectPlayer(playerID);
			oberfläche.updateScoreBoard();

		} else {
			// ein spieler ist verbunden und ich bin im singleplayer? dann bin ich das wohl
			// selbst!
			throw new IllegalStateException(
					"Der Server hat versucht den Disconnect eines fremden Spielers zu vermitteln, während der Client sich im Singleplayer befindet.");
		}

	}

	@Override
	public void setDrawingPlayer(int playerID) {
		if (playingMultiplayer) {
			currentMPGame.setDrawingPlayer(currentMPGame.getPlayer(playerID));
			oberfläche.updateScoreBoard();

		} else {
			// ein spieler ist verbunden und ich bin im singleplayer? dann bin ich das wohl
			// selbst!
			throw new IllegalStateException(
					"Der Server hat versucht den Malstatus eines fremden Spielers zu vermitteln, während der Client sich im Singleplayer befindet.");
		}

	}

	@Override
	public void setWordToDraw(String word) {
		if (playingMultiplayer) {
			if (currentMPGame.getDrawingPlayer().getPlayerID() != ownPlayerID) {
				throw new IllegalStateException("Der Client hat ein Wort erhalten, ohne der malende Spieler zu sein.");
			}
			currentMPGame.setWordToDraw(word);
		} else {
			currentSPGame.setWordToDraw(word);
		}
		oberfläche.updateSpielStatus();

	}

	Queue<String> chatMessages = new LinkedList<String>();

	Runnable chatMsgRunner = new Runnable() {
		@Override
		public void run() {
			while (!chatMessages.isEmpty()) {
				oberfläche.updateChat();
			}
			// newMessagesThread.interrupt();
		}
	};

	Thread newMessagesThread = new Thread(chatMsgRunner);

	@Override
	public void appendToChat(int playerIdAuthor, String message) {
		String author = "unkown";
		try {
			author = currentMPGame.getPlayer(playerIdAuthor).getNickname();
		} catch (NullPointerException np) {
			System.err.println("Der Spieler mit der ID " + playerIdAuthor
					+ " hat uns eine Nachricht geschickt. Sein Spieler Objekt konnte jedoch nicht gefunden werden");
		}

		chatMessages.add(author + " : " + message);
		if (!newMessagesThread.isAlive()) {
			newMessagesThread = new Thread(chatMsgRunner);
			newMessagesThread.start();
		}
	}

	@Override
	public void wordGuessed(int playerID) {
		if (!playingMultiplayer) {
			throw new IllegalStateException(
					"Der Server sagt, ein Spieler hätte ein Wort erraten, aber der Client befindet sich im Singleplayer.");
		}
		currentMPGame.getPlayer(playerID).setHasGuessed(true);
		oberfläche.updateScoreBoard();
	}

	@Override
	public void assignPoints(int playerID, int points) {
		if (playingMultiplayer) {
			currentMPGame.getPlayer(playerID).assignPoints(points);
		} else {
			if (currentSPGame.getMyselfSpieler().getPlayerID() != playerID) {
				throw new IllegalStateException(
						"Der Client befindet sich in einem Singleplayerspiel. Der Server wollte einem fremden Spieler Punkte vergeben.");
			}
			currentSPGame.getMyselfSpieler().assignPoints(points);
		}
		oberfläche.updateScoreBoard();

	}

	@Override
	public void roundOver() {
		if (playingMultiplayer) {
			currentMPGame.setDrawingPlayer(null);

			// for (Spieler s : currentMP.spielerListe)
			for (Map.Entry<Integer, Spieler> entry : currentMPGame.getPlayerList().entrySet()) {
				entry.getValue().setHasGuessed(false);
			}
		}

	}

	@Override
	public void gameOver() {
		if (playingMultiplayer) {
			currentMPGame.setDrawingPlayer(null);

			// for (Spieler s : currentMP.spielerListe)
			for (Map.Entry<Integer, Spieler> entry : currentMPGame.getPlayerList().entrySet()) {
				entry.getValue().setHasGuessed(false);
				entry.getValue().resetPoints();
			}
		}

	}

	@Override
	public void setImage(BufferedImage image) {
		if (playingMultiplayer) {
			currentMPGame.setCurrentImage(image);
		} else {
			throw new IllegalStateException(
					"Dem Clienten wurde ein gezeichnetes Bild zum Anzeigen übermittelt, obwohl er sich im Singleplayer befindet!");
		}

	}

	@Override
	public void startMultiplayergame(int rundenzahl, int timePerRound) {
		if (playingMultiplayer) {
			System.err.println("Spiel wurde vom Host gestartet. Rundenzahl = " + rundenzahl + " Zeit pro Runde: " + timePerRound + " Sekunden.");
			currentMPGame.startGame(rundenzahl, timePerRound);
		} else {
			throw new IllegalStateException(
					"Dem Clienten wurde eine Rundenzahl übermittelt, obwohl er sich im Singleplayer befindet!");
		}

	}

	/*
	 * Methoden, die von der Oberfläche aufgerufen werden:
	 */

	@Override
	public String getNextChatMessage() throws NoSuchElementException {
		return chatMessages.remove();
	}

	@Override
	public int getPoints(String playerName) {
		// for (Spieler s : currentMP.spielerListe)
		for (Map.Entry<Integer, Spieler> entry : currentMPGame.getPlayerList().entrySet()) {
			if (entry.getValue().getNickname().equals(playerName)) {
				return entry.getValue().getPunkte();
			}
		}
		throw new NullPointerException(
				playerName + "konnte nicht gefunden werden. Eine Punktzahl ist für ihn daher nicht existent.");
	}

	@Override
	public boolean hasGuessed(String playerName) {
		for (Map.Entry<Integer, Spieler> entry : currentMPGame.getPlayerList().entrySet()) {
			if (entry.getValue().getNickname().equals(playerName)) {
				return entry.getValue().hasGuessed();
			}
		}
		throw new NullPointerException(
				playerName + "konnte nicht gefunden werden. Eine hasGuessed Status ist für ihn daher nicht existent.");
	}

	@Override
	public String getMalenderSpieler() {
		if (playingMultiplayer) {
			return currentMPGame.getDrawingPlayer().getNickname();
		} else {
			System.err.println(
					"Warnung!\nEs wurde nach dem derzeit malenden Spieler gefragt, obwohl der Client sich im Singleplayer befindet. Es wird der Name des Clients selbst zurück gegeben!");
			return currentSPGame.getMyselfSpieler().getNickname();
		}
	}

	@Override
	public void sendMessage(String message) {
		serverKommunikation.sendToGameChat(message);

	}

	@Override
	public List<String> getPlayerNames() {

		ArrayList<String> names = new ArrayList<String>();

		if (!playingMultiplayer) {
			System.err.println(
					"Warnung!\nEs wurde nach der Spielerliste gefragt, obwohl der Client sich im Singleplayer befindet. Es wird eine Liste zurück gegeben, die lediglich den Namen des Clients selbst enthält!");
			names.add(currentSPGame.getMyselfSpieler().getNickname());
			return names;
		} else {
			for (Map.Entry<Integer, Spieler> entry : currentMPGame.getPlayerList().entrySet()) {
				names.add(entry.getValue().getNickname());
			}
			return names;
		}
	}

	@Override
	public String getWordToDraw() {
		if (playingMultiplayer) {
			return currentMPGame.getWordToDraw();
		} else {
			return currentSPGame.getWordToDraw();
		}
	}

	@Override
	public BufferedImage getCurrentImage() {
		if (!playingMultiplayer) {
			throw new IllegalStateException(
					"Client ist im Singleplayer und wird trotzdem von der GUI nack momentanem zu zeichnendem Bild gefragt");
		}
		return currentMPGame.getCurrentImage();
	}

	@Override
	public String createMultiplayerGame() {
		playingMultiplayer = true;
		currentSPGame = null;
		currentMPGame = new MultiplayerSpiel();
		String gameID = serverKommunikation.createMultiplayerGame(); //Fügt Spieler als Host auf Serverseite hinzu - Join aufruf nicht notwendig
		return gameID;
	}

	@Override
	public void startMultiplayerGame(String wortliste, int rundenZahl, int timePerRound) {
		playingMultiplayer = true;
		serverKommunikation.startMultiplayerGame(wortliste, rundenZahl, timePerRound);

	}

	@Override
	public void createSingleplayerGame() {
		playingMultiplayer = false;
		currentMPGame = null;
		currentSPGame = new SingleplayerSpiel();
		serverKommunikation.createSingleplayerGame();

	}

	@Override
	public void startSingleplayerGame(int difficulty) {
		playingMultiplayer = false;
		// In späterer Version können hier Parameter zur Konfiguration des
		// Einzelspielers übergeben werden!
		serverKommunikation.startSinglePlayerGame(difficulty);
		// serverKommunikation.joinSinglePlayerGame();

	}

	@Override
	public void joinMultiplayerGame(String gameID) {
		playingMultiplayer = true;
		currentMPGame = new MultiplayerSpiel();
		serverKommunikation.joinMultiplayerGame(gameID);

	}

	public int getAktuelleRunde() {
		if (!playingMultiplayer) {
			return currentSPGame.getAktuelleRunde();
		} else {
			return currentMPGame.getAktuelleRunde();
		}
	}

	@Override
	public int getRundenzahl() {
		if (!playingMultiplayer) {
			return currentSPGame.getRundenzahl();
		} else {
			return currentMPGame.getRundenzahl();
		}
	}

	@Override
	public int getTimeLeft() {
		if (!playingMultiplayer) {
			return currentSPGame.getTimeLeft();
		} else {
			return currentMPGame.getTimeLeft();
		}
	}

}
