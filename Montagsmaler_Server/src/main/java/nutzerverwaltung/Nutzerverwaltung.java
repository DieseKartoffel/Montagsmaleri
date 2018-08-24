package nutzerverwaltung;

import java.util.ArrayList;
import java.util.List;

public class Nutzerverwaltung implements IServerSpielNutzerverwaltung {

	@Override
	public boolean loginValid(int playerID) {
		int r = (int) (Math.random() * 10);
		return (r == 9) ? true : false;
	}

	@Override
	public String getNickname(int playerID) {
		return "Nickname"+playerID;
	}

	@Override
	public List<String> getWortliste(int playerID) {
		List<String> myTestWordList = new ArrayList<String>();
		myTestWordList.add("THM");
		myTestWordList.add("Softwaretechnik");
		myTestWordList.add("Eclipse");
		myTestWordList.add("Compiler");
		myTestWordList.add("Simplex");
		myTestWordList.add("SAP");
		myTestWordList.add("Java");
		myTestWordList.add("GUI");
		
		return myTestWordList;
	}

}
