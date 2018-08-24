package spielsteuerung;

import kommunikation.IServerClientkommunikation;

public abstract class Spiel {
	
	protected IServerClientkommunikation klientenKommunikation;
	protected String gameID;
	protected boolean isMultiplayer;
	
	//Referenz zur der Instanz, die für die Kommunikation mit den Spielerclients benötigt wird, wird hier übergeben.
	//In der Spielsteuerung wurde diese ein mal instanziert. Sie wird jedoch nur in den Spielen tatsächlich genutzt:
	public Spiel(IServerClientkommunikation klientenKommunikation, String gameID) {
		this.klientenKommunikation = klientenKommunikation;
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}

	public boolean isMultiplayer() {
		return isMultiplayer;
	}

	public void setIsMultiplayer(boolean isMultiplayer) {
		this.isMultiplayer = isMultiplayer;
	}
}
