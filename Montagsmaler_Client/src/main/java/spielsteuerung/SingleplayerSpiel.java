package spielsteuerung;

public class SingleplayerSpiel{
	
	private Spieler myselfSpieler;
	private String wordToDraw;
	private int rundenZahl;
	private int aktuelleRunde;
	private int timeLeft;

	public void setPlayer(Spieler probablyMyself) {
		this.myselfSpieler = probablyMyself;	
	}
	
	public String getWordToDraw() {
		return wordToDraw;
	}

	public void setWordToDraw(String wordToDraw) {
		this.wordToDraw = wordToDraw;
	}

	public Spieler getMyselfSpieler() {
		return myselfSpieler;
		
	}

	public int getRundenzahl() {
		return this.rundenZahl;
	}

	public int getAktuelleRunde() {
		return this.aktuelleRunde;
	}

	public int getTimeLeft() {
		return this.timeLeft;
	}


}
