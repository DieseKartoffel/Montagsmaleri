package spielsteuerung;

import java.awt.image.BufferedImage;

import kommunikation.IServerClientkommunikation;
import neuronalesNetz.INetzSpielsteuerung;
import neuronalesNetz.NeuronalesNetz;

public class SingleplayerSpiel extends Spiel {
	
	Spieler player;
	INetzSpielsteuerung neuronalesNetz;
	String currentWord;

	public SingleplayerSpiel(IServerClientkommunikation klientenKommunikation, String gameID) {
		super(klientenKommunikation, gameID);
		neuronalesNetz = new NeuronalesNetz();
	}

	public void setPlayer(Spieler player) {
		this.player = player;
		
	}

	public void startGame(int difficulty) {
		
	}

	public boolean imageDrawn(BufferedImage image) {
		return neuronalesNetz.bildErkennung(image, currentWord);
	}

}
