package kommunikation.rmi;

public class RegistrierteVerbindung {

	
	/**
	 * Remote Objekt des Clients: 
	 * Wird in der Register-Methode vom Client gesendet.
	 * Enthält die Methoden, die der Server auf dem Clienten aufrufen kann.
	 */
	private IClientMethoden clientVerbindung = null;
	private int playerID;

	public RegistrierteVerbindung(int playerID, IClientMethoden clientVerbindung) {
		this.playerID = playerID;
		this.clientVerbindung = clientVerbindung;
	}
	
	public IClientMethoden getClientVerbindung() {
		return clientVerbindung;
	}

	public int getPlayerID() {
		return playerID;
	}
	
	
}
