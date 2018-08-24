package statistikverwaltung;

public interface IServerSpielStatistikverwaltung {
	public void spielGewonnen(int playerID);
	public void spielAbgeschlossen(int playerID);
	public void punkteErhalten(int playerID);
	public void wortErraten(int playerID);
	public void singlePlayerWin(int playerID, int drawingSeconds);
}
