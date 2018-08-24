package nutzerverwaltung;

import java.util.List;

public interface IServerSpielNutzerverwaltung {
	
	public boolean loginValid(int playerID);
	public String getNickname(int playerID);
	public List<String> getWortliste(int playerID);

}
